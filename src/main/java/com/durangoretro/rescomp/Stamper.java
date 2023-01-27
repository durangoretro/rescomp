package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class Stamper {
		private static final String BUILD_STAMP = "BUILD:[";

		private static int findSignatureOffset(byte[] rom) {
			return new String(rom, Charset.forName("ASCII")).indexOf(BUILD_STAMP) + BUILD_STAMP.length();
		}
		
		/* s must be an even-length string. */
		public static byte[] hexStringToByteArray(String s) {
		    int len = s.length();
		    byte[] data = new byte[len / 2];
		    for (int i = 0; i < len; i += 2) {
		        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
		                             + Character.digit(s.charAt(i+1), 16));
		    }
		    return data;
		}
		
		public static void stamp(byte[] rom, String stamp) throws Exception {
			int offset = findSignatureOffset(rom);
			int start = 64*1024-rom.length;
			System.out.println("Adding build version at " + String.format("%02X", start+offset));
			
			if(stamp.length()!=7) {
				throw new Exception("Invalid stamp length. It should be 7 char");
			}
			byte[] data = hexStringToByteArray("0"+stamp);
			
			for(int i=0; i<data.length; i++) {
				rom[offset+i] = data[i];
			}
		}
	}
