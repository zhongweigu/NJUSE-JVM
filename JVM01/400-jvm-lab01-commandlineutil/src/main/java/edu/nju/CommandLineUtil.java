package edu.nju;

import org.apache.commons.cli.*;

public class CommandLineUtil {
    private static CommandLine line;
    private static CommandLineParser parser = new DefaultParser();
    private static Options options = new Options();
    private boolean sideEffect;
    public static final String WRONG_MESSAGE = "MissingUserArg";

    /**
     * you can define options here
     * or you can create a func such as [static void defineOptions()] and call it before parse input
     */
    static {
        options.addOption("h", "help", false, "Print help message");
        options.addOption("s", "sideEffect", false, "Print sideEffect");
        options.addOption("p","print", true, "Print the result of the program");
    }

    public void main(String[] args){

        line = parseInput(args);
        sideEffect = false;

        if (!line.hasOption("h") && line.getArgList().isEmpty()) {
            System.out.println(WRONG_MESSAGE);
            return;
        }

        handleOptions(line);

    }

    /**
     * Print the usage of all options
     */
    private static void printHelpMessage() {
        System.out.println("help");
    }

    /**
     * Parse the input and handle exception
     * @param args origin args form input
     */
    public CommandLine parseInput(String[] args) {
        parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            System.exit(-1);
        }
        return cmd;
    }

    /**
     * You can handle options here or create your own func
     */
    public void handleOptions(CommandLine line) {

        if (line.hasOption("h")){
            printHelpMessage();
            return;
        }

        sideEffect = getSideEffectFlag();

        if (line.hasOption("p")){
            String print = line.getOptionValue("p");
            System.out.println(print);
        }
    }

    public boolean getSideEffectFlag() {
        return line.hasOption("s") && !line.hasOption("h");
    }
}
