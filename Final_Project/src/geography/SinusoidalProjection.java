package geography;

/**
 * Computes the sinusoidal map projection.
 * 
 * @author pollakaj
 * @version 1.0
 * 
 * Honor Code: This code complies with the JMU Honor Code.
 */
public class SinusoidalProjection extends AbstractMapProjection
{
  private double[] retval;
  
  /**
   * Default Constructor.
   */
  public SinusoidalProjection()
  {
    retval = new double[2];
  }

  @Override
  public double[] forward(final double lambda, final double phi)
  {
    double cosphi  = Math.cos(phi);

    retval[0] = R * lambda * cosphi;
    retval[1] = R * phi;

    return (retval);
  }

  @Override
  public double[] inverse(final double ew, final double ns)
  {
    double phi    = ns / R;
    double cosphi = Math.cos(phi);
    double lambda = ew / (R * cosphi);

    retval[0] = lambda;
    retval[1] = phi;

    return retval;
  }

}
