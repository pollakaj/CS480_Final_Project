package partition;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import feature.StreetSegment;

public class GridPartitionPanel extends JPanel
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private GridPartition gridPart;
  private double cellSize;
  
  public GridPartitionPanel(GridPartition gridPart)
  {
    this.gridPart = gridPart;
    this.cellSize = gridPart.getCellSize();
    setOpaque(false);
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.RED);
    g2d.setStroke(new BasicStroke(1));
    
    Map<String, List<StreetSegment>> grid = gridPart.getGrid();
    for (String key : grid.keySet())
    {
      String[] keyParts = key.split("_");
      int cellX = Integer.parseInt(keyParts[0]);
      int cellY = Integer.parseInt(keyParts[1]);
      
      double startX = cellX * cellSize;
      double startY = cellY * cellSize;
      
      g2d.drawRect((int) startX, (int) startY, (int) cellSize, (int) cellSize);
    }
  }
}
