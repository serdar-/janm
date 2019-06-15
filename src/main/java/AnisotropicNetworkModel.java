import java.util.List;
import no.uib.cipr.matrix.DenseMatrix;
import no.uib.cipr.matrix.NotConvergedException;
import no.uib.cipr.matrix.SVD;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.jama.Matrix;


/**
 *
 * @author Serdar
 */
public class AnisotropicNetworkModel extends BaseNM implements NormalModeAnalysis{

    protected DenseMatrix HessianMatrix;
    protected DenseMatrix invHessianMatrix;
    protected double[] eigenValues;
    protected DenseMatrix eigenValueMatrix;
    protected Matrix eigenVectors; // JAMA Matrix

    public AnisotropicNetworkModel(){}

    public AnisotropicNetworkModel(Structure structure){
        super(structure);
    }

    public AnisotropicNetworkModel(List<Chain> model){
        super(model);
    }

    public AnisotropicNetworkModel(Chain chain){
        super(chain);
    }

    private Matrix Hij(double[] x, double[] y, double distance) {

        Matrix hij = new Matrix(3,3);
        Matrix distanceVector =
                new Matrix(new double[] {x[0] - y[0], x[1] - y[1], x[2] - y[2]},1);
        hij = distanceVector.transpose().times(distanceVector.
                times(-this.GAMMA/Math.pow(distance,2)));
        return hij;

    }


    public void buildConnectivityMatrix() {

        double[][] coords = this.coordinates;
        int n = coords.length;
        Matrix hessian = new Matrix(n*3,n*3,0);
        for( int i = 0; i < n; i++ ){
            for( int j = i + 1; j < n; j++ ){
                double distance = NMUtils.norm(coords[i], coords[j]);
                if( distance <= this.CUTOFF ){
                    Matrix hij = Hij(coords[i], coords[j], distance);
                    hessian.setMatrix(i*3, i*3+2, j*3, j*3+2, hij);
                    hessian.setMatrix(j*3, j*3+2, i*3, i*3+2, hij);
                    hessian.setMatrix(i*3, i*3+2, i*3, i*3+2,
                            hessian.getMatrix(i*3, i*3+2, i*3, i*3+2).minusEquals(hij));
                    hessian.setMatrix(j*3, j*3+2, j*3, j*3+2,
                            hessian.getMatrix(j*3, j*3+2, j*3, j*3+2).minusEquals(hij));
                }
            }
        }
        DenseMatrix hess = new DenseMatrix(hessian.getArrayCopy());
        this.HessianMatrix = hess;
    }


    public DenseMatrix getConnectivityMatrix() {
        return this.HessianMatrix;
    }


    public void calculateModes() throws NotConvergedException {
        SVD svd = new SVD(this.HessianMatrix.numRows(),
                this.HessianMatrix.numColumns(), true);
        svd.factor(this.HessianMatrix);
        DenseMatrix u = svd.getU();
        // Convert it U to JAMA matrix
        Matrix eigenVectors = new Matrix(u.numColumns(),u.numRows());
        for( int i = 0; i < u.numRows(); i++ ){
            for ( int j = 0; j < u.numColumns(); j++ ){
                eigenVectors.set(i, j, u.get(i, j));
            }
        }
        this.eigenVectors = eigenVectors;
        double[] eig = svd.getS().clone();
        double[] eigenvalues = new double[eig.length-6];
        int j = 0;
        for(int i = eig.length - 7; i >= 0 ; i--){
            eigenvalues[j++] = eig[i];
        }
//        this.eigenValues = svd.getS();
        this.eigenValues = eigenvalues;
        System.out.println(eig.length + " eigenvalues calculated.");

    }


    public Matrix getEigenVectors() {
        return this.eigenVectors;
    }


    public double[] getEigenValues() {
        return this.eigenValues;
    }

    public DenseMatrix getEigenValueMatrix(){
        return this.eigenValueMatrix;
    }

//    @Override
//    public Matrix getMode(int mod) {
//        
//        DenseMatrix u = this.eigenVectors;
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


    public void displayModes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public Matrix getInverseConnectivityMatrix() {
//        
//        Matrix u = this.eigenVectors;
//        double[] S = this.eigenValues;
//        int size = S.length;
//        int n = this.coordinates.length;
//        Matrix invHessian = new Matrix(n*3, n*3, 0);
//        for(int i = 0; i < size; i++){
//            Matrix uk = u.getMatrix(0, size + 5, size - i - 1, size - i - 1);
//            invHessian.plusEquals(uk.times(uk.transpose().times(1/S[i])));
//        }
//        return invHessian;
//    }

}