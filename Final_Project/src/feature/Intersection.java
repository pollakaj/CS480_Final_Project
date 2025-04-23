package feature;

import java.util.ArrayList;
import java.util.List;

/**
 * An encapsulation of an intersection in a street network. A vertex in a graph.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public class Intersection
{
  private List<StreetSegment> inbound;
  private List<StreetSegment> outbound;

  /**
   * Intersection constructor initializing inbound and outbound street segment lists.
   */
  public Intersection()
  {
    this.inbound = new ArrayList<>();
    this.outbound = new ArrayList<>();
  }
  
  /**
   * Adds a Street segment to the inbound of the Intersection.
   * @param segment StreetSegment to be added
   */
  public void addInbound(final StreetSegment segment)
  {
    inbound.add(segment);
  }
  
  /**
   * Adds a street segment to the outbound of the intersection.
   * @param segment StreetSegment to be added
   */
  public void addOutbound(final StreetSegment segment)
  {
    outbound.add(segment);
  }
  
  /**
   * Gets the List of street segments for the inbound.
   * @return List<StreetSegment> Inbound list
   */
  public List<StreetSegment> getInbound()
  {
    return inbound;
  }
  
  /**
   * Gets the List of street segments for the outbound.
   * @return List<StreetSegment> Outbound list
   */
  public List<StreetSegment> getOutbound()
  {
    return outbound;
  }
}
