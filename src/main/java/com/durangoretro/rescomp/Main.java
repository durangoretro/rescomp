package net.emiliollbb.listacc;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	
	public static void main(String[] args) throws Exception {
		Options options = new Options();

		Option user = new Option("o", "output", true, "File output");
        user.setRequired(true);
        options.addOption(user);
        
        Option password = new Option("f", "format", true, "Format to generate");
        password.setRequired(true);
        options.addOption(password);
                

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);

            System.exit(1);
            return;
        }

        String o = cmd.getOptionValue("output");
        String f = cmd.getOptionValue("format");
        
	}
	
	
}
