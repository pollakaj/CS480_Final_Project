package graph;

/**
 * Manage the Permanent Label collections.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface PermanentLabelManager extends LabelManager
{
  /**
   * Must return a Label that has the minimum value among all non-permanent 
   * Label objects.
   *
   * @return Label smallest Label in Permanent Label collection
   */
  public abstract Label getSmallestLabel();
  
  /**
   * Makes the Label with given intersectionID permanent.
   *
   * @param intersectionID int ID of label to make permanent
   */
  public abstract void makePermanent(final int intersectionID);
}
