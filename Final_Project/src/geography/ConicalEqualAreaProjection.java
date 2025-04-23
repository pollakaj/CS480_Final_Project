package geography;

/**
 * Calculates the Conical Equal Area Projection for a Map Projection.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class ConicalEqualAreaProjection extends AbstractMapProjection
{
  protected double[] retval = new double[2];
  private double lambda0, phi0;
  private double phi1, phi2;
  private double n, c, rho0;

  /**
   * Constructs the projection and initializes the reference 3-dimensional 
   * values.
   *
   * @param refM double Reference Meridian for longitude of origin
   * @param refP double Reference Parallel for latitude of origin
   * @param refP1 double Standard parallel one
   * @param refP2 double Standard parallel two
   */
  public ConicalEqualAreaProjection(final double refM, final double refP, 
      final double refP1, final double refP2)
  {
    this.lambda0 = refM * RADIANS_PER_DEGREE;
    this.phi0 = refP * RADIANS_PER_DEGREE;
    this.phi1 = refP1 * RADIANS_PER_DEGREE;
    this.phi2 = refP2 * RADIANS_PER_DEGREE;
    
    this.n = 0.5 * (Math.sin(phi1) + Math.sin(phi2));
    this.c = (Math.cos(phi1) * Math.cos(phi1) + 2.0 * this.n * Math.sin(phi1));
    this.rho0 = Math.sqrt(this.c - 2.0 * this.n * Math.sin(phi0)) / this.n;
  }

  @Override
  public double[] forward(final double lambda, final double phi)
  {
    double sinphi  = Math.sin(phi);
    double theta = n * (lambda - lambda0);
    double rho = Math.sqrt(c - 2.0*n*sinphi) / n;

    retval[0] = R * rho * Math.sin(theta);        
    retval[1] = R * (rho0 - rho * Math.cos(theta));

    return retval;
  }

  @Override
  public double[] inverse(final double ew, final double ns)
  {
    double a = Math.sqrt(Math.pow(ew/R, 2) + Math.pow((rho0 - ns/R), 2));
    double b = Math.atan((ew/R)/(rho0 - (ns/R)));

    retval[0] = lambda0 + b/n;
    retval[1] = Math.asin((c-a*a*n*n)/(2.0*n));

    return retval;
  }

}
