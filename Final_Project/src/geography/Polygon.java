package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

/**
 * Polygon closed Geographic 2D Shape formed by connected line segments.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class Polygon extends PiecewiseLinearCurve
{
  private boolean closed = false;

  /**
   * Constructs a polygon object with an identification number.
   *
   * @param id String identification number of Polygon
   */
  public Polygon(final String id)
  {
    super(id);
  }
  
  /**
   * Overloaded constructor with shape parameter.
   *
   * @param id String identification number of Polygo
   * @param shape Path2D.Double object representing the shape
   */
  public Polygon(final String id, final Path2D.Double shape)
  {
    super(id, shape);
    this.closed = isClosed(shape);
    if (!this.closed)
    {
      this.shape.closePath();
      this.closed = true;
    }
  }
  
  @Override
  public Shape getShape()
  {
    if (!this.closed)
    {
      this.shape.closePath();
      this.closed = true;
    }
    return this.shape;
  }
  
  /**
   * Helper method to determine if the Polygon is closed.
   *
   * @param shape Shape object of the Polygon
   * @return boolean if the Polygon is closed or not
   */
  private boolean isClosed(final Shape shape)
  {
    PathIterator it = shape.getPathIterator(null);

    while (!it.isDone())
    {
      if (it.currentSegment(new double[2]) == PathIterator.SEG_CLOSE)
        return true;
      it.next();
    }
    return false;
  }
}
