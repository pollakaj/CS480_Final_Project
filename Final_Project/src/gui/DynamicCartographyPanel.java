package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;

/**
 * A DynamicCartographyPanel object is a CartographyPanel object that can handle
 *  dynamic position/location updates.
 *
 * @param <T> The type of the data
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{
  /**
   * Default Serial version ID.
   */
  private static final long serialVersionUID = 1L;
  private GPGGASentence gpgga;
  private MapProjection proj;

  /**
   * Constructor to initialize cartography doucment, cartographer interface and
   *  map projection.
   *
   * @param model CartographyDocument<T> the model to use
   * @param cartographer Cartographer<T> the cartographer to use
   * @param proj MapProjection the map projection to use
   */
  public DynamicCartographyPanel(final CartographyDocument<T> model, 
      final Cartographer<T> cartographer, final MapProjection proj)
  {
    super(model, cartographer);
    this.proj = proj;
  }
  
  /**
   * Develops the map image and dot following gps data. Invokes the street 
   * network, projects and transforms the current position.
   * 
   * @param g Graphics rendering engine to be used
   */
  public void paint(final Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    
    if (gpgga != null)
    {
      double lat = gpgga.getLatitude();
      double longi = gpgga.getLongitude();
      double[] projection = proj.forward(new double[] {longi, lat});

      if (zoomStack.isEmpty())
      {
        double width = 2000.0;
        double height = 2000.0;
        Rectangle2D.Double rect = new Rectangle2D.Double(
            projection[0] - width / 2, projection[1] - height / 2, width, 
            height);

        zoomStack.addFirst(rect);
      }

      Rectangle displayBounds = g2.getClipBounds();
      Rectangle2D viewBounds = zoomStack.peekFirst();
      AffineTransform at = displayTransform.getTransform(displayBounds, 
          viewBounds);

      super.paint(g);
      
      double[] displayCoords = new double[2];
      at.transform(projection, 0, displayCoords, 0, 1);
      
      g2.setColor(Color.RED);
      g2.fillOval((int) displayCoords[0] - 4, (int) displayCoords[1] - 4, 8, 8);
    }
  }

  @Override
  public void handleGPSData(final String sentence)
  {
    this.gpgga = GPGGASentence.parseGPGGA(sentence);  
    repaint();
  }

}
