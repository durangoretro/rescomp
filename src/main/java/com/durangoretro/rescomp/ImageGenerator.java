package com.durangoretro.rescomp;

import java.awt.image.BufferedImage;
import java.io.File;

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
	
	public static String compileSpriteSheet(File pngFile, int spriteWidth, int spriteHeight, String resourceName) throws Exception {
		StringBuilder sb = new StringBuilder(10000);
		StringBuilder resourceNameSB = new StringBuilder(100);
		final BufferedImage image = ImageIO.read(pngFile);
		for(int row=0; row<image.getHeight()/spriteHeight; row++) {
			for(int col=0; col<image.getWidth()/spriteWidth; col++) {
				byte pixels[] = generateSpriteData(image, col, row, spriteWidth, spriteHeight);
				resourceNameSB.setLength(0);
				resourceNameSB.append(resourceName).append('_').append(row).append('_').append(col);
				sb.append(getHexString(resourceNameSB.toString(), pixels)).append("\n");
			}
		}
		return sb.toString();
	}
	
	public static String getHexString(String resurceName, byte[] pixels) {
		StringBuilder sb = new StringBuilder(5000);
		sb.append("const unsigned char ").append(resurceName).append('[').append(pixels.length).append("] = {").append("\n");
		for(int i=0; i<pixels.length; i++) {
			sb.append("0x").append(String.format("%02X",pixels[i])).append(',');
			if(i%32==31) {
				sb.append("\n");
			}
		}
		sb.append("\n").append("};");
		return sb.toString();
	}

	/*
 const unsigned char tiles[8192] = {
0x11,0x44,0x44,0x44,0x11,0x41,0x44,0x44,0x11,0x14,0x44,0x44,0x11,0x11,0x44,0x44,0x41,0x11,0x14,0x44,0x41,0x11,0x14,0x44,0x94,0x44,0x44,0x44,0x94,0x44,0x44,0x44,
0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0xFF,0xFF,0xFF,0xFF,0x00,0x00,0x00,0x00,0xFF,0xFF,0xFF,0xFF,
};

*/
	
		
	
}
