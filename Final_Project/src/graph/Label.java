package graph;

import feature.StreetSegment;

/**
 * Label objects are used in label setting and label correcting algorithms.
 * They maintain information about the shortest path to a particular 
 * Intersection that has been found thus far, including the length of the path
 * and the incoming StreetSegment.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class Label
{
  private boolean permanent;
  private double value;
  private int id;
  private StreetSegment predecessor;
  
  /**
   * Label Constructor.
   */
  public Label() 
  {
    this(-1);
  }
  
  /**
   * Explicit value constructor with id attribute.
   *
   * @param id int ID for the Label
   */
  public Label(final int id)
  {
    this.id = id;
    this.permanent = false;
    this.value = Double.POSITIVE_INFINITY;
  }
  
  /**
   * Updates the value and predecessor if the possibleValue is less than the 
   * current value.
   *
   * @param possibleValue double value to check against Label's value.
   * @param possiblePredecessor StreetSegment street segment associated with 
   * possible value to update predecessor
   */
  public boolean adjustValue(final double possibleValue, 
      final StreetSegment possiblePredecessor)
  {
    boolean result = false;
    if (possibleValue < value) 
    {
      value = possibleValue;
      predecessor = possiblePredecessor;
      result = true;
    }
    
    return result;
  }
  
  /**
   * Getter for ID Value.
   * 
   * @return int ID of Label
   */
  public int getID()
  {
    return id;
  }
  
  /**
   * Getter for Label's predecessor.
   * 
   * @return StreetSegment predecessor of Label
   */
  public StreetSegment getPredecessor()
  {
    return predecessor;
  }
  
  /**
   * Getter for Label Value.
   * 
   * @return int value of Label
   */
  public double getValue()
  {
    return value;
  }
  
  /**
   * Checks if the Label is permanent.
   * 
   * @return boolean permanent attribute's value
   */
  public boolean isPermanent()
  {
    return permanent;
  }
  
  /**
   * Setter for permanent attribute.
   */
  public void makePermanent()
  {
    permanent = true;
  }
  
  /**
   * Setter for label value.
   *
   * @param value double value to assign to the Label
   */
  public void setValue(final double value)
  {
    this.value = value;
  }
}
