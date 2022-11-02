package com.durangoretro.rescomp;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

	private static final String BACKGROUND = "BACKGROUND";
	private static final String SPRITESHEET = "SPRITESHEET";
	private static final String SCREENSHOOT = "SCREENSHOOT";
	private static final String BINARY = "BINARY";

	public static void main(String[] args) {
		Options options = new Options();

		Option resourceOption = new Option("n", "name", true, "Resource name");
		resourceOption.setRequired(true);
		options.addOption(resourceOption);

		Option inputOption = new Option("i", "input", true, "File input");
		inputOption.setRequired(true);
		options.addOption(inputOption);

		Option outputOption = new Option("o", "output", true, "File output");
		outputOption.setRequired(true);
		options.addOption(outputOption);

		Option modeOption = new Option("m", "mode", true, "Working mode, BACKGROUND");
		modeOption.setRequired(true);
		options.addOption(modeOption);
		
		Option widthOption = new Option("w", "width", true, "Sprite width");
		widthOption.setRequired(false);
		options.addOption(widthOption);
		
		Option heightOption = new Option("h", "height", true, "Sprite height");
		heightOption.setRequired(false);
		options.addOption(heightOption);


		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("utility-name", options);

			System.exit(1);
		}

		String resourceName = cmd.getOptionValue("name");
		String sourceFile = cmd.getOptionValue("input");
		String outputFile = cmd.getOptionValue("output");
		String mode = cmd.getOptionValue("mode");
		int status = 2;
		if(mode.equalsIgnoreCase(BACKGROUND)) {
			status = compileBackground(resourceName, sourceFile, outputFile);
		}
		else if(mode.equalsIgnoreCase(SPRITESHEET)) {
			int width = Integer.parseInt(cmd.getOptionValue("width"));
			int height = Integer.parseInt(cmd.getOptionValue("height"));
			status = compileSpriteSheet(resourceName, sourceFile, width, height, outputFile);
		}
		else if(mode.equalsIgnoreCase(SCREENSHOOT)) {
			status = extractScreenshoot(sourceFile, outputFile);
		}
		else if(mode.equalsIgnoreCase(BINARY)) {
			status = compileBinary(resourceName, sourceFile, outputFile);
		}
		else {
			System.out.println("Unknown mode");
			status = 2;
		}
		System.exit(status);

	}

	private static int compileBinary(String resourceName, String sourceFile, String outputFile) {
		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(ImageGenerator.getHexString(outputFile, mem).getBytes());
			out.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	private static int extractScreenshoot(String sourceFile, String outputFile) {
		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			ScreenshootExtractor.generatePng(mem, out);
			out.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	private static int compileBackground(String resourceName, String sourceFile, String outputFile) {
		try {
			final File file = new File(sourceFile);
			byte[] pixels = ImageGenerator.convertToDurango(file);
			byte [] encoded = new RLEEncoder().encode(2, pixels);
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(ImageGenerator.getHexString(resourceName, encoded).getBytes());	        
			out.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

	private static int compileSpriteSheet(String resourceName, String sourceFile, int spriteWidth, int spriteHeight, String outputFile) {
		try {
			final File file = new File(sourceFile);
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(ImageGenerator.compileSpriteSheet(file, spriteWidth, spriteHeight, resourceName).getBytes());
			out.close();
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			return 3;
		}
	}

}
