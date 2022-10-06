package com.durangoretro.rescomp;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {
	
	private static final String BACKGROUND = "BACKGROUND";
	
	public static void main(String[] args) throws Exception {
		Options options = new Options();

		Option inputOption = new Option("i", "input", true, "File input");
        inputOption.setRequired(true);
        options.addOption(inputOption);
        
		Option outputOption = new Option("o", "output", true, "File output");
        outputOption.setRequired(true);
        options.addOption(outputOption);
        
        Option modeOption = new Option("m", "mode", true, "Working mode, BACKGROUND");
        modeOption.setRequired(true);
        options.addOption(modeOption);
                

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

        String sourceFile = cmd.getOptionValue("input");
        String outputFile = cmd.getOptionValue("output");
        String mode = cmd.getOptionValue("mode");
        
        if(mode.equalsIgnoreCase(BACKGROUND)) {
        	compileBackground(sourceFile, outputFile);
        }
	}

	private static void compileBackground(String sourceFile, String outputFile) throws Exception {
		final File file = new File(sourceFile);
        byte[] pixels = ImageGenerator.convertToDurango(file);
		byte [] encoded = new RLEEncoder().encode(2, pixels);
        FileOutputStream out = new FileOutputStream(new File(outputFile));
        out.write(ImageGenerator.getHexString(encoded).getBytes());		        
		out.close();		
	}	
	
}
