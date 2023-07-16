package com.durangoretro.rescomp;

public class Util {
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
}
