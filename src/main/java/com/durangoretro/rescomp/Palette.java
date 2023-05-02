package com.durangoretro.rescomp;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Palette Class: Information About Durango Palette
 * here you can find information about the palette used by durango.
 *
 * @Author Emilio Lopez and Improved By Victor Suarez
 * @Date 15-10-2022
 */
public class Palette {

    /**
     * Black
     */
    public static final int COLOR_00 = -16777216;	// Negro
    /**
     * Green
     */
    public static final int COLOR_01 = -16733696;	// Verde
    /**
     * Red
     */
    public static final int COLOR_02 = -65536;		// Rojo
    /**
     * Naranja
     */
    public static final int COLOR_03 = -22016;		// Naranja
    /**
     * Green Bottle
     */
    public static final int COLOR_04 = -16755456;	// Botella
    /**
     * Lime
     */
    public static final int COLOR_05 = -16711936;	// Lima
    /**
     * Brown (brick)
     */
    public static final int COLOR_06 = -43776;		// Ladrillo
    /**
     * Yellow
     */
    public static final int COLOR_07 = -256;		// Amarillo
    /**
     * Blue
     */
    public static final int COLOR_08 = -16776961;	// Azul
    /**
     * Sky Blue
     */
    public static final int COLOR_09 = -16733441;	// Celeste
    /**
     * Magenta
     */
    public static final int COLOR_10 = -65281;		// Magenta
    /**
     * Pink
     */
    public static final int COLOR_11 = -21761;		// Rosita
    /**
     * Azur
     */
    public static final int COLOR_12 = -16755201;	// Azur
    /**
     * Cyan
     */
    public static final int COLOR_13 = -16711681;	// Cian
    /**
     * Fucsia
     */
    public static final int COLOR_14 = -43521;		// Fucsia
    /**
     * White
     */
    public static final int COLOR_15 = -1;			// Blanco

    /**
     * COlor Index Map. Store the Color inforamtion and returns the Color Index
     */
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

    /**
     * Color Byte map. Key-Value map Color Information to Byte.
     */
    private static final Map<Integer, Byte> COLOR_BYTE= new HashMap<>();

    static {
        COLOR_BYTE.put(COLOR_00,(byte)0x00);
        COLOR_BYTE.put(COLOR_01,(byte)0x01);
        COLOR_BYTE.put(COLOR_02,(byte)0x02);
        COLOR_BYTE.put(COLOR_03,(byte)0x03);
        COLOR_BYTE.put(COLOR_04,(byte)0x04);
        COLOR_BYTE.put(COLOR_05,(byte)0x05);
        COLOR_BYTE.put(COLOR_06,(byte)0x06);
        COLOR_BYTE.put(COLOR_07,(byte)0x07);
        COLOR_BYTE.put(COLOR_08,(byte)0x08);
        COLOR_BYTE.put(COLOR_09,(byte)0x09);
        COLOR_BYTE.put(COLOR_10,(byte)0x0a);
        COLOR_BYTE.put(COLOR_11,(byte)0x0b);
        COLOR_BYTE.put(COLOR_12,(byte)0x0c);
        COLOR_BYTE.put(COLOR_13,(byte)0x0d);
        COLOR_BYTE.put(COLOR_14,(byte)0x0e);
        COLOR_BYTE.put(COLOR_15,(byte)0x0f);
    }

    /**
     * Reverse Color Index Map. From Index To Color Information
     */
    private static final Map<Integer,Integer> INDEX_COLOR_MAP =
            COLOR_INDEX.entrySet().stream()
                    .collect(Collectors.toMap(Map.Entry::getValue,Map.Entry::getKey));

    /**
     * Color Spanish. Map with Color information to Spanish Translation.
     */
    private static final Map<Integer,String> COLOR_SPANISH= new HashMap<>();

    static {
        COLOR_SPANISH.put(COLOR_00,"Negro");
        COLOR_SPANISH.put(COLOR_01,"Verde");
        COLOR_SPANISH.put(COLOR_02,"Rojo");
        COLOR_SPANISH.put(COLOR_03,"Naranja");
        COLOR_SPANISH.put(COLOR_04,"Botella");
        COLOR_SPANISH.put(COLOR_05,"Lima");
        COLOR_SPANISH.put(COLOR_06,"Ladrillo");
        COLOR_SPANISH.put(COLOR_07,"Amarillo");
        COLOR_SPANISH.put(COLOR_08,"Azul");
        COLOR_SPANISH.put(COLOR_09,"Celeste");
        COLOR_SPANISH.put(COLOR_10,"Magenta");
        COLOR_SPANISH.put(COLOR_11,"Rosita");
        COLOR_SPANISH.put(COLOR_12,"Azur");
        COLOR_SPANISH.put(COLOR_13,"Cian");
        COLOR_SPANISH.put(COLOR_14,"Fucsia");
        COLOR_SPANISH.put(COLOR_15,"Blanco");
    }

    /**
     * Get the Color Index
     * @param rgb rgb Color
     * @return Color Index. Return -1 if the color is not found
     */
    public static int getColorIndex(int rgb) {
        return COLOR_INDEX.getOrDefault(rgb, -1);
    }

    /**
     * Get Color From Index
     * @param index Color Index
     * @return Int Color. Return 0 if the index is not found
     */
    public static int getIntColorFromIndex(int index) {
       return INDEX_COLOR_MAP.getOrDefault(index,0);
    }

    /**
     * Get Color Byte
     * @param rgb RGB Color
     * @return Byte COlor
     * @throws Exception Throws an exception if the color is not found.
     */
    public static byte getColorByte(int rgb) throws Exception{

        return Optional.ofNullable(COLOR_BYTE.get(rgb))
                .orElseThrow(
                        ()->{
                            throw new RuntimeException("Color Not Supported!");
                        }
                );
    }

    /**
     * Get The Spanish Translation From the RGB Color
     * @param rgb RGB Color information
     * @return Spanish Translation or empty string if not found.
     */
    public String getColorSpanish(int rgb) {
      return COLOR_SPANISH.getOrDefault(rgb,"");
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
