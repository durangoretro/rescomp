package com.durangoretro.rescomp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageGenerator {
	
	public static byte[] convertToDurango(File file) throws Exception {
		final BufferedImage image = ImageIO.read(file);
		byte pixels[] = new byte[8192];
		int i=0;
		
		for(int row=0; row < image.getHeight(); row++) {
			for(int col=0; col<image.getWidth(); col+=2) {
				pixels[i++]=Palette.getColorByte(image.getRGB(col, row), image.getRGB(col+1, row));				
			}			
		}
		return pixels;				
	}
	
	public static byte[] generateSpriteData(BufferedImage spriteSheet, 
			int spriteCol, int spriteRow, int spriteWidth, int spriteHeight) throws Exception {
		byte pixels[] = new byte[spriteWidth*spriteHeight/2];
		int i=0;
		
		for(int row=spriteRow*spriteHeight; row < spriteRow*spriteHeight+spriteHeight; row++) {
			for(int col=spriteCol*spriteWidth; col<spriteCol*spriteWidth+spriteWidth; col+=2) {
				pixels[i++]=Palette.getColorByte(spriteSheet.getRGB(col, row), spriteSheet.getRGB(col+1, row));				
			}			
		}
		
		return pixels;
	}
	
	public static byte[] generateFontData(BufferedImage spriteSheet, 
			int letterCol, int letterRow, int letterWidth, int letterHeight) throws Exception {
		int arraySize = letterWidth*letterHeight;
		while(arraySize%8!=0) {
			arraySize++;
		}
		boolean pixels[] = new boolean[arraySize];
		int i=0;
		
		for(int row=letterRow*letterHeight; row < letterRow*letterHeight+letterHeight; row++) {
			for(int col=letterCol*letterWidth; col<letterCol*letterWidth+letterWidth; col++) {
				pixels[i++]=spriteSheet.getRGB(col, row)==Palette.COLOR_15;				
			}			
		}
		
		byte data[] = new byte[arraySize/8];
		for(int j=0; j<data.length; j++) {
			byte d7, d6, d5, d4, d3, d2, d1, d0;
			d7 = pixels[j*8+0] ? (byte)0b10000000 : 0x00;
			d6 = pixels[j*8+1] ? (byte)0b01000000 : 0x00;
			d5 = pixels[j*8+2] ? (byte)0b00100000 : 0x00;
			d4 = pixels[j*8+3] ? (byte)0b00010000 : 0x00;
			d3 = pixels[j*8+4] ? (byte)0b00001000 : 0x00;
			d2 = pixels[j*8+5] ? (byte)0b00000100 : 0x00;
			d1 = pixels[j*8+6] ? (byte)0b00000010 : 0x00;
			d0 = pixels[j*8+7] ? (byte)0b00000001 : 0x00;
			
			data[j]=(byte)(d7|d6|d5|d4|d3|d2|d1|d0);
		}
		
		return data;
	}
	
	public static String compileSpriteSheet(File pngFile, int spriteWidth, int spriteHeight, String resourceName) throws Exception {
		StringBuilder sb = new StringBuilder(10000);
		StringBuilder resourceNameSB = new StringBuilder(100);
		final BufferedImage image = ImageIO.read(pngFile);
		for(int row=0; row<image.getHeight()/spriteHeight; row++) {
			for(int col=0; col<image.getWidth()/spriteWidth; col++) {
				byte pixels[] = generateSpriteData(image, col, row, spriteWidth, spriteHeight);
				resourceNameSB.setLength(0);
				resourceNameSB.append(resourceName).append('_').append(row).append('_').append(col);
				sb.append(Util.getHexString(resourceNameSB.toString(), pixels)).append("\n");
			}
		}
		return sb.toString();
	}
	
	public static String compileFont(File pngFile, int fontWidth, int fontHeight, String resourceName) throws Exception {
		StringBuilder sb = new StringBuilder(10000);
		StringBuilder resourceNameSB = new StringBuilder(100);
		resourceNameSB.append(resourceName);
		final BufferedImage image = ImageIO.read(pngFile);
		List<Byte> bytes = new LinkedList<>();
		for(int row=0; row<image.getHeight()/fontHeight; row++) {
			for(int col=0; col<image.getWidth()/fontWidth; col++) {
				byte[] letter = generateFontData(image, col, row, fontWidth, fontHeight);
				for(int i=0; i<letter.length; i++) {
					bytes.add(letter[i]);
				}
			}
		}
		byte[] pixels = new byte[bytes.size()];
		int i=0;
		for(Byte b : bytes) {
			pixels[i++]=b;
		}
		sb.append(Util.getHexString(resourceNameSB.toString(), pixels)).append("\n");
		return sb.toString();
	}
}
