package com.durangoretro.rescomp;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MusicGenerator {

	public static byte[] convertToDurango(File file) throws Exception {
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		Element part = doc.getRootElement().element("part");
		Integer divisions = Integer.valueOf(part
				.element("measure").element("attributes").element("divisions").getStringValue());
		System.out.println("Divisions: " + divisions);
		
		Iterator<Element> notesIterator = part.element("measure").elementIterator("note");
		while(notesIterator.hasNext()) {
			Element note = notesIterator.next();
			String step = note.element("pitch").element("step").getStringValue();
			Integer duration = Integer.parseInt(note.element("duration").getStringValue());
			System.out.println("NOTA: " + step + " -> "+ duration);
		}
		
		byte pixels[] = new byte[10];
		return pixels;
	}

}
