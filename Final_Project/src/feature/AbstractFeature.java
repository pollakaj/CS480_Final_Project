package feature;

import geography.GeographicShape;

/**
 * This class provides the basic functionality of (most) objects that implement
 *  the Feature interface.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public abstract class AbstractFeature implements Feature
{
  private String id;
  
  /**
   * Abstract Feature Constructor, initializes ID attribute.
   *
   * @param id String id value for feature
   */
  public AbstractFeature(final String id)
  {
    this.id = id;
  }

  @Override
  public String getID()
  {
    return id;
  }

  @Override
  public abstract GeographicShape getGeographicShape();

}
