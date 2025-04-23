package geography;

import java.awt.Color;
import java.awt.Stroke;

/**
 * Theme object for the coloring and strokes in the map.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU honor code.
 */
public class Theme
{
  private Color color;
  private Stroke stroke;
  
  /**
   * Theme constructor to initialize color and stroke.
   *
   * @param color Color for the theme
   * @param stroke Strok for the theme
   */
  public Theme(final Color color, final Stroke stroke)
  {
    this.color = color;
    this.stroke = stroke;
  }
  
  /**
   * Gets the the theme color.
   * @return Color theme color
   */
  public Color getColor()
  {
    return color;
  }
  
  /**
   * Gets the Theme stroke.
   * @return Stroke theme stroke
   */
  public Stroke getStroke()
  {
    return stroke;
  }

}
