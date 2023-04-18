package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class DXHead {
	public static final int COMMENTS_OFFSET = 8;

	public static void stampTitleDescription(byte[] rom, String title, String description) throws Exception {
		int offset = COMMENTS_OFFSET;
		int start = 64*1024-rom.length;
		System.out.println("Adding title: "+title+". description: " + description +" at " + String.format("%02X", start+offset));
		
		byte[] titleData = title.getBytes(Charset.forName("ASCII"));
		byte[] descriptionData = description.getBytes(Charset.forName("ASCII"));
		int i=0;
		for(int j=0; j<titleData.length; j++, i++) {
			rom[offset+i] = titleData[j];
		}
		i++;
		rom[offset+i] = 0;
		for(int j=0; j<descriptionData.length; j++, i++) {
			rom[offset+i] = descriptionData[j];
		}
		i++;
		rom[offset+i] = 0;
	}
}
