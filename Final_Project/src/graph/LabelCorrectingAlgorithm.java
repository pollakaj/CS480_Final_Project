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
    List<String> highlightIDs = new ArrayList<>();

    for (int i = 0; i < net.size(); i++) 
    {
      labels.getLabel(i).setValue(Double.POSITIVE_INFINITY);
    }
    labels.getLabel(origin).setValue(0.0);

    if (labels instanceof CandidateLabelList) 
      ((CandidateLabelList) labels).addCandidate(origin);

    int workingNode = origin;

    while (true) 
    {
      Label currLabel = labels.getLabel(workingNode);
      if (currLabel == null) break;

      Intersection currIntersection = net.getIntersection(workingNode);
      for (StreetSegment segment : currIntersection.getOutbound()) 
      {
        int outID = segment.getHead();
        Label outLabel = labels.getLabel(outID);

        double possibleDistance = currLabel.getValue() 
            + segment.getLength();
        if (possibleDistance < outLabel.getValue()) 
        {
          outLabel.adjustValue(possibleDistance, segment);

          if (labels instanceof CandidateLabelList)
              ((CandidateLabelList) labels).addCandidate(outID);
          highlightIDs.add(segment.getID());
          notifyStreetSegmentObservers(highlightIDs);
        }
      }

      Label nextLabel = labels.getCandidateLabel();
      if (nextLabel == null) break;
      workingNode = nextLabel.getID();
    }

    int currID = destination;
    while (currID != origin)
    {
      StreetSegment segment = labels.getLabel(currID).getPredecessor();
      if (segment != null) 
      {
        result.put(segment.getID(), segment);
        currID = segment.getTail();
      } 
      else break;
    }

    return result;
  }
}
