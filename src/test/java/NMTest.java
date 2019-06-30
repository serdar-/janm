import no.uib.cipr.matrix.NotConvergedException;
import org.biojava.nbio.structure.Chain;
import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.junit.Test;

import java.io.IOException;

public class NMTest {

    public void printArray(double[] array){
        for(int i = 0; i < array.length; i++){
            System.out.printf("%.5e\n",array[i]);
        }
    }
    @Test
    public void gnmTest() throws IOException, StructureException, NotConvergedException {

        AtomCache cache = new AtomCache();
        Structure ake = cache.getStructure("4ake");
        Chain chainA = ake.getChain("A");
        GaussianNetworkModel gnm = new GaussianNetworkModel(chainA);
        gnm.setCutoffRadius(10.);
        gnm.buildConnectivityMatrix();
        gnm.calculateModes();
        System.out.println("Eigenvalues for GNM");
        double[] eigvals = gnm.getEigenValues();
        printArray(eigvals);
    }
    @Test
    public void anmTest() throws IOException, StructureException, NotConvergedException {

        AtomCache cache = new AtomCache();
        Structure ake = cache.getStructure("4ake");
        Chain chainA = ake.getChain("A");
        AnisotropicNetworkModel anm = new AnisotropicNetworkModel(chainA);
        anm.setCutoffRadius(15.);
        anm.buildConnectivityMatrix();
        anm.calculateModes();
        System.out.println("Eigenvalues for ANM");
        double[] eigvals = anm.getEigenValues();
        printArray(eigvals);

    }
}
