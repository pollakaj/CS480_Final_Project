package graph;

import feature.*;
import java.util.*;

/**
 * A label-calculating algorithm for calculating shortest paths.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm 
{
  private CandidateLabelManager labels;

  /**
   * Explicit Value Constructor.
   * 
   * @param labels The LabelManager to use
   */
  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
  {
    super();
    this.labels = labels;
  }

  /**
   * Find the shortest path from the given origin Intersection to the given 
   * destination Intersection on the given StreetNetwork.
   * 
   * @param origin
   * @param destination
   * @param net
   * @return The path
   */
  @Override
  public Map<String, StreetSegment> findPath(final int origin, final int destination, 
      final StreetNetwork net)
  {
    // List<String> highlightIDs = new ArrayList<String>();
    Map<String, StreetSegment> result = new HashMap<String, StreetSegment>();
    labels.getLabel(origin).setValue(0.0);

    int currentID = origin;

    Label candidate;
    do
    {
      // Adjust the labels of the reachable intersections
      Intersection current = net.getIntersection(currentID);
      for (StreetSegment segment: current.getOutbound())
      {
        labels.adjustHeadValue(segment);
      }

//      // Find the best path so far
//      highlightIDs.clear();
//      int temp = currentID;
//      while (temp != origin)
//      {
//        StreetSegment segment = labels.getLabel(temp).getInbound();
//        if (segment != null) 
//        {
//          highlightIDs.add(segment.getID());
//          temp = segment.getTail();
//        }
//        else
//        {
//          temp = origin;
//        }
//      }
//      // Notify observers about intermediate results
//      notifyStreetSegmentObservers(highlightIDs);

      candidate = labels.getCandidateLabel();
      if (candidate != null) currentID = candidate.getID();
    } while (candidate != null);


    currentID = destination;
    while (currentID != origin)
    {
      StreetSegment segment = labels.getLabel(currentID).getPredecessor();
      if (segment != null)
      {
        result.put(segment.getID(), segment);
        currentID = segment.getTail();
      }
      else
      {
        currentID = origin;
      }
    }


    return result;
  }
}
