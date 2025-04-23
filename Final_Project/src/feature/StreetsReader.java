package feature;

import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import geography.GeographicShape;
import gui.CartographyDocument;

/**
 * Reads a collection of Street objects from .str files.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor code.
 */
public class StreetsReader
{
  private BufferedReader in;
  private CartographyDocument<GeographicShape> geographicShapes;
  
  /**
   * Constructs the StreetsReader with buffered reader and geographic shapes
   * cartography document.
   *
   * @param is Input stream to read from
   * @param shapes CartographyDocument of geographic shapes
   */
  public StreetsReader(final InputStream is, 
      final CartographyDocument<GeographicShape> shapes)
  {
    this.in = new BufferedReader(new InputStreamReader(is));
    this.geographicShapes = shapes;
  }
  
  /**
   * Reads in data from .str files and creates the street and street segments.
   *
   * @param streets Map<String, Street> collection of streets from the file
   * @return CartographyDocument<StreetSegment> a cartography document of the 
   * street segments in the streets
   * @throws IOException
   */
  public CartographyDocument<StreetSegment> read(final Map<String, Street> streets)
      throws IOException
  {
    if (streets == null) 
      throw new IllegalArgumentException("The outbound parameter must be initialized!");
    Map<String, StreetSegment> streetSegments = new HashMap<>();
    
    String line, token;
    while ((line = in.readLine()) != null)
    {
      StringTokenizer tokenizer = new StringTokenizer(line, "\t");

      int tail = Integer.parseInt(tokenizer.nextToken().trim());
      int head = Integer.parseInt(tokenizer.nextToken().trim());
      token = tokenizer.nextToken().trim();
      if (token.equals("NaN")) token = "0.0";
      double length;
      try
      {
        length = Double.parseDouble(token);
      }
      catch (NumberFormatException nfe)
      {
        length = 0.0;
      }
      String code = tokenizer.nextToken().trim().substring(0, 2);
      String id = tokenizer.nextToken().trim();

      String prefix = tokenizer.nextToken().trim();
      if (prefix.isEmpty()) prefix = "";

      String name = tokenizer.nextToken().trim();
      if (name.isEmpty()) name = "";

      String type = tokenizer.nextToken().trim();
      if (type.isEmpty()) type = "";

      String suffix = tokenizer.nextToken().trim();
      if (suffix.isEmpty()) suffix = "";

      String canonicalName = Street.createCanonicalName(prefix, name, type, 
          suffix);

      Street street = streets.get(canonicalName);
      if (street == null)
      {
        street = new Street(prefix, name, type, suffix, code);
        streets.put(canonicalName, street);
      }
      
      // Starting Address
      token = tokenizer.nextToken();
      int temp1;
      try
      {
        temp1 = Integer.parseInt(token.trim());
      }
      catch (NumberFormatException nfe)
      {
        temp1 = 0;
      }

      // Ending Address
      token = tokenizer.nextToken();
      int temp2;
      try
      {
        temp2 = Integer.parseInt(token.trim());
      }
      catch (NumberFormatException nfe)
      {
        temp2 = 0;
      }

      int tailAddy  = Math.min(temp1, temp2);
      int headAddy = Math.max(temp1, temp2);

      if (id != null)
      {
        id = id.trim();             
        if (!id.equals(""))
        {
          GeographicShape geographicShape = null;
          if (geographicShapes != null) geographicShape = geographicShapes.getElement(id);
          StreetSegment segment = new StreetSegment(id, code, geographicShape, 
              tailAddy, headAddy, tail, head, length);
          street.addSegment(segment);
          streetSegments.put(id, segment);
        }
      }
    }
    Rectangle2D.Double bounds = null;
    if (geographicShapes != null) bounds = geographicShapes.getBounds();
    return new CartographyDocument<StreetSegment>(streetSegments, bounds);

  }
}
