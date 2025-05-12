package graph;

import java.util.*;

import feature.StreetSegment;

/**
 * A CandidateLabelManager that can operate as a stack (when the policy is NEWEST)
 * or a queue (when the policy is OLDEST).
 */
public class CandidateLabelList extends AbstractLabelManager implements CandidateLabelManager 
{
  public static final String NEWEST = "N";
  public static final String OLDEST = "O";
  
  private List<Integer> candidates;
  private String policy;
  
  /**
   * Explicit Value Constructor.
   * 
   * @param networkSize The size of the network
   * @param policy NEWEST or OLDEST
   */
  public CandidateLabelList(final String policy, final int networkSize)
  {
    super(networkSize);
    this.policy = policy;
    candidates = new LinkedList<Integer>();
  }
  
  /**
   * Get a Label that has been adjusted/modified (i.e., a candidate).
   * 
   * If the policy is NEWEST it will get the most recently added
   * candidate. Otherwise it will get the oldest candidate. 
   * 
   * @return The appropriate Label
   */
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

  @Override
  public void adjustHeadValue(StreetSegment segment)
  {
    int tailID = segment.getTail();
    int headID = segment.getHead();
    Label label = labels[headID];

    double valueOfCurrent = labels[tailID].getValue();
    double newValue = valueOfCurrent + segment.getLength();
    boolean adjusted = label.adjustValue(newValue, segment);
    
    if (adjusted) candidates.addLast(headID);
  }
  
}
