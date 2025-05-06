package app;

import geography.*;
import gui.*;
import partition.GridPartition;
import partition.GridPartitionPanel;

import java.io.*;
import java.util.*;
import javax.swing.*;

import com.fazecast.jSerialComm.SerialPort;

import feature.*;
import gps.*;

/**
 * The application for PA6.
 * 
 * @author Prof. David Bernstein, James Madison University
 * @version 1.0
 */
public class PA6App implements Runnable
{
  private DynamicCartographyPanel<StreetSegment> panel;
  private CartographyDocument<StreetSegment> document;
  private JFrame frame;

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
      
      double cellSize = 100.0;
      GridPartition gridPart = new GridPartition(cellSize);
      for (StreetSegment segment : document)
      {
        gridPart.addSegment(segment);
      }

      panel = new DynamicCartographyPanel<StreetSegment>(document, new StreetSegmentCartographer(), proj);
      
      panel.setGridPartition(gridPart);
      GridPartitionPanel gridPanel = new GridPartitionPanel(gridPart);
      panel.add(gridPanel);
      
      frame = new JFrame("Map");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(600, 600);
      frame.setContentPane(panel);
      
//      // Find the right serial port
//      SerialPort[] ports = SerialPort.getCommPorts();
//      String gpsPath = null;
//      for (SerialPort port:ports)
//      {
//        String description = port.getPortDescription();
//        String path = port.getSystemPortPath();
//        if (description.indexOf("GPS") >= 0) gpsPath = path;
//      }
//    
//      // Setup the serial port
//      SerialPort gps = SerialPort.getCommPort(gpsPath); 
//      gps.openPort();
//      gps.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
//      InputStream is = gps.getInputStream();
      
      // Setup the GPSReaderTask
//      GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
//      gpsReader.addGPSObserver(this);
//      frame.setVisible(true);
//      gpsReader.execute();
      
      GPSSimulator gps = new GPSSimulator("rockingham.gps");
      InputStream is = gps.getInputStream();

      
      GPSReaderTask gpsReader = new GPSReaderTask(is, "GPGGA");
      gpsReader.addGPSObserver(panel);
      frame.setVisible(true);
      gpsReader.execute();

      frame.setVisible(true);
    }
    catch (IOException ioe)
    {
      JOptionPane.showMessageDialog(frame, 
          ioe.toString(),
          "Error", JOptionPane.ERROR_MESSAGE);
    }
  }

}
