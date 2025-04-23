package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Interface for viewing a specific cartography transformation.
 * 
 * @author pollakaj
 * @version 1.0;
 *
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface ViewTransformation
{
  /**
   * Must return the reflection that was used.
   *
   * @return AffineTrnasform object of the reflection transformation used
   */
  public abstract AffineTransform getLastReflection();
  
  /**
   * Must return the last complete transformation that was used.
   *
   * @return AffineTransformation object of the last complete transformation
   */
  public abstract AffineTransform getLastTransform();
  
  /**
   * Must return an AffineTransform that translates, rotates, and scales the 
   * coordinates that are in kilometers to display coordinates that are in 
   * pixels.
   *
   * @param displayBounds Rectangle2D.Double bounds of the display window
   * @param contentBounds Rectangle2D.Double bounds of the content to be 
   * transformed to fit in the display
   * @return AffineTransform object to transforms the coordinates to display 
   * coordinates
   */
  public abstract AffineTransform getTransform(final Rectangle2D displayBounds, 
      final Rectangle2D contentBounds);
}
