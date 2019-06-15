import java.util.List;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.SVD;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.jama.Matrix;

/**
 *
 * @author Serdar Özsezen
 */
public class GaussianNetworkModel extends BaseNM implements NormalModeAnalysis {

    private DenseMatrix KirchhoffMatrix;
    private double[] eigenValues;
    private DenseMatrix eigenValueMatrix;
    private Matrix eigenVectors;

    public GaussianNetworkModel(){

    }

    public GaussianNetworkModel(Structure structure){
        super(structure);
    }

    public GaussianNetworkModel(List<Chain> model){
        super(model);
    }

    public GaussianNetworkModel(Chain chain){
        super(chain);
    }


    public void buildConnectivityMatrix() {

        double[][] coords = this.coordinates;
        int n = coords.length;
        Matrix kirchhoff = new Matrix(n,n);
        for(int i = 0; i < n; i++){
            for(int j = i + 1; j < n; j++){
                double distance = NMUtils.norm(coords[i], coords[j]);
                if(distance <= this.CUTOFF){
                    kirchhoff.set(i, j, -1.0);
                    kirchhoff.set(j, i, -1.0);
                    kirchhoff.set(i, i, kirchhoff.get(i, i) + 1.0);
                    kirchhoff.set(j, j, kirchhoff.get(j, j) + 1.0);
                }
            }
        }
        DenseMatrix km = new DenseMatrix(kirchhoff.getArrayCopy());
        this.KirchhoffMatrix = km;
    }


    public DenseMatrix getConnectivityMatrix() {
        return this.KirchhoffMatrix;
    }


    public void calculateModes() {

        int n = this.coordinates.length;
        SVD svd = new SVD(this.KirchhoffMatrix.numRows(),
                this.KirchhoffMatrix.numColumns(), true);
//        SingularValueDecomposition svd = 
//                new SingularValueDecomposition(this.KirchhoffMatrix);
        DenseMatrix eigvec = svd.getU();
//        this.eigenValueMatrix = svd.getS();
        double[] eig = svd.getS();
        double[] eigenvalues = new double[eig.length-1];
        int j = 0;
        for( int i = eig.length - 2; i >= 0 ; i--){
            eigenvalues[j++] = eig[i];
        }
//        this.eigenValues = eigenvalues;
        Matrix eigenvectors = new Matrix(n,n);
        for( int a = 0 ; a < eigvec.numRows() ; a++) {
            for( int b = 0; b < eigvec.numColumns(); b++){
                eigenvectors.set(a, b, eigvec.get(a, b));
            }
        }
        this.eigenVectors = eigenvectors;
        this.eigenValues = eigenvalues;
        System.out.println(this.eigenValues.length + " eigenvalues calculated.");

    }


    public Matrix getEigenVectors() {
        return this.eigenVectors;
//        throw new UnsupportedOperationException("");
    }


    public double[] getEigenValues() {
        return this.eigenValues;
    }


    public void displayModes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public Matrix getMode(int mod) {
//        
//        Matrix u = this.eigenVectors;
//        double[] S = this.eigenValues;
//        int size = S.length;
//        int modValue = mod - 1;
//        if( modValue > size ){
//            throw new IllegalArgumentException("Number of modes can not be higher than number of eigen values calculated.");
//        } else {
//            Matrix uk = u.getMatrix(0, size, size - mod, size - mod);
//            Matrix mode = uk.times(uk.transpose().times(1/S[modValue]));
//            return mode;
//        }
//    }

//    @Override
//    public Matrix getInverseConnectivityMatrix() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }





}