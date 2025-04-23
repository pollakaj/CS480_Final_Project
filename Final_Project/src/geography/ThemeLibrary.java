package geography;

/**
 * Theme Library Interface to be used with Street Theme Library.
 * 
 * @author Adam Pollak
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface ThemeLibrary
{
  /**
   * Gets the theme for highlighted objects.
   * @return Theme theme for highlighted
   */
  public abstract Theme getHighlightTheme();
  
  /**
   * Gets the theme by means of Object's code (TIGER Road type code).
   * @param code String Code for corresponding object
   * @return Theme used with the given code
   */
  public abstract Theme getTheme(String code);
}
