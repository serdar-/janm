import org.biojava.nbio.structure.*;

import java.util.List;

/**
 * @author Serdar
 */
public class BaseNM {

    protected Structure structure;
    protected double[][] coordinates;
    protected double CUTOFF;
    protected double GAMMA = 1;


    public BaseNM(){}

    public BaseNM(Structure structure){
        setStructure(structure);
    }

    public BaseNM(List<Chain> model){
        setStructure(model);
    }

    public BaseNM(Chain chain){
        setStructure(chain);
    }

    public void setStructure(Structure structure) {

        this.structure = structure.clone();
        Atom[] CAArray = getCAs();
        this.coordinates = new double[CAArray.length][3];
        for(int i = 0; i < CAArray.length; i++)
            this.coordinates[i] = CAArray[i].getCoords();

    }


    public void setStructure(List<Chain> model) {

        this.structure = new StructureImpl();

        for(Chain chain: model){
            this.structure.addChain(chain);
        }

        Atom[] CAArray = getCAs();
        this.coordinates = new double[CAArray.length][3];
        for(int i = 0; i < CAArray.length; i++)
            this.coordinates[i] = CAArray[i].getCoords();

    }


    public void setStructure(Chain chain) {

        this.structure = new StructureImpl();
        this.structure.addChain(chain);
        Atom[] CAArray = getCAs();
        this.coordinates = new double[CAArray.length][3];
        for(int i = 0; i < CAArray.length; i++)
            this.coordinates[i] = CAArray[i].getCoords();

    }

    public Structure getStructure() {
        return this.structure;
    }


    public List<Chain> getChains() {
        return this.structure.getChains();
    }


    public Chain getChain(int position) {
        return this.structure.getChainByIndex(position);

    }


    public Atom[] getCAs() {
        return StructureTools.getAtomCAArray(this.structure);
    }



    public void setCoordinates(double[][] coordinates) {
        this.coordinates = coordinates;
    }


    public double[][] getCoordinates() {
        return this.coordinates;
    }


    public void setCutoffRadius(double r) {
        this.CUTOFF = r;
    }


    public double getCutoffRadius() {
        return this.CUTOFF;
    }


    public void setGamma(double gamma) {
        this.GAMMA = gamma;
    }


    public double getGamma() {
        return this.GAMMA;
    }
}
