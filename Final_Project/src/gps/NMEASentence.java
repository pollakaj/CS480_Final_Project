package gps;

/**
 * Abstract class for NMEA sentences, performs data acquisition.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public abstract class NMEASentence
{
  /**
   * Algorithm for receiver to validate sentences, XORs each character together.
   *
   * @param s String message sentence
   * @param originalChecksum int checksum to be added to
   * @return int value of the updated checksum
   */
  public static int addToChecksum(final String s, final int originalChecksum)
  {
    int checksum = originalChecksum;
    for (int i = s.indexOf("$") + 1; i <= s.indexOf("*"); i++)
    {
      checksum ^= (int) (s.charAt(i));
    }
    return checksum;
  }
  
  /**
   * Converts NMEA latitude format to the standard decimal degrees.
   *
   * @param latitudeString String Latitude from NMEA string to be converted
   * @return double standard decimal degree value of latitude
   */
  public static double convertLatitude(final String latitudeString)
  {
    double lat;
    try
    {
      lat = Double.parseDouble(latitudeString);
    }
    catch (NumberFormatException nfe)
    {
      lat = 0;
    }
    int degrees = (int) (lat / 100);
    double mins = lat - (degrees * 100);
    return degrees + (mins / 60.0);
  }
  
  /**
   * Converts NMEA longitude format to the standard decimal degrees.
   *
   * @param longitudeString String longitude from NMEA string to be converted
   * @return double standard decimal degree value of longitude
   */
  public static double convertLongitude(final String longitudeString)
  {
    double longi;
    try
    {
      longi = Double.parseDouble(longitudeString);
    }
    catch (NumberFormatException nfe)
    {
      longi = 0;
    }
    int degrees = (int) (longi / 100);
    double mins = longi - (degrees * 100);
    return degrees + (mins / 60.0);
  }
}
