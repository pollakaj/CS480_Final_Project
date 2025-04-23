package graph;

import feature.StreetSegment;

/**
 * Abstract class that manages an array of Labels.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public abstract class AbstractLabelManager implements LabelManager
{
  protected Label[] labels;
  
  /**
   * Constructor initializing the labels array.
   *
   * @param networkSize Size of the Street Network
   */
  public AbstractLabelManager(final int networkSize)
  {
    labels = new Label[networkSize];
  }

  @Override
  public abstract void adjustHeadValue(final StreetSegment segment);

  @Override
  public Label getLabel(final int intersectionID)
  {
    return labels[intersectionID];
  }
}
