package graph;

/**
 * Label Manager for Candidates used in correcting algorithms.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface CandidateLabelManager extends LabelManager
{
  /**
   * Gets the candidate label for the specific manager.
   *
   * @return Label candidate label
   */
  public abstract Label getCandidateLabel();
}
