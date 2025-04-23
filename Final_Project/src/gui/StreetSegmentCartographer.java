package gui;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.Iterator;

import feature.StreetSegment;
import feature.StreetThemeLibrary;
import geography.Theme;
import geography.ThemeLibrary;

/**
 * Strategy that a CartographyPanel uses to render StreetSegment objects.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class StreetSegmentCartographer implements Cartographer<StreetSegment>
{
  private ThemeLibrary themes;
  
  /**
   * StreetSegmentCartographer Constructor intializes StreetTheme Library.
   */
  public StreetSegmentCartographer()
  {
    this.themes = new StreetThemeLibrary();
  }
  
  /**
   * Paint the given elements.
   * 
   * @param g2 The rendering engine to use
   * @param at The AffineTransform to use
   * @param i The elements
   * @param highlight boolean to check if segment should be highlighted
   */
  private void paintElements(final Graphics2D g2, final AffineTransform at, 
      final Iterator<StreetSegment> i, final boolean highlight)
  {
    while (i.hasNext()) 
    {
      StreetSegment streetSegment = i.next();

      Shape shape = streetSegment.getGeographicShape().getShape();
      Shape transformedShape = at.createTransformedShape(shape);

      if (highlight) 
      {
        Theme highlightColor = this.themes.getHighlightTheme();
        g2.setColor(highlightColor.getColor());
        g2.setStroke(highlightColor.getStroke());
      } else 
      {
        Theme theme = themes.getTheme(streetSegment.getCode());
        if (theme == null) theme = themes.getTheme(null);

        g2.setColor(theme.getColor());
        g2.setStroke(theme.getStroke());
      }

      g2.draw(transformedShape);
    }
  }

  @Override
  public void paintHighlights(final CartographyDocument<StreetSegment> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    paintElements(g2, at, model.highlighted(), true);
  }

  @Override
  public void paintShapes(final CartographyDocument<StreetSegment> model, 
      final Graphics2D g2, final AffineTransform at)
  {
    paintElements(g2, at, model.iterator(), false);
  }
}
