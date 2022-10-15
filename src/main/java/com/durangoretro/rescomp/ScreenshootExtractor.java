package com.durangoretro.rescomp;

import java.awt.image.BufferedImage;
import java.io.FileOutputStream;

import javax.imageio.ImageIO;

public class ScreenshootExtractor {

	public static void generatePng(byte[] mem, FileOutputStream out) throws Exception {
		// [HiRes Invert S1 S0    RGB LED NC NC]
		byte videoMode = mem[0xdf80];
		if((videoMode & 0xc8) == 0x08) {
			int screen = (videoMode & 0x30)>>4;
			int screenStart = 0x2000 * screen;
			int screenEnd = screenStart + 0x2000;
			byte[] screenData = new byte[0x2000];
			for(int i=0,j=screenStart; j<screenEnd; i++,j++) {
				screenData[i]=mem[j];
			}
			
			final BufferedImage image = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
			int i=0;
			for(int row=0; row<128; row++) {
				for(int col=0; col<128; col+=2) {
					byte pixelPair = screenData[i++];
					image.setRGB(col, row, Palette.getIntColorLeftFromByte(pixelPair));
					image.setRGB(col+1, row, Palette.getIntColorRightFromByte(pixelPair));
				}
			}
			
			ImageIO.write(image, "PNG", out);
		}
		else {
			throw new Exception("Video mode not implemented yet.");
		}		
	}
	
}
