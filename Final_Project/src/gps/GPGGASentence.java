package gps;

/**
 * GPGGA sentence is a type of NMEA sentence for GPS Fix Data.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class GPGGASentence extends NMEASentence
{
  private String time;
  private double latitude;
  private double longitude;
  private int fixType;
  private int satellites;
  private double dilution;
  private double altitude;
  private String altitudeUnits;
  private double seaLevel;
  private String geoidUnits;
  
  /**
   * Constructor for GPGGA sentence initializes all relevant attributes.
   *
   * @param time String current time
   * @param latitude double latitude in DDMM.MMM format
   * @param longitude double longitude in DDDMM.MMM format
   * @param fixType int 0 for no fix, 1 for gps, 2 for dgps
   * @param satellites int number of satellites used for fix
   * @param dilution double horizontal dilution of precision
   * @param altitude double altitude above mean sea level
   * @param altitudeUnits String altitude units
   * @param seaLevel double height of mean sea level
   * @param geoidUnits String Units of the above geoid separation
   */
  public GPGGASentence(final String time, final double latitude, 
      final double longitude, final int fixType, final int satellites, 
      final double dilution, final double altitude, final String altitudeUnits, 
      final double seaLevel, final String geoidUnits)
  {
    this.time = time;
    this.latitude = latitude;
    this.longitude = longitude;
    this.fixType = fixType;
    this.satellites = satellites;
    this.dilution = dilution;
    this.altitude = altitude;
    this.altitudeUnits = altitudeUnits;
    this.seaLevel = seaLevel;
    this.geoidUnits = geoidUnits;
  }
  
  /**
   * Gets the current time.
   *
   * @return String current time
   */
  public String getTime()
  {
    return time;
  }
  
  /**
   * Gets the latitude.
   *
   * @return double latitude
   */
  public double getLatitude()
  {
    return latitude;
  }
  
  /**
   * Gets the longitude.
   *
   * @return double longitude
   */
  public double getLongitude()
  {
    return longitude;
  }
  
  /**
   * Gets the fix type.
   *
   * @return int fix type
   */
  public int getFixType()
  {
    return fixType;
  }
  
  /**
   * Gets the number of satellites.
   *
   * @return int satellites
   */
  public int getSatellites()
  {
    return satellites;
  }
  
  /**
   * Gets the dilution.
   *
   * @return double dilution
   */
  public double getDilution()
  {
    return dilution;
  }
  
  /**
   * Gets the altitude.
   *
   * @return double altitude
   */
  public double getAltitude()
  {
    return altitude;
  }
  
  /**
   * Gets the altitude units.
   *
   * @return String altitude units
   */
  public String getAltitudeUnits()
  {
    return altitudeUnits;
  }
  
  /**
   * Gets the sea level.
   *
   * @return double sea level
   */
  public double getSeaLevel()
  {
    return seaLevel;
  }
  
  /**
   * Gets the geoid units.
   *
   * @return String geoid units
   */
  public String getGeoidUnits()
  {
    return geoidUnits;
  }
  
  /**
   * Parses a given GPGAA NMEA sentence and cretes a GPGGASentence object.
   *
   * @param s String GPGGA NMEA sentence
   * @return GPGGASentence object from parsed String
   */
  public static GPGGASentence parseGPGGA(final String s)
  { 
    String[] str = s.split(",");
    String sTime, sAltUnits, sGeoidUnits, sLat, sLong;
    double lat, longi, sDilution, sAltitude, sSeaLevel;
    int sFixType, sSatellites;
    
    sTime = str[1];
    
    sLat = str[2];
    String latDir = str[3];
    lat = NMEASentence.convertLatitude(sLat);
    if (latDir.equalsIgnoreCase("S")) lat *= -1;
    
    sLong = str[4];
    String longDir = str[5];
    longi = NMEASentence.convertLatitude(sLong);
    if (longDir.equalsIgnoreCase("W")) longi *= -1;
    
    try
    {
      sFixType = Integer.parseInt(str[6]);
      sSatellites = Integer.parseInt(str[7]);
      sDilution = Double.parseDouble(str[8]);
      sAltitude = Double.parseDouble(str[9]);
      sSeaLevel = Double.parseDouble(str[11]);
    }
    catch (NumberFormatException nfe)
    {
      sFixType = 0;
      sSatellites = 0;
      sDilution = 0.0;
      sAltitude = 0.0;
      sSeaLevel = 0.0;
    }
    
    sAltUnits = str[10];
    sGeoidUnits = str[12];

    return new GPGGASentence(sTime, lat, longi, sFixType, sSatellites, 
        sDilution, sAltitude, sAltUnits, sSeaLevel, sGeoidUnits);
  }
}
