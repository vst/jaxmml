package com.vsthost.rnd.flame;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.util.List;

/**
 * Hello world!
 *
 */
public class XMMLCLI {
    /**
     * Defines the version of the Flame XMML Application/Library.
     */
    public static final String VERSION = "0.0.0-SNAPSHOT";

    /**
     * Defines the logger for the class.
     */
    private static Logger Log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    /**
     * Provides the main entry point of the Flame XMML application.
     *
     * @param args Arguments to the application.
     */
    public static void main( String[] args ) {
        // Initialise the command line arguments parser:
        CommandMain commandMain = new CommandMain();
        JCommander jc = new JCommander(commandMain);
        jc.setProgramName("xmmlcli");

        // Add validate command:
        CommandValidate commandValidate = new CommandValidate(commandMain);
        jc.addCommand("validate", commandValidate);

        // Parse command line options:
        jc.parse(args);

        // Configure the logger if needed:
        if (commandMain.silent) {
            Log.setLevel(Level.OFF);
        }
        else if (commandMain.verbose) {
            Log.setLevel(Level.ALL);
        }

        // Print the preamble:
        if (!commandMain.silent) {
            System.out.println("Flame XMML Software Library and Toolkit, Version " + XMMLCLI.VERSION);
            System.out.println("This Application is a Free and Open Source Software Application.");
            System.out.println("Copyright (c) 2014 Vehbi Sinan Tunalioglu");
            System.out.println("Note: Flame is under Copyright (c) 2013 Software Engineering Group, Scientific Computing, STFC");
            System.out.println();
        }

        // Check the command and act accordingly:
        String command = jc.getParsedCommand();
        Log.trace("Application received command '" + (command == null ? "NONE" : command) + "'");
        if (command == null) {
            jc.usage();
        }
        else if (command.equals("validate")) {
            commandValidate.run();
        }
    }
}

class CommandMain {
    @Parameter(names={"-log", "-verbose"}, description="Level of verbosity")
    public boolean verbose = false;

    @Parameter(names={ "-silent"}, description="Should we avoid unnecessary params?")
    public boolean silent = false;
}

@Parameters(commandDescription="Validates a given XMML file")
class CommandValidate {
    /**
     * Defines the logger for the class.
     */
    private static Logger Log = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);

    @Parameter(description="The list of files to validate")
    private List<String> files;

    /**
     * Defines the main command arguments of the command.
     */
    private CommandMain commandMain;

    /**
     * Default constructor.
     *
     * @param commandMain Main command arguments of the command.
     */
    public CommandValidate(CommandMain commandMain) {
        this.commandMain = commandMain;
    }

    /**
     * Runs the command.
     */
    public void run() {
        Log.trace("Running command: 'validate'");
    }
}
