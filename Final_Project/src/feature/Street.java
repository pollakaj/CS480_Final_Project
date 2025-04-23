package feature;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import geography.GeographicShape;
import geography.PiecewiseLinearCurve;

/**
 * A collection of Street Segments.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public class Street extends AbstractFeature
{
  private PiecewiseLinearCurve shape;
  private String category;
  private String code;
  private String name;
  private String prefix;
  private String suffix;
  private List<StreetSegment> segments;
  
  /**
   * Street constructor.
   *
   * @param prefix String prefix of street name (N, S, W, E)
   * @param name String name of street
   * @param category String type of Street
   * @param suffix String suffix of street name
   * @param code String TIGER Road type code
   */
  public Street(final String prefix, final String name, final String category, 
      final String suffix, final String code)
  {
    super(createCanonicalName(prefix, name, category, suffix));
    this.prefix = prefix;
    this.name = name;
    this.category = category;
    this.suffix = suffix;
    this.code = code;
    this.segments = new LinkedList<>();
  }
  
  /**
   * Adds the Street segment to the list.
   * @param segment Street Segment to be added
   */
  public void addSegment(final StreetSegment segment)
  {
    this.segments.add(segment);
  }
  
  /**
   * Creates the canonial name of the street.
   *
   * @param prefix String prefix of street name (N, S, W, E)
   * @param name String name of street
   * @param category String type of Street
   * @param suffix String suffix of street name
   * @return String of the concatenated name
   */
  public static String createCanonicalName(final String prefix, 
      final String name, final String category, final String suffix)
  {
    return String.join(" ", prefix, name, category, suffix).trim();
  }
  
  /**
   * Gets the segments matching the street number passed.
   *
   * @param number int street number passed
   * @return List<StreetSegment> of street segments with given street number
   */
  public List<StreetSegment> getSegments(final int number)
  {
    List<StreetSegment> result = new LinkedList<>();
    for (StreetSegment segment : segments) 
    {
      if (segment.getLowAddress() <= number 
          && segment.getHighAddress() >= number) result.add(segment);
    }
    return result;
  }
  
  /**
   * Gets the street segments as an iterator over the elements.
   * @return Iterator<StreetSegment> iterator over the segments
   */
  public Iterator<StreetSegment> getSegments()
  {
    return segments.iterator();
  }
  
  /**
   * Gets the Geographic Shape for the street.
   * @return GeographicShape Shape for the street
   */
  public GeographicShape getGeographicShape()
  {
    return shape;
  }
  
  /**
   * Returns the size of the Street (number of segments).
   * @return int number of segments in street
   */
  public int getSize()
  {
    return this.segments.size();
  }
  
  /**
   * Gets the street type.
   * @return String type of street
   */
  public String getCategory()
  {
    return category;
  }
  
  /**
   * Gets the TIGER Road type code.
   * @return String type code
   */
  public String getCode()
  {
    return code;
  }
  
  /**
   * Gets the name of the street.
   * @return String street name
   */
  public String getName()
  {
    return name;
  }
  
  /**
   * Gets the prefix of the canonical street name.
   * @return String street prefix
   */
  public String getPrefix()
  {
    return prefix;
  }
  
  /**
   * Gets the suffix of the canonical street name.
   * @return String street suffix
   */
  public String getSuffix()
  {
    return suffix;
  }
}
