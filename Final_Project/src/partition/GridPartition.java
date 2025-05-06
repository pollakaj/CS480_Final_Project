package partition;

import java.awt.Shape;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import feature.StreetSegment;
import geography.GeographicShape;

public class GridPartition implements SpatialPartition
{
  private double cellSize;
  private Map<String, List<StreetSegment>> grid;
  
  public GridPartition(final double cellSize)
  {
    this.cellSize = cellSize;
    this.grid = new HashMap<>();
  }
  
  private String keyGeneration(final double x, final double y)
  {
    int cellX = (int) Math.floor(x / cellSize);
    int cellY = (int) Math.floor(y / cellSize);
    return cellX + "_" + cellY;
  }
  
  public double getCellSize()
  {
    return cellSize;
  }
  
  public Map<String, List<StreetSegment>> getGrid() 
  {
    return grid;
  }

  @Override
  public void addSegment(final StreetSegment segment)
  {
    GeographicShape geo = segment.getGeographicShape();
    Shape shape = geo.getShape();
    if (shape == null) return;
    
    double[] cords = new double[6];
    PathIterator it = shape.getPathIterator(null);
    
    double minX = Double.POSITIVE_INFINITY;
    double minY = Double.POSITIVE_INFINITY;
    double maxX = Double.NEGATIVE_INFINITY;
    double maxY = Double.NEGATIVE_INFINITY;
    
    while (!it.isDone())
    {
      int type = it.currentSegment(cords);
      if (type != PathIterator.SEG_CLOSE)
      {
        double x = cords[0];
        double y = cords[1];
        minX = Math.min(minX, x);
        minY = Math.min(minY, y);
        maxX = Math.max(maxX, x);
        maxY = Math.max(maxY, y);
      }
      it.next();
    }
    
    for (double x = minX; x <= maxX; x += cellSize)
    {
      for (double y = minY; y <= maxY; y += cellSize)
      {
        String key = keyGeneration(x, y);
        grid.computeIfAbsent(key, k -> new ArrayList<>()).add(segment);
      }
    }
  }

  @Override
  public List<StreetSegment> getNearbySegments(final double radius, 
      final Point2D local)
  {
    Set<StreetSegment> result = new HashSet<>();
    double minX = local.getX() - radius;
    double minY = local.getY() - radius;
    double maxX = local.getX() + radius;
    double maxY = local.getY() + radius;
    
    for (double x = minX; x <= maxX; x += cellSize)
    {
      for (double y = minY; y <= maxY; y += cellSize)
      {
        String key = keyGeneration(x, y);
        List<StreetSegment> segments = grid.get(key);
        if (segments != null) result.addAll(segments);
      }
    }
    return new ArrayList<>(result);
  }

}
