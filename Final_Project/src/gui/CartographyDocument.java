package gui;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Model component for the data regarding the displayed objects in the map.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * @param <T> Generic Type for the type of shape in the map
 *
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class CartographyDocument<T> implements Iterable<T>
{
  private Map<String, T> highlighted;
  private Map<String, T> elements;
  private Rectangle2D.Double bounds;
  
  /**
   * Constructor to initialize attributes and create the model.
   *
   * @param elements Map<String, T> of elements (data) for the document
   * @param bounds Rectangle2D.Double bounds of the model
   */
  public CartographyDocument(final Map<String, T> elements, 
      final Rectangle2D.Double bounds)
  {
    this.elements = elements;
    this.bounds = bounds;
    this.highlighted = new HashMap<String, T>();
  }
  
  /**
   * Must return the smallest rectangle that bounds all of the elements in the 
   * CartographyDocument.
   *
   * @return Rectangle2D.Double, smallest rectangle that bounds all of the 
   * elements in the document
   */
  public Rectangle2D.Double getBounds()
  {
    return this.bounds;
  }
  
  /**
   * Gets the element in the document mapped to by the passed ID.
   *
   * @param id String identification number of the object
   * @return T object with the passed ID
   */
  public T getElement(final String id)
  {
    return this.elements.get(id);
  }
  
  /**
   * Must return an Iterator containing the elements in the CartographyDocument 
   * that are to be highlighted (which may be empty).
   *
   * @return Return Iterator<T> an Iterator containing the elements to be 
   * highlighted
   */
  public Iterator<T> highlighted()
  {
    return this.highlighted.values().iterator();
  }

  @Override
  public Iterator<T> iterator()
  {
    return this.elements.values().iterator();
  }
  
  /**
   * Setter for the highlighted attribute.
   *
   * @param highlighted Map<String, T> Mapping of objects to be highlighted
   */
  public void setHighlighted(final Map<String, T> highlighted)
  {
    this.highlighted = highlighted;
  }

}
