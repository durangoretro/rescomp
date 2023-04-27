package com.durangoretro.rescomp;

import java.util.HashMap;
import java.util.Map;

public class Palette {

	public static final int COLOR_00 = -16777216;	// Negro
	public static final int COLOR_01 = -16733696;	// Verde
	public static final int COLOR_02 = -65536;		// Rojo
	public static final int COLOR_03 = -22016;		// Naranja
	public static final int COLOR_04 = -16755456;	// Botella
	public static final int COLOR_05 = -16711936;	// Lima
	public static final int COLOR_06 = -43776;		// Ladrillo
	public static final int COLOR_07 = -256;		// Amarillo
	public static final int COLOR_08 = -16776961;	// Azul
	public static final int COLOR_09 = -16733441;	// Celeste
	public static final int COLOR_10 = -65281;		// Magenta
	public static final int COLOR_11 = -21761;		// Rosita
	public static final int COLOR_12 = -16755201;	// Azur
	public static final int COLOR_13 = -16711681;	// Cian
	public static final int COLOR_14 = -43521;		// Fucsia
	public static final int COLOR_15 = -1;			// Blanco

	private static final Map<Integer, Integer> COLOR_INDEX= new HashMap<>();
			static {
				COLOR_INDEX.put(COLOR_00, 0);
				COLOR_INDEX.put(COLOR_01, 1);
				COLOR_INDEX.put(COLOR_02, 2);
				COLOR_INDEX.put(COLOR_03, 3);
				COLOR_INDEX.put(COLOR_04, 4);
				COLOR_INDEX.put(COLOR_05, 5);
				COLOR_INDEX.put(COLOR_06, 6);
				COLOR_INDEX.put(COLOR_07, 7);
				COLOR_INDEX.put(COLOR_08, 8);
				COLOR_INDEX.put(COLOR_09, 9);
				COLOR_INDEX.put(COLOR_10, 10);
				COLOR_INDEX.put(COLOR_11, 11);
				COLOR_INDEX.put(COLOR_12, 12);
				COLOR_INDEX.put(COLOR_13, 13);
				COLOR_INDEX.put(COLOR_14, 14);
				COLOR_INDEX.put(COLOR_15, 15);
			}
	private static final Map<Integer, Byte> COLOR_BYTE= new HashMap<>();

			static {
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_01,(byte)0x01);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
				COLOR_BYTE.put(COLOR_00,(byte)0x00);
			}

	public static int getColorIndex(int rgb) {
		return COLOR_INDEX.getOrDefault(rgb, -1);
	}
	
	public static int getIntColorFromIndex(int index) {
		switch(index) {
			case 0:  return COLOR_00;
			case 1:  return COLOR_01;
			case 2:  return COLOR_02;
			case 3:  return COLOR_03;
			case 4:  return COLOR_04;
			case 5:  return COLOR_05;
			case 6:  return COLOR_06;
			case 7:  return COLOR_07;
			case 8:  return COLOR_08;
			case 9:  return COLOR_09;
			case 10: return COLOR_10;
			case 11: return COLOR_11;
			case 12: return COLOR_12;
			case 13: return COLOR_13;
			case 14: return COLOR_14;
			case 15: return COLOR_15;
			default : return 0;
		}
	}
	
	public static byte getColorByte(int rgb) throws Exception {
		switch(rgb) {
			case COLOR_00: return 0x00;
			case COLOR_01: return 0x01;
			case COLOR_02: return 0x02;
			case COLOR_03: return 0x03;
			case COLOR_04: return 0x04;
			case COLOR_05: return 0x05;
			case COLOR_06: return 0x06;
			case COLOR_07: return 0x07;
			case COLOR_08: return 0x08;
			case COLOR_09: return 0x09;
			case COLOR_10: return 0x0a;
			case COLOR_11: return 0x0b;
			case COLOR_12: return 0x0c;
			case COLOR_13: return 0x0d;
			case COLOR_14: return 0x0e;
			case COLOR_15: return 0x0f;
			default : throw new Exception("Color not supported!");
		}
	}
	
	public String getColorSpanish(int rgb) {
		switch(rgb) {
			case COLOR_00: return "Negro";
			case COLOR_01: return "Verde";
			case COLOR_02: return "Rojo";
			case COLOR_03: return "Naranja";
			case COLOR_04: return "Botella";
			case COLOR_05: return "Lima";
			case COLOR_06: return "Ladrillo";
			case COLOR_07: return "Amarillo";
			case COLOR_08: return "Azul";
			case COLOR_09: return "Celeste";
			case COLOR_10: return "Magenta";
			case COLOR_11: return "Rosita";
			case COLOR_12: return "Azur";
			case COLOR_13: return "Cian";
			case COLOR_14: return "Fucsia";
			case COLOR_15: return "Blanco";
			default : return "";
		}
	}
	
	public static byte getColorByte(int rgbLeft, int rgbRight) throws Exception {
		return (byte) ((byte) (getColorByte(rgbLeft)<<4) | getColorByte(rgbRight));
	}
	
	public static int getIntColorRightFromByte(byte value) {
		return getIntColorFromIndex(value & 0x0f);
	}
	
	public static int getIntColorLeftFromByte(byte value) {
		return getIntColorFromIndex((value & 0xf0)>>4);
	}
}
