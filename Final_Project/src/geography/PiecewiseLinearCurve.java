package geography;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

/**
 * PiecewiseLinearCurve Geographic Shape composed of connected line segments.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class PiecewiseLinearCurve extends AbstractGeographicShape
{
  protected Path2D.Double shape;

  /**
   * Constructs a piecewise linear curve Geographic Shape with an ID.
   *
   * @param id Identification number for the shape
   */
  public PiecewiseLinearCurve(final String id)
  {
    super(id);
    this.shape = new Path2D.Double();
  }
  
  /**
   * Overloaded constructor with shape parameter.
   *
   * @param id Identification number for the shape
   * @param shape Path2D.Double object representing the shape
   */
  public PiecewiseLinearCurve(final String id, final Path2D.Double shape)
  {
    super(id);
    this.shape = shape;
  }
  
  /**
   * Adds a point to the PiecewiseLinearCurve by drawing a line to it.
   *
   * @param point double[] point to be added
   */
  public void add(final double[] point)
  {
    if (this.shape.getCurrentPoint() == null) 
      this.shape.moveTo(point[0], point[1]);
    else this.shape.lineTo(point[0], point[1]);
  }
  
  /**
   * The append() method must append the given addition to the shape attribute 
   * of the PiecewiseLinearCurve.
   *
   * @param addition Shape to be connected to the PiecewiseLinearCurve
   * @param connect boolean determines whether the addition should be connected
   *  to the shape attribute or not.
   */
  public void append(final Shape addition, final boolean connect)
  {   
    PathIterator it = addition.getPathIterator(null);
    double[] cords = new double[6];
    boolean firstPoint = true;
    
    while (!it.isDone())
    {
      int type = it.currentSegment(cords);
      if (type == PathIterator.SEG_MOVETO)
      {
        if (firstPoint)
        {
          firstPoint = false;
          if (connect && this.shape.getCurrentPoint() != null) 
            this.shape.lineTo(cords[0], cords[1]);
          else this.shape.moveTo(cords[0], cords[1]);
        } 
      } else if (type == PathIterator.SEG_LINETO) this.shape.lineTo(cords[0], 
          cords[1]);

      it.next();
    }
  }

  @Override
  public Shape getShape()
  {
    return this.shape;
  }
}
