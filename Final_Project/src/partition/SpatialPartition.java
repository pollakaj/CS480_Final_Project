package partition;

import java.awt.geom.Point2D;
import java.util.List;

import feature.StreetSegment;

public interface SpatialPartition
{
  public abstract void addSegment(final StreetSegment segment);
  public List<StreetSegment> getNearbySegments(final double radius, 
      final Point2D local);
}
