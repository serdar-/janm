import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.biojava.nbio.structure.jama.Matrix;

/** This class contains some static methods that are related to matrix calculations
 * which come in handy for normal mode analysis.
 *
 * @author Serdar
 */
public class NMUtils {

    /** For finding Eucledian distance between two points.
     *
     * @param x
     * @param y
     * @return distance as double
     */
    public static double norm(double[] x, double[] y){

        return Math.sqrt(Math.pow( x[0] - y[0], 2 ) + Math.pow( x[1] - y[1], 2) +
                Math.pow( x[2] - y[2], 2 ));

    }

    /** Returns the diagonal of a square matrix, if matrix is not square
     * exception is thrown.
     *
     * @param matrix
     * @return double array of diagonal elements
     */
    public static double[] diag(Matrix matrix) {

        double[] diagonal = null;
        if (matrix.getRowDimension() == matrix.getColumnDimension()) {
            int m = matrix.getRowDimension();
            diagonal = new double[m];
            for (int i = 0; i < m; i++) {
                diagonal[i] = matrix.get(i, i);
            }
        } else {
            throw new IllegalArgumentException("Matrix must be square.");
        }

        return diagonal;

    }

    /** Finds the cross correlations matrix of a matrix given.
     *
     * @param modes
     * @return cross correlation matrix as 2D double array
     */
    public static double[][] crossCorrelations(Matrix modes) {

        double[][] mode = modes.getArrayCopy();
        int rows = mode.length;
        int columns = mode[0].length;
        double[][] correlations = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                correlations[i][j] = mode[i][j] / Math.sqrt(mode[i][i] * mode[j][j]);
            }
        }
        return correlations;
    }

    /** Calculates the matrix that contains the trace of each super element of
     * inverse of Hessian matrix (Hij^-1).
     *
     * @param inverse Hessian matrix (3Nx3N)
     * @return trace matrix (NxN)
     */
    public static Matrix shrinkMatrix(Matrix invHessian) {

        int n = invHessian.getRowDimension();
        int m = n / 3;
        Matrix shrinked = new Matrix(m, m);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                shrinked.set(i, j,
                        invHessian.getMatrix(i * 3, i * 3 + 3, j * 3, j * 3 + 3).trace());
            }
        }
        return shrinked;
    }

    /** Writes the eigenvectors to a text file which can be read directly from
     * NumPy by using loadtxt.
     *
     * @param pdbName
     * @param chains
     * @param eigenVectors
     * @throws IOException
     */
    public static void writeEigenVectors(String pdbName, String chains, double[][] eigenVectors)
            throws IOException {

        int rows = eigenVectors.length;
        BufferedWriter writer = new BufferedWriter(new FileWriter(pdbName + "_"
                + chains + ".eigvec"));
        for (int i = 0; i < rows; i++) {
            double revarray[] = ArrayUtils.subarray(eigenVectors[i],
                    rows - 26, rows - 6);
            ArrayUtils.reverse(revarray);
            writer.write(StringUtils.join(revarray, " ") + "\n");
        }
        writer.close();

    }
    /** Writers eigenvalues to a text file.
     *
     * @param pdbName
     * @param chains
     * @param eigenValues
     * @throws IOException
     */
    public static void writeEigenValues(String pdbName, String chains, double[] eigenValues)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(pdbName + "_"
                + chains + ".eigval"));
        for (int i = 0; i < eigenValues.length; i++) {
            writer.write(Double.toString(eigenValues[i]) + "\n");
        }
        writer.close();
    }


}
