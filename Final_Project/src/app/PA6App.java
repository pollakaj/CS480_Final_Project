package app;

import geography.*;
import gui.*;
import partition.GridPartition;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;

import javax.swing.*;

import com.fazecast.jSerialComm.SerialPort;

import dataprocessing.Geocoder;
import feature.*;
import gps.*;
import graph.CandidateLabelList;
import graph.CandidateLabelManager;
import graph.PermanentLabelManager;
import graph.LabelCorrectingAlgorithm;
import graph.LabelSettingAlgorithm;
import graph.PathFindingWorker;
import graph.PermanentLabelList;
import graph.ShortestPathAlgorithm;
import graph.StreetNetwork;

/**
 * The application for PA6.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class PA6App implements ActionListener, Runnable, StreetSegmentObserver, 
    PropertyChangeListener, RouteRecalculationListener
{
  private static final int SET_DESTINATION = 0;
  private static final int SET_ORIGIN = 1;
  private static final int CALCULATE_PATH = 2;

  private static final String CALCULATE = "Calculate";
  private static final String EXIT = "Exit";
  private static final String DESTINATION = "Destination";
  private static final String ORIGIN = "Origin";
  
  private static final String TAB = "\t";
  private DynamicCartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> document;
  private GeocodeDialog dialog;
  private int mode;
  private JFrame frame;
  private ShortestPathAlgorithm alg;
  private PathFindingWorker task;
  private StreetSegment originSegment, destinationSegment;
  private StreetNetwork network;

  /**
   * The code to be executed in the event dispatch thread.
   */
  @Override
  public void run()
  {
    try
    {
      InputStream isgeo = new FileInputStream(new File("rockingham-streets.geo"));
      AbstractMapProjection proj = new ConicalEqualAreaProjection(-96.0, 37.5, 29.5, 45.5);
      GeographicShapesReader gsReader = new GeographicShapesReader(isgeo, proj);
      CartographyDocument<GeographicShape> geographicShapes = gsReader.read();
      System.out.println("Read the .geo file");

      InputStream iss = new FileInputStream(new File("rockingham-streets.str"));
      StreetsReader sReader = new StreetsReader(iss, geographicShapes);
      Map<String, Street> streets = new HashMap<String, Street>();
      document = sReader.read(streets);
      System.out.println("Read the .str file");
      
      // Set up grid
      double cellSize = 100.0;
      GridPartition gridPart = new GridPartition(cellSize);
      for (StreetSegment segment : document)
      {
        gridPart.addSegment(segment);
      }

      panel = new DynamicCartographyPanel<StreetSegment>(document, 
          new StreetSegmentCartographer(), proj);
      
      panel.setGridPartition(gridPart);
      // Initialize Listener
      panel.setRouteRecalculationListener(this);
      
      frame = new JFrame("Map");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 600);
      
      network = StreetNetwork.createStreetNetwork(streets);

      JMenuBar menuBar = new JMenuBar();
      frame.setJMenuBar(menuBar);
      JMenuItem item;
      JMenu menu;

      menu = new JMenu("File");
      menuBar.add(menu);

      item = new JMenuItem(EXIT);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("Geocode");
      menuBar.add(menu);

      item = new JMenuItem(ORIGIN);
      item.addActionListener(this);
      menu.add(item);
      item = new JMenuItem(DESTINATION);
      item.addActionListener(this);
      menu.add(item);

      menu = new JMenu("Path");
      menuBar.add(menu);

      item = new JMenuItem(CALCULATE);
      item.addActionListener(this);
      menu.add(item);
      frame.setContentPane(panel);
      
      // Find the right serial port
      SerialPort[] ports = SerialPort.getCommPorts();
      String gpsPath = null;
      for (SerialPort port:ports)
      {
        System.out.println("Description: " + port.getPortDescription());
        System.out.println("System Port Path: " + port.getSystemPortPath());
        String description = port.getPortDescription();
        String path = port.getSystemPortPath();
        if (description.indexOf("GPS") >= 0) gpsPath = path;
      }
    
      // Setup the serial port
      SerialPort gps = SerialPort.getCommPort(gpsPath); 
      gps.openPort();
      gps.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
      InputStream is = gps.getInputStream();
      
    // Setup the GPSReaderTask
//      GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
//      gpsReader.addGPSObserver(this);
//      frame.setVisible(true);
//      gpsReader.execute();
      
//      GPSSimulator gps = new GPSSimulator("rockingham.gps");
//      InputStream is = gps.getInputStream();

      
      GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
      gpsReader.addGPSObserver(panel);
      frame.setVisible(true);
      gpsReader.execute();

      // Set up property change listener to trigger route recalculation
      panel.addPropertyChangeListener("recalculateRoute", evt -> 
      {
        StreetSegment newOrigin = (StreetSegment) evt.getNewValue();
        if (newOrigin != null)
        {
          originSegment = newOrigin;
          routeRecalculated(newOrigin);
        }
      });
      frame.setVisible(true);
      Geocoder geocoder = new Geocoder(geographicShapes, document, streets);
      dialog = new GeocodeDialog(frame, geocoder, panel);
      dialog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
      dialog.addStreetSegmentObserver(this);
      dialog.setLocation((int)frame.getBounds().getMaxX(), 
          (int)frame.getBounds().getY());
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(frame, 
          ioe.toString(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }
  
  // Uses a path finding worker and label correcting algorithm to recalculate 
  // the route with the new starting point.
  public void recalculateRoute()
  {
    if (originSegment != null && destinationSegment != null)
    {
      if (task != null && !task.isDone())
      {
        task.cancel(true);
      }

      panel.clearRoute();
      panel.repaint();
      CandidateLabelManager labels = 
          new CandidateLabelList(CandidateLabelList.NEWEST, network.size());
      alg = new LabelCorrectingAlgorithm(labels);
      task = new PathFindingWorker(alg, originSegment.getHead(), 
          destinationSegment.getHead(), network, document, panel);
      task.addPropertyChangeListener(this);
      task.execute();
    }
  }

  @Override
  public void propertyChange(final PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("progress")) 
    {
      System.out.println("progress...");
    }
    else if (evt.getPropertyName().equals("state")) 
    {
      if (evt.getNewValue().equals(SwingWorker.StateValue.DONE)) 
      {
        try
        {
          Map<String, StreetSegment> path = task.get();
          document.setHighlighted(path);
          panel.repaint();
          task = null;
        }
        catch (InterruptedException | ExecutionException e)
        {
          JOptionPane.showMessageDialog(frame, "Interrupted", "Exception", 
              JOptionPane.ERROR_MESSAGE);
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  public void handleStreetSegments(final List<String> segmentIDs)
  {
    HashMap<String, StreetSegment> highlighted = new HashMap<String, StreetSegment>();
    for (String id: segmentIDs) 
    {
      highlighted.put(id, document.getElement(id));
    }
    document.setHighlighted(highlighted);
    panel.repaint();

    if (segmentIDs.size() > 0)
    {
      if (mode == SET_ORIGIN)
      {
        dialog.setVisible(true);
        originSegment = highlighted.get(segmentIDs.get(0));
        System.out.println(originSegment + TAB + destinationSegment);
      }
      else if (mode == SET_DESTINATION)
      {
        dialog.setVisible(true);
        destinationSegment = highlighted.get(segmentIDs.get(0));
        System.out.println(originSegment + TAB + destinationSegment);
      }
    }
  }

  @Override
  public void actionPerformed(final ActionEvent evt)
  {
    String ac = evt.getActionCommand();

    if (ac.equals(ORIGIN)) mode = SET_ORIGIN;
    else if (ac.equals(DESTINATION)) mode = SET_DESTINATION;
    else if (ac.equals(CALCULATE)) mode = CALCULATE_PATH;

    if (ac.equals(ORIGIN) || ac.equals(DESTINATION))
    {
      if (!dialog.isVisible())
      {
        dialog.setLocation((int)frame.getBounds().getMaxX(), 
            (int)frame.getBounds().getY());
        dialog.setVisible(true);
      }
    }

    if (ac.equals(CALCULATE))
    {
      // TODO CONSTUCT THE ALGORITHM   --   Use a Label Setting Algorithm
      PermanentLabelManager labels = new PermanentLabelList(network.size());
      //PermanentLabelManager labels = new PermanentLabelBuckets(network.size());
      //PermanentLabelManager labels = new PermanentLabelHeap(5, network.size());
      alg = new LabelSettingAlgorithm(labels);
      
      // TODO CONSTRUCT THE ALGORITHM   --   Use a LabelCorrecting Algorithm
      //CandidateLabelManager labels = 
      //  new CandidateLabelList(CandidateLabelList.NEWEST, network.size()); 
      //alg = new LabelCorrectingAlgorithm(labels);

      // Construct the SwingWorker
      task = new PathFindingWorker(alg, 
          originSegment.getHead(), destinationSegment.getHead(), network, 
          document, panel);
      task.addPropertyChangeListener(this);
      // TODO SET TO true IF YOU WANT TO SEE INTERMEDIATE RESULTS
      task.shouldShowIntermediateResults(false);
      dialog.setVisible(false);

      // Construct the dialog box
      BackgroundTaskDialog<Map<String, StreetSegment>, String> btd = 
          new BackgroundTaskDialog<Map<String, StreetSegment>, String>(
              frame, "Calculating...", task);
      
      // Execute
      btd.execute();
    }

    if (ac.equals(EXIT))
    {
      dialog.dispose();
      frame.dispose();
      System.exit(0);
    }
  }

  @Override
  public void routeRecalculated(StreetSegment newOrigin)
  {
    originSegment = newOrigin;
    recalculateRoute();
  }
}
