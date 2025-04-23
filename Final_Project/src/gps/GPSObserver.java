package gps;

/**
 * An observer of events related to GPS Data.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface GPSObserver
{
  /**
   * Parses the NMEA sentence it is passed and stores it as an attribute.
   *
   * @param sentence NMEA sentence for formatting GPS data.
   */
  public abstract void handleGPSData(final String sentence);
}
