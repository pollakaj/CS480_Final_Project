package geography;

import java.awt.Shape;

/**
 * Interface for Geographic Shape objects.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface GeographicShape
{
  /**
   * Gets the Id of the Geographic Shape.
   *
   * @return String representation of the Identification number
   */
  public abstract String getID();
  
  /**
   * Gets the Shape object of the Geographic Shape.
   *
   * @return Shape shape of the Geographic Shape
   */
  public abstract Shape getShape();
}
