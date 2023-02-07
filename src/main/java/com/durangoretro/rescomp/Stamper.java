package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class Stamper {
		public static final String BUILD_STAMP = "BUILD:[";

		private static int findSignatureOffset(byte[] rom, String stamp) {
			return new String(rom, Charset.forName("ASCII")).indexOf(stamp) + BUILD_STAMP.length();
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
		
		public static void stampHexValue(byte[] rom, String stamp, String value) throws Exception {
			int offset = findSignatureOffset(rom, stamp);
			int start = 64*1024-rom.length;
			System.out.println("Adding Stamp: "+stamp+". value: " + value +" at " + String.format("%02X", start+offset));
			
			if(value.length()!=8) {
				throw new Exception("Invalid stamp length. It should be 16 char");
			}
			byte[] data = hexStringToByteArray(value);
			
			for(int i=0; i<data.length; i++) {
				rom[offset+i] = data[i];
			}
		}
	}
