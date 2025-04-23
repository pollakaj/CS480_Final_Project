package graph;

import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;
import feature.StreetSegmentSubject;

/**
 * Interface for shortest Path Algorithms as a street segment subject to be 
 * observed.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface ShortestPathAlgorithm extends StreetSegmentSubject
{
  /**
   * Find the shortest path from a given origin to destination in a street 
   * network.
   *
   * @param origin int id of origin node
   * @param destination int id of destination node
   * @param net StreetNetwork given street network for the nodes
   * @return Map<String, StreetSegment> mapping of the Street Segments for the 
   *  shortest path
   */
  public abstract Map<String, StreetSegment> findPath(final int origin, 
      final int destination, final StreetNetwork net);
  
  /**
   * Adds a street segment observer to identify street segment objects during 
   * calculations.
   * 
   * @param observer StreetSegmentObserver to be added
   */
  public abstract void addStreetSegmentObserver(final StreetSegmentObserver observer);
  
  /**
   * Removes a street segment observer from the subject.
   * 
   * @param observer StreetSegmentObserver to be removed
   */
  public abstract void removeStreetSegmentObserver(final StreetSegmentObserver observer);
  
  /**
   * Method to notify street segment observers of identified street segment 
   * objects.
   *
   * @param segmentIDs List<String> of segment IDs to notify the observers
   */
  public abstract void notifyStreetSegmentObservers(final List<String> segmentIDs);
}
