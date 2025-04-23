package graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import feature.StreetSegment;
import feature.StreetSegmentObserver;

/**
 * Abstract class for classes implementing shortest path algorithms for the 
 * streets.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public abstract class AbstractShortestPathAlgorithm implements ShortestPathAlgorithm
{
  private Collection<StreetSegmentObserver> observers;
  
  /**
   * Abstract shortest path algorithm constructor to initialize observers.
   */
  public AbstractShortestPathAlgorithm()
  {
    this.observers = new ArrayList<>();
  }

  @Override
  public abstract Map<String, StreetSegment> findPath(final int origin, 
      final int destination, final StreetNetwork net);

  @Override
  public void addStreetSegmentObserver(final StreetSegmentObserver observer)
  {
    if (observer != null) observers.add(observer);
  }

  @Override
  public void removeStreetSegmentObserver(final StreetSegmentObserver observer)
  {
    if (observers.contains(observer)) observers.remove(observer);
  }

  @Override
  public void notifyStreetSegmentObservers(final List<String> segmentIDs)
  {
    for (StreetSegmentObserver observer : observers)
    {
      observer.handleStreetSegments(segmentIDs);
    }
  }

}
