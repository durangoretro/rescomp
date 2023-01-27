package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class Stamper {
		private static final String BUILD_STAMP = "BUILD:[";

		private static int findSignatureOffset(byte[] rom) {
			return new String(rom, Charset.forName("ASCII")).indexOf(BUILD_STAMP) + BUILD_STAMP.length();
		}
		
		public static void stamp(byte[] rom, String stamp) throws Exception {
			int offset = findSignatureOffset(rom);
			int start = 64*1024-rom.length;
			System.out.println("Adding build version at " + String.format("%02X", start+offset));
			
			char[] data = stamp.toCharArray();
			if(data.length!=7) {
				throw new Exception("Invalid stamp length. It should be 7 char");
			}
			
			for(int i=0; i<data.length; i++) {
				rom[offset+i] = (byte) data[i];
			}
		}
	}
