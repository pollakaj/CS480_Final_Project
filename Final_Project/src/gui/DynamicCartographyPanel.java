package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import feature.StreetSegment;
import geography.GeographicShape;
import geography.MapProjection;
import gps.GPGGASentence;
import gps.GPSObserver;
import partition.GridPartition;

/**
 * A DynamicCartographyPanel object is a CartographyPanel object that can handle
 *  dynamic position/location updates.
 *
 * @param <T> The type of the data
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class DynamicCartographyPanel<T> extends CartographyPanel<T> implements GPSObserver
{
  /**
   * Default Serial version ID.
   */
  private static final long serialVersionUID = 1L;
  private GPGGASentence gpgga;
  private MapProjection proj;
  private GridPartition gridPartition;
  //private StreetSegment matchedSegment;
  private Point2D matchedPoint;

  /**
   * Constructor to initialize cartography doucment, cartographer interface and
   *  map projection.
   *
   * @param model CartographyDocument<T> the model to use
   * @param cartographer Cartographer<T> the cartographer to use
   * @param proj MapProjection the map projection to use
   */
  public DynamicCartographyPanel(final CartographyDocument<T> model, 
      final Cartographer<T> cartographer, final MapProjection proj)
  {
    super(model, cartographer);
    this.proj = proj;
  }
  
  /**
   * Develops the map image and dot following gps data. Invokes the street 
   * network, projects and transforms the current position.
   * 
   * @param g Graphics rendering engine to be used
   */
  public void paint(final Graphics g)
  {
    Graphics2D g2 = (Graphics2D) g;
    
    if (gpgga != null)
    {
      double lat = gpgga.getLatitude();
      double longi = gpgga.getLongitude();
      double[] projection = proj.forward(new double[] {longi, lat});

      if (zoomStack.isEmpty())
      {
        double width = 2000.0;
        double height = 2000.0;
        Rectangle2D.Double rect = new Rectangle2D.Double(
            projection[0] - width / 2, projection[1] - height / 2, width, 
            height);

        zoomStack.addFirst(rect);
      }

      Rectangle displayBounds = g2.getClipBounds();
      Rectangle2D viewBounds = zoomStack.peekFirst();
      AffineTransform at = displayTransform.getTransform(displayBounds, 
          viewBounds);

      super.paint(g);
      
      double[] displayCoords = new double[2];
      at.transform(projection, 0, displayCoords, 0, 1);
      
      g2.setColor(Color.RED);
      g2.fillOval((int) displayCoords[0] - 4, (int) displayCoords[1] - 4, 8, 8);
      
      if (matchedPoint != null)
      {
        double[] matchCords = new double[2];
        double[] matchSource = new double[] {matchedPoint.getX(), 
            matchedPoint.getY()};
        
        at.transform(matchSource, 0, matchCords, 0, 1);
        g2.setColor(Color.BLUE);
        g2.fillOval((int) matchCords[0] - 4, (int) matchCords[1] - 4, 8, 8);
      }
    }
  }
  
  public void setGridPartition(final GridPartition partition)
  {
    this.gridPartition = partition;
  }

  @Override
  public void handleGPSData(final String sentence)
  {
    this.gpgga = GPGGASentence.parseGPGGA(sentence);

    double lat = gpgga.getLatitude();
    double lon = gpgga.getLongitude();
    double[] projection = proj.forward(new double[] {lon, lat});
    Point2D.Double gpsPoint = new Point2D.Double(projection[0], projection[1]);
    
    double searchRadius = 200.0;
    List<StreetSegment> nearby = gridPartition.getNearbySegments(searchRadius, 
        gpsPoint);
    
    double minDist = Double.MAX_VALUE;
    //StreetSegment bestSeg = null;
    Point2D bestPoint = null;
    
    for (StreetSegment segment : nearby)
    {
      GeographicShape shape = segment.getGeographicShape();
      Point2D closest = getClosestPoint(gpsPoint, shape);
      double dist = gpsPoint.distance(closest);
      
      if (dist < minDist)
      {
        minDist = dist;
        //bestSeg = segment;
        bestPoint = closest;
      }
    }
    
    //matchedSegment = bestSeg;
    matchedPoint = bestPoint;
    repaint();
  }
  
  private Point2D getClosestPoint(final Point2D target, 
      final GeographicShape shape)
  {
    double minDist = Double.MAX_VALUE;
    Point2D closest = null;
    double[] cords = new double[6];
    
    PathIterator it = shape.getShape().getPathIterator(null);
    Point2D.Double prev = null;
    
    while (!it.isDone())
    {
      int type = it.currentSegment(cords);
      if (type == PathIterator.SEG_MOVETO || type == PathIterator.SEG_LINETO)
      {
        Point2D.Double curr = new Point2D.Double(cords[0], cords[1]);
        if (prev != null)
        {
          Point2D point = getClosestSegmentPoint(target, prev, curr);
          double dist = point.distanceSq(target);
          
          if (dist < minDist)
          {
            minDist = dist;
            closest = point;
          }
        }
        prev = curr;
      }
      it.next();
    }
    return closest;
  }
  
  private Point2D getClosestSegmentPoint(Point2D p, Point2D a, Point2D b)
  {
    double ax = a.getX();
    double ay= a.getY();
    double bx = b.getX();
    double by = b.getY();
    double px = p.getX();
    double py = p.getY();
    double dx = bx - ax;
    double dy = by - ay;
    
    if (dx == 0 && dy == 0) return new Point2D.Double(ax, ay);
    
    double t = ((px - ax) * dx + (py - ay) * dy) / (dx * dx + dy * dy);
    t = Math.max(0, Math.min(1, t));
    
    return new Point2D.Double(ax + t * dx, ay + t * dy);
  }

}
