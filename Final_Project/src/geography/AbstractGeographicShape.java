package geography;

import java.awt.Shape;

/**
 * Abstract Geographic Shape class for different types of Geographic Shapes.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public abstract class AbstractGeographicShape implements GeographicShape
{  
  private String id;
  
  /**
   * Parent constructor for all Geographic Shape objects.
   *
   * @param id String id for identifying individual geographic shape objects
   */
  public AbstractGeographicShape(final String id)
  {
    this.id = id;
  }

  @Override
  public String getID()
  {
    return this.id;
  }

  @Override
  public abstract Shape getShape();

}
