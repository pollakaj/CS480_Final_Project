package graph;

import feature.StreetSegment;

/**
 * Must search through all non-permanent Label object in the List.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class PermanentLabelList extends AbstractLabelManager implements PermanentLabelManager
{

  /**
   * Permanent Label List constructor to initialize labels in list and its size.
   *
   * @param networkSize Size of street network for label list
   */
  public PermanentLabelList(final int networkSize)
  {
    super(networkSize);
    
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
    Label headLabel = getLabel(headID);
    Label tailLabel = getLabel(tailID);

    if (headLabel.isPermanent()) return;

    double possibleValue = tailLabel.getValue() + segment.getLength();

    headLabel.adjustValue(possibleValue, segment);
  }

  @Override
  public Label getSmallestLabel()
  {
    Label smallest = null;

    for (Label label : labels) 
    {
      if (!label.isPermanent()) 
      {
        if (smallest == null || label.getValue() < smallest.getValue()) 
          smallest = label;
      }
    }
    return smallest;
  }

  @Override
  public void makePermanent(final int intersectionID)
  {
    Label label = getLabel(intersectionID);
    if (label != null) label.makePermanent();
  }
}
