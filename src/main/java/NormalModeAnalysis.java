import java.util.List;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.NotConvergedException;
import org.biojava.nbio.structure.Atom;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.jama.Matrix;

/** The <code>NormalModeAnalysis</code> interface is the interface that
 * contains the common methods for doing calculations using elastic network
 * models. These models are Gaussian and anisotropic network models.
 *
 *
 * @author Serdar Ozsezen
 */
public interface NormalModeAnalysis {


    /** Sets the structure whose alpha carbon coordinates are going to be used
     * for finding eigenvalues and eigenvectors.
     *
     * @param structure
     */
    public void setStructure(Structure structure);

    public void setStructure(List<Chain> model);

    public void setStructure(Chain chain);

    public Structure getStructure();

    public List<Chain> getChains();

    public Chain getChain(int position);

    /** Returns array of alpha carbon atoms that are used in normal mode analysis
     * calculations.
     *
     * @return Atom array
     */
    public Atom[] getCAs();

    /** Sets the coordinates for normal mode analysis calculations. If coordinates
     * are provided, it is possible to do the calculations without setting a
     * structure or chain object.
     *
     * @param two-dimensional array of alpha carbon coordinates
     */
    public void setCoordinates(double[][] coordinates);

    /**
     *
     * @return two-dimensional array of alpha carbon coordinates
     */
    public double[][] getCoordinates();

    /** According to elastic network model, alpha carbons, which are within a
     * certain cutoff radius, assumed to be interacting with each other. Default
     * cutoff radius is <b>10 Angstroms</b> for Gaussian network model and
     * <b>15 Angstroms</b> for anisotropic network model.
     *
     * @param cutoff radius as double
     */
    public void setCutoffRadius(double r);

    /**
     *
     * @return cutoff radius as double
     */
    public double getCutoffRadius();

    /** Creates the connectivity matrix based on give alpha carbon coordinates.
     * Connectivity matrix is Kirchhoff matrix for Gaussian network model and
     * Hessian matrix for anisotropic network model.
     *
     */
    public void buildConnectivityMatrix();

    /** Creates the connectivity matrix according to given cutoff radius.
     *
     * @return connectivity matrix
     */
    public DenseMatrix getConnectivityMatrix();

    /** Calculates the pseudo-inverse of Kirchhoff or Hessian matrices.
     *
     * @return inverse Kirchhoff or Hessian
     */
//    public DenseMatrix getInverseConnectivityMatrix();

    public void setGamma(double gamma);

    public double getGamma();

    public void calculateModes() throws NotConvergedException;

    public Matrix getEigenVectors();

    public double[] getEigenValues();

//    public DenseMatrix getMode(int mod);

    public void displayModes();

}
