package hu.infokristaly.homework;
import java.util.Arrays;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Parser;

import tasks.ZipAccessTask;
import tasks.ZipCreateTask;
import tasks.ZipListTask;


public class Homework4Zip {

    @SuppressWarnings("static-access")
    private static Options getCommandLineOptions() {
        Options options = new Options();
        options.addOption(OptionBuilder.isRequired(true).withArgName("task").withLongOpt("task").hasArg(true).withDescription("filesystem task [rm,mkdir,oldScan,queueScan,nioScan,find,observe]").create());
        options.addOption(OptionBuilder.isRequired(false).withArgName("dir").withLongOpt("dir").hasArg(true).withDescription("target dir for all tasks").create());
        options.addOption(OptionBuilder.isRequired(false).withArgName("file").withLongOpt("file").hasArg(true).withDescription("file to print").create());
        return options;
    }

    public static void main(String[] args) {
        Parser parser = new GnuParser();
        Options options = getCommandLineOptions();
        String task = null, dir = null, fileName = null;
        CommandLine commandLine = null;
        try {
            commandLine = parser.parse(options, args);
            task = (String) commandLine.getOptionValue("task");
            switch (task) {
            case "list":
                fileName = commandLine.getOptionValue("file");
                ZipListTask.main(fileName);
                break;
            case "create":
                fileName = commandLine.getOptionValue("file");
                String[] files = Arrays.copyOfRange(args, 4, args.length);
                ZipCreateTask.createZip(fileName,files);
                break;
            case "extract":
                fileName = commandLine.getOptionValue("file");
                dir = commandLine.getOptionValue("dir");
                ZipAccessTask.extract(fileName, dir);
                break;
            default:
                printHelp();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            printHelp();
            System.exit(-1);
        }
    }

    private static void printHelp() {
        HelpFormatter formatter = new HelpFormatter();
        Options options = getCommandLineOptions();
        formatter.printHelp("java -jar homework4zip.jar", options);
    }

}
