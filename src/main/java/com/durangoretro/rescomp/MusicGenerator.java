package com.durangoretro.rescomp;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MusicGenerator {

	public static byte[] convertToDurango(File file) throws Exception {
		Integer divisions = 1;
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		Iterator<Element> measures = doc.getRootElement().element("part").elementIterator("measure");
		while(measures.hasNext()) {
			Element measure = measures.next();
			if(measure.element("attributes")!=null && measure.element("attributes").element("divisions")!=null) {
				divisions = Integer.valueOf(measure.element("attributes").element("divisions").getStringValue());
			}
			System.out.println("Divisions: " + divisions);
			
			Iterator<Element> notesIterator = measure.elementIterator("note");
			while(notesIterator.hasNext()) {
				Element note = notesIterator.next();
				String step = note.element("pitch").element("step").getStringValue();
				Integer duration = Integer.parseInt(note.element("duration").getStringValue());
				System.out.println("NOTA: " + step + " -> "+ duration);
			}
		}
		
		byte pixels[] = new byte[10];
		return pixels;
	}

}
