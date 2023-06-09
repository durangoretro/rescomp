package com.durangoretro.rescomp;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Locale;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.StringUtils;

import static com.durangoretro.rescomp.Status.*;

public class Main {

	private static final String RESCOMP="rescomp";
	private static final String NAME="name";
	private static final String INPUT="input";
	private static final String OUTPUT="output";
	private static final String MODE="mode";
	private static final String WIDTH="width";
	private static final String HEIGHT="height";
	public static final String TITLE = "title";
	public static final String DESCRIPTION = "description";




	public static void main(String[] args) {

		Options options =generateOptions();


		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd = null;

		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp(RESCOMP, options);

			System.exit(INVALID_PARAMETERS.getCode());
		}

		String resourceName = cmd.getOptionValue(NAME);
		String sourceFile = cmd.getOptionValue(INPUT);
		String outputFile = cmd.getOptionValue(OUTPUT);
		Modes mode;
		try {
			mode = Modes.valueOf(cmd.getOptionValue(MODE).toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException e) {
			mode=Modes.UNKNOWN;
		}
		Status status;
		int width;
		int height;

		switch (mode) {
			case BACKGROUND:
				status = compileBackground(resourceName, sourceFile, outputFile);
				break;
			case SPRITESHEET:

				width = getRequiredOption(cmd,WIDTH);
				height = getRequiredOption(cmd,HEIGHT);
				status = compileSpriteSheet(resourceName, sourceFile, width, height, outputFile);
				break;
			case SCREENSHOOT:
				status = extractScreenshoot(sourceFile, outputFile);
				break;
			case BINARY:
				status = compileBinary(sourceFile, outputFile);
			break;
			case FONT:
				width = getRequiredOption(cmd,WIDTH);
				height = getRequiredOption(cmd,HEIGHT);

				status = compileFont(resourceName, sourceFile, width, height, outputFile);
				break;
			case SIGNER:
				String title = cmd.getOptionValue(TITLE);
				String description = cmd.getOptionValue(DESCRIPTION);
				if(StringUtils.isBlank(title)) {
					System.out.println("title is mandatory");
					System.exit(INVALID_PARAMETERS.getCode());
				}
				if(StringUtils.isBlank(description)) {
					System.out.println("description is mandatory");
					System.exit(INVALID_PARAMETERS.getCode());
				}
				status = signBinary(resourceName, sourceFile, outputFile, title, description);
				break;
			case VERIFY:
				status = verifySign(sourceFile);
				break;
			case STAMP:
				status = stampStr(sourceFile, resourceName, outputFile);
				break;
			case UNKNOWN:
			default:
				System.out.println("Unknown mode");
				status = Status.UNKNOWMODE;
				break;

		}
		System.exit(status.getCode());
	}

	private static int getRequiredOption(CommandLine cmd,String key){
		String strOpt = cmd.getOptionValue(key);
		if(StringUtils.isBlank(strOpt)){
			System.out.println("Required Option "+key);
			System.exit(INVALID_PARAMETERS.getCode());
		}
		return Integer.parseInt(strOpt);
	}

	private static Options generateOptions(){
		Options options=new Options();

		Arrays.stream(com.durangoretro.rescomp.Options.values())
				.forEach(
						options1 ->
						{
							Option option = new Option(options1.getOpt(),options1.getLongOpt(),options1.getHasArgs(),options1.getDescription());
							option.setRequired(options1.getRequired());
							options.addOption(option);
						}
				);
		return options;
	}

	private static Status compileBinary(String sourceFile, String outputFile) {

		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			FileOutputStream out = new FileOutputStream(outputFile);
			out.write(ImageGenerator.getHexString(outputFile, mem).getBytes());
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	private static Status extractScreenshoot(String sourceFile, String outputFile) {
		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			FileOutputStream out = new FileOutputStream(outputFile);
			ScreenshootExtractor.generatePng(mem, out);
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	private static Status compileBackground(String resourceName, String sourceFile, String outputFile) {
		try {
			final File file = new File(sourceFile);
			byte[] pixels = ImageGenerator.convertToDurango(file);
			byte [] encoded = new RLEEncoder().encode(2, pixels);
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(ImageGenerator.getHexString(resourceName, encoded).getBytes());	        
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	private static Status compileSpriteSheet(String resourceName, String sourceFile, int spriteWidth, int spriteHeight, String outputFile) {
		try {
			final File file = new File(sourceFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			out.write(ImageGenerator.compileSpriteSheet(file, spriteWidth, spriteHeight, resourceName).getBytes());
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private static Status compileFont(String resourceName, String sourceFile, int fontWidth, int fontHeight, String outputFile) {
		try {
			final File file = new File(sourceFile);
			FileOutputStream out = new FileOutputStream(outputFile);
			out.write(ImageGenerator.compileFont(file, fontWidth, fontHeight, resourceName).getBytes());
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private static Status signBinary(String resourceName, String sourceFile, String outputFile, String title, String description) {
		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			Stamper.stampStrValue(mem, Stamper.BUILD_STAMP, resourceName);
			DXHead.stampTitleDescription(mem, title, description);
			DXHead.copyBuildHashs(mem);
			DXHead.setVersion(mem, "");
			DXHead.setTimestamp(mem);
            DXHead.setRomSize(mem);
			Signer.sign(mem);
			FileOutputStream out = new FileOutputStream(new File(outputFile));
			out.write(mem);
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private static Status verifySign(String sourceFile) {
		try {
			byte[] mem = Files.readAllBytes(new File(sourceFile).toPath());
			Signer.sign(mem);
			
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	private static Status stampStr(String romFile, String stampName, String stampValue) {
		try {
			File file = new File(romFile);
			byte[] mem = Files.readAllBytes(file.toPath());
			Stamper.stampStrValue(mem, stampName+":[", stampValue);
			FileOutputStream out = new FileOutputStream(file);
			out.write(mem);
			out.close();
			return OK;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

}
