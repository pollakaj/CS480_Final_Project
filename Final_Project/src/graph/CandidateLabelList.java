package graph;

import java.util.ArrayList;
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
    this.candidates = new ArrayList<>();
    
    for (int i = 0; i < networkSize; i++) 
    {
      this.labels[i] = new Label(i);
    }
  }

  @Override
  public void adjustHeadValue(final StreetSegment segment)
  {
    int headID = segment.getHead();
    int tailID = segment.getTail();
    Label tailLabel = getLabel(tailID);
    Label headLabel = getLabel(headID);

    double possibleValue = tailLabel.getValue() + segment.getLength();
    headLabel.adjustValue(possibleValue, segment);
    
    if (!candidates.contains(headID)) candidates.add(headID);
  }

  @Override
  public Label getCandidateLabel()
  {
    if (candidates.isEmpty()) return null;

    int id;
    if (policy.equals(NEWEST)) id = candidates.remove(candidates.size() - 1);
    else id = candidates.remove(0);

    return getLabel(id);
  }
  
  /**
   * Helper method for adding a candidate to the candidates list.
   *
   * @param id int ID for the candidate to add.
   */
  public void addCandidate(final int id) 
  {
    if (!candidates.contains(id)) candidates.add(id);
  }
}
