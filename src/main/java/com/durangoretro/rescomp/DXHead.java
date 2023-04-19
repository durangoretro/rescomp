package com.durangoretro.rescomp;

import java.nio.charset.Charset;
import java.util.Calendar;

public class DXHead {
	public static final int COMMENTS_OFFSET = 0x0008;
	public static final int HASHS_OFFSET = 0x00E6;
	public static final int VERSION_OFFSET = 0x00F6;
	public static final int TIMESTAMP_OFFSET = 0x00F8;

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
		rom[offset+i] = 0;
        i++;		
		for(int j=0; j<descriptionData.length; j++, i++) {
			rom[offset+i] = descriptionData[j];
		}		
		rom[offset+i] = 0;
        i++;
	}
	
	public static void copyBuildHashs(byte[] rom) {
		
		for(int i=0; i<8; i++) {
			rom[HASHS_OFFSET+i]=rom[Stamper.findSignatureOffset(rom, Stamper.DCLIB_STAMP)+i];
		}
		for(int i=0; i<8; i++) {
			rom[HASHS_OFFSET+8+i]=rom[Stamper.findSignatureOffset(rom, Stamper.BUILD_STAMP)+i];
		}
	}
	
	/**
	 * 
	 * @param rom
	 * @param versionStr 2.5-RC-001
	 */
	public static void setVersion(byte[] rom, String versionStr) {
		/*
		 Little-endian 16-bit vvvvrrrr ppbbbbbb, 
		 where v is version number, r revision, b build and 
		 p phase (%00=alpha, %01=beta, %10=Release Candidate, %11=final)
		 */
		byte version = 0x10;
		byte build = (byte)0xC0;
		
		rom[VERSION_OFFSET]=version;
		rom[VERSION_OFFSET+1]=build;
	}
	
	public static void setTimestamp(byte[] rom) {
		Calendar now = Calendar.getInstance();
		int y=now.get(Calendar.YEAR);
		int m=now.get(Calendar.MONTH);
		int d=now.get(Calendar.DAY_OF_MONTH);
		int h=now.get(Calendar.HOUR_OF_DAY);
		int min=now.get(Calendar.MINUTE);
		
		int date = (y-1980)<<9 | m << 5 | d;
		int time = h<<11 | min << 5;
		
		rom[TIMESTAMP_OFFSET]=(byte) time;
		rom[TIMESTAMP_OFFSET+1]=(byte) date;
	}
}
