package partition;

import java.awt.geom.Point2D;
import java.util.List;

import feature.StreetSegment;

/**
 * SpatialPartion interface (thought about using the quad plane).
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code
 */
public interface SpatialPartition
{
  public abstract void addSegment(final StreetSegment segment);
  public List<StreetSegment> getNearbySegments(final double radius, 
      final Point2D local);
}
