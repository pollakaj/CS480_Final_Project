package math;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

/**
 * Converts from coordinates that are in kilometers to display coordinates that 
 * are in pixels with at the upper left.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class DisplayCoordinatesTransformation implements ViewTransformation
{
  private AffineTransform aroundX, at;
  
  /**
   * Default Constructor.
   */
  public DisplayCoordinatesTransformation()
  {
    aroundX = new AffineTransform(1.0, 0.0, 0.0, -1.0, 0.0, 0.0);
  }
  
  @Override
  public AffineTransform getLastReflection()
  {
    return aroundX;
  }

  @Override
  public AffineTransform getLastTransform()
  {
    return at;
  }

  @Override
  public AffineTransform getTransform(final Rectangle2D displayBounds, 
      final Rectangle2D contentBounds)
  {
    double contentHeight = contentBounds.getHeight();
    double contentWidth = contentBounds.getWidth();
    double contentX = contentBounds.getX();
    double contentY = contentBounds.getY();
    AffineTransform translation = 
        AffineTransform.getTranslateInstance(-contentX, -(contentHeight+contentY));
    
    double displayWidth = displayBounds.getWidth();
    double displayHeight = displayBounds.getHeight();
    double scale = Math.min(displayWidth/contentWidth, displayHeight/contentHeight);
    AffineTransform scaling = AffineTransform.getScaleInstance(scale, scale);
    
    at = translation;
    at.preConcatenate(aroundX);
    at.preConcatenate(scaling);
    
    return at;
  }

}
