package gui;

import feature.StreetSegment;

/**
 * Listener for when Route Recalculation needs to be done, based on user going 
 * off route.
 * 
 * @author Adam Pollak & Tyler Lopes
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public interface RouteRecalculationListener
{
  // Abstract Method implemented by the app to perform the recalculation.
  abstract void routeRecalculated(StreetSegment newOrigin);
}
