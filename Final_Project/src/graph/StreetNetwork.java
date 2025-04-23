package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.Street;
import feature.StreetSegment;

/**
 * Collection of Street Segments connected by intersections (vertices).
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies to the JMU Honor Code.
 */
public class StreetNetwork
{
  private List<Intersection> intersections;
  
  /**
   * Constructor to initialize list of Intersections.
   */
  public StreetNetwork()
  {
    this.intersections = new ArrayList<>();
  }
  
  /**
   * Add an intersection to the List at a specific index.
   *
   * @param index int index in which the intersection should be added in list
   * @param intersection Intersection to be added to list
   */
  public void addIntersection(final int index, final Intersection intersection)
  {
    this.intersections.add(index, intersection);
  }
  
  /**
   * Getter for the intersection at given index.
   *
   * @param index int index for desired intersection
   * @return Intersection at the given index in list
   */
  public Intersection getIntersection(final int index)
  {
    return intersections.get(index);
  }
  
  /**
   * Returns the size of the Street network.
   *
   * @return int size of network
   */
  public int size()
  {
    return intersections.size();
  }
  
  /**
   * Creates of street network from given map of streets.
   *
   * @param streets Map<String, Street> map of streets to create a street 
   * network
   * @return StreetNetwork created from given streets
   */
  public static StreetNetwork createStreetNetwork(final Map<String, Street> streets)
  {
    StreetNetwork sn = new StreetNetwork();
    Map<Integer, Intersection> intersectionMap = new HashMap<>();
    for (Street street : streets.values())
    {
      Iterator<StreetSegment> segments = street.getSegments();
      while (segments.hasNext())
      {
        StreetSegment segment = segments.next();

        Intersection tailIntersection = intersectionMap.get(segment.getTail());
        if (tailIntersection == null) 
        {
          tailIntersection = new Intersection();
          intersectionMap.put(segment.getTail(), tailIntersection);
        }

        Intersection headIntersection = intersectionMap.get(segment.getHead());
        if (headIntersection == null) 
        {
          headIntersection = new Intersection();
          intersectionMap.put(segment.getHead(), headIntersection);
        }
        tailIntersection.addOutbound(segment);
        headIntersection.addInbound(segment);
      }
    }
    sn.intersections.addAll(intersectionMap.values());
    return sn;
  }
}
