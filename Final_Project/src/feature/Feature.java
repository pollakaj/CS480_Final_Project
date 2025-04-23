package feature;

import geography.GeographicShape;

/**
 * An element of a thematic map.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public interface Feature
{
  /**
   * Gets the ID of the feature.
   *
   * @return String ID value
   */
  public abstract String getID();
  
  /**
   * Gets the Geogrpahic Shape of the feature.
   *
   * @return GeographicShape Shape for the feature
   */
  public GeographicShape getGeographicShape();
}
