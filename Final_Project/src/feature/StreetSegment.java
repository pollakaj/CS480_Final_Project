package feature;

import geography.GeographicShape;

/**
 * An encapsulation of the portion of a street that connects two intersections.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public class StreetSegment extends AbstractFeature
{
  private double length;
  private int tail;
  private int head;
  private int highAddress;
  private int lowAddress;
  private GeographicShape geographicShape;
  private String code;
  
  /**
   * Constructs the street segment feature.
   *
   * @param id String Arc id for street segment
   * @param code String TIGER Road type code
   * @param shape GeographicShape shape of the segment
   * @param lowAddress int Address at tail node
   * @param highAddress int Address at head node
   * @param tail int tail node
   * @param head int head node
   * @param length double length of the segment
   */
  public StreetSegment(final String id, final String code, 
      final GeographicShape shape, final int lowAddress, final int highAddress, 
      final int tail, final int head, final double length)
  {
    super(id);
    this.code = code;
    this.geographicShape = shape;
    this.lowAddress = lowAddress;
    this.highAddress = highAddress;
    this.tail = tail;
    this.head = head;
    this.length = length;
  }
  
  /**
   * Gets the length of the segment.
   * @return double segment length
   */
  public double getLength()
  {
    return length;
  }
  
  /**
   * Gets the tail node of the segment.
   * @return int segment tail node
   */
  public int getTail()
  {
    return tail;
  }
  
  /**
   * Gets the head node of the segment.
   * @return int segment head node
   */
  public int getHead()
  {
    return head;
  }
  
  /**
   * Gets the head node address in the segment.
   * @return int head node address
   */
  public int getHighAddress()
  {
    return highAddress;
  }
  
  /**
   * Gets the tail node address in the segment.
   * @return int tail node address
   */
  public int getLowAddress()
  {
    return lowAddress;
  }

  /**
   * Gets the Geographic Shape for the street segment.
   * @return GeographicShape Shape for the street segment
   */
  public GeographicShape getGeographicShape()
  {
    return geographicShape;
  }
  
  /**
   * Gets the TIGER road code for the segment.
   * @return String segment code
   */
  public String getCode()
  {
    return code;
  }
}
