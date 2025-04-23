package graph;

import feature.StreetSegment;

/**
 * Manages the Permanent and Candidate Labels.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface LabelManager
{
  /**
   * Must adjust the Label at the head node of the given StreetSegment.
   *
   * @param segment StreetSegment whose head node's label needs to be adjusted
   */
  public abstract void adjustHeadValue(final StreetSegment segment);
  
  /**
   * Gets the label from the corresponding intersection ID.
   *
   * @param intersectionID int intersection ID for the Label
   * @return Label with the given intersection ID
   */
  public abstract Label getLabel(final int intersectionID);
}
