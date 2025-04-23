package gps;

/**
 * A generator of events related to GPS Data.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface GPSSubject
{
  /**
   * Adds a GPSObserver.
   *
   * @param observer GPSObserver to be added
   */
  public abstract void addGPSObserver(final GPSObserver observer);
  
  /**
   * Notifies the GPSObservers of an NMEA Sentence from GPS Data.
   *
   * @param sentence String NMEA sentence
   */
  public abstract void notifyGPSObservers(final String sentence);
  
  /**
   * Removes a GPSObserver.
   *
   * @param observer GPSObserver to be removed
   */
  public abstract void removeGPSObserver(final GPSObserver observer);
}
