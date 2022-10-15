package com.durangoretro.rescomp;

import java.io.FileOutputStream;

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
						
		}
		else {
			throw new Exception("Video mode not implemented yet.");
		}		
	}
	
}
