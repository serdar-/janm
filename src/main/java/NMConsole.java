import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * This is the main class for running normal mode analysis on console. 
 *
 * @author Serdar
 */
public class NMConsole {

    public static void main(String[] args){

        Options options = new Options();
        options.addOption("gnm", false, "GNM calculation");
        options.addOption("anm", false, "ANM calculation");
        Option download = OptionBuilder.withArgName("pdb")
                .hasArg()
                .withDescription("PDB code to download from RCBS")
                .create("download");
        Option file = OptionBuilder.withArgName("pdb file")
                .hasArg()
                .withDescription("PDB file path")
                .create("file");
        Option chains = OptionBuilder.withArgName("chain names")
                .hasArg()
                .withDescription("Chain IDs")
                .create("chains");
        options.addOption(download);
        options.addOption(file);
        options.addOption(chains);
        try{
            CommandLineParser parser = new GnuParser();
            CommandLine cmd = parser.parse(options, args);
            cmd.hasOption(null);
        } catch (ParseException e){
            System.err.println(e.getMessage());
        }
    }

}