package gui;

import feature.StreetSegment;

public interface RouteRecalculationListener
{
  abstract void routeRecalculated(StreetSegment newOrigin);
}
