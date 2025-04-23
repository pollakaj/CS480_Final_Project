package gps;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * A SwingWorker that processes NMEA sentences to deliver gps data for the 
 * CarographyPanel.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class GPSReaderTask extends SwingWorker<Void, String> implements GPSSubject
{
  private BufferedReader in;
  private String[] sentences;
  private Collection<GPSObserver> observers;
  
  /**
   * Constructor that reads from the input stream and initializes NMEA sentences
   *  to be processed.
   *
   * @param is InputStream to read from
   * @param sentences String... variable number of NMEA sentences
   */
  public GPSReaderTask(final InputStream is, final String...sentences)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.sentences = sentences;
    this.observers = new ArrayList<>();
  }
  
  @Override
  public void process(final List<String> lines)
  {
    for (String line : lines)
    {
      for (String sentence : sentences)
      {
        if (line.startsWith("$" + sentence))
        {
          notifyGPSObservers(line);
          break;
        }
      }
    }
  }

  @Override
  public void addGPSObserver(final GPSObserver observer)
  {
    if (!observers.contains(observer)) observers.add(observer);
  }

  @Override
  public void notifyGPSObservers(final String sentence)
  {
    for (GPSObserver observer : observers)
    {
      observer.handleGPSData(sentence);
    }
  }

  @Override
  public void removeGPSObserver(final GPSObserver observer)
  {
    observers.remove(observer);
  }

  @Override
  protected Void doInBackground() throws Exception
  {
    String line;
    while (!isCancelled() && (line = in.readLine()) != null)
    {
      publish(line);
    }
    
    return null;
  }

}
