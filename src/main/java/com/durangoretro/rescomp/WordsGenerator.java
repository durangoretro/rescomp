package com.durangoretro.rescomp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;

public class WordsGenerator {

	public static byte[] convertToDurango(File file) throws Exception {
		List<String> words = new LinkedList<>();
		int size=0;
		try(BufferedReader in = new BufferedReader(new FileReader(file))){
			String word;
			while ((word = in.readLine()) != null) {
				if(word.trim().length()!=0) {
					words.add(word.toUpperCase());
					size+=word.length()+1;
				}
			}
		}

		byte pixels[] = new byte[size];
		int i=0;
		for(String s : words) {
			for(char c : s.toCharArray()) {
				pixels[i++]=(byte) c;
			}
			pixels[i++]=(byte) 0;
		}


		return pixels;
	}
}
