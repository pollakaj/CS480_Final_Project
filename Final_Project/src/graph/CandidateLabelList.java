package graph;

import java.util.LinkedList;
import java.util.List;

import feature.StreetSegment;

/**
 * CandidateLabelManager that utilizes a List for the candidates.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class CandidateLabelList extends AbstractLabelManager implements CandidateLabelManager
{
  public static final String NEWEST = "N";
  public static final String OLDEST = "O";
  private List<Integer> candidates;
  private String policy;

  /**
   * Constructor to initialize the policy and labels in the list.
   *
   * @param policy String policy for added Candidate Label
   * @param networkSize int Street Network size for the label
   */
  public CandidateLabelList(final String policy, final int networkSize)
  {
    super(networkSize);
    this.policy = policy;
    this.candidates = new LinkedList<Integer>();
  }

  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int headID = segment.getHead();
    int tailID = segment.getTail();
    Label label = labels[headID];
    
    double currVal = labels[tailID].getValue();
    double possibleValue = currVal + segment.getLength();
    label.adjustValue(possibleValue, segment);
    
    if (!candidates.contains(headID)) candidates.add(headID);
  }

  @Override
  public Label getCandidateLabel()
  {
    Label result = null;
    if (candidates.size() != 0)
    {
      if (policy.equals(NEWEST)) result = labels[candidates.removeLast()];
      else result = labels[candidates.removeFirst()];
    }

    return result;
  }
}
