import org.biojava.nbio.structure.Structure;
import org.biojava.nbio.structure.StructureException;
import org.biojava.nbio.structure.align.util.AtomCache;
import org.biojava.nbio.structure.gui.BiojavaJmol;

import javax.swing.*;
import java.io.IOException;

public class StructureViewTest {


    public static void showStructureTest() throws IOException, StructureException {

        AtomCache cache = new AtomCache();
        Structure struct = cache.getStructure("4ake");
        BiojavaJmol jmol = new BiojavaJmol();
        jmol.setStructure(struct);
        JFrame frame = jmol.getFrame();

    }
    public static void main(String[] args) throws IOException, StructureException {
        showStructureTest();
    }

}
