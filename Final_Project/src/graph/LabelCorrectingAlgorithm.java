package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import feature.Intersection;
import feature.StreetSegment;

/**
 * Associate a label with each node that represents the length of the shortest
 *  path to that node that has been found so far.
 *  
 *  @author Adam Pollak
 *  @version 1.0
 *  
 *  Honor Code: This code complies with the JMU Honor Code.
 */
public class LabelCorrectingAlgorithm extends AbstractShortestPathAlgorithm
{
  private CandidateLabelManager labels;
  
  /**
   * Constructor for the correcitng algorithm.
   *
   * @param labels Collection of labels managed for correcting algorithm
   */
  public LabelCorrectingAlgorithm(final CandidateLabelManager labels)
  {
    super();
    this.labels = labels;
  }
  
  @Override
  public Map<String, StreetSegment> findPath(final int origin, 
      final int destination, final StreetNetwork net)
  {
    Map<String, StreetSegment> result = new HashMap<>();
    //List<String> highlightIDs = new ArrayList<>();

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

    int currID = destination;
    while (currID != origin)
    {
      StreetSegment segment = labels.getLabel(currID).getPredecessor();
      if (segment != null) 
      {
        result.put(segment.getID(), segment);
        currID = segment.getTail();
      } 
      else currID = origin;
    }

    return result;
  }
}
