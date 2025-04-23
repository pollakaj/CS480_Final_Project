package gui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * A Cartographer is a Strategy (in the sense of the Strategy Pattern) that a 
 * CartographyPanel uses to render the elements and the highlighted elements in 
 * a CartographyDocument.
 *
 * @author pollakaj
 * @version 1.0
 * 
 * @param <T> Generic Type for the type of shape in the map
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface Cartographer<T>
{
  /**
   * Transforms each of the elements and then render them.
   *
   * @param model CartographyDocument of the same type for the model component
   *  of the map.
   * @param g2 Graphics2D object for display purposes
   * @param at AffineTransform object used to translate, reflect, and scale the
   * elements 
   */
  public abstract void paintHighlights(final CartographyDocument<T> model,
      final Graphics2D g2, final AffineTransform at);
  
  /**
   * Transform each of the elements (using the AffineTransform it is passed) and
   *  then render them in the Color that was passed to the constructor.
   * @param model CartographyDocument of the same type for the model component
   *  of the map.
   * @param g2 Graphics2D object for display purposes
   * @param at AffineTransform object used to translate, reflect, and scale the
   * elements
   */
  public abstract void paintShapes(final CartographyDocument<T> model,
      final Graphics2D g2, final AffineTransform at);
}
