package com.durangoretro.rescomp;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MusicGenerator {

	public static byte[] convertToDurango(File file) throws Exception {
		Integer divisions = 1;
		List<MusicNote> notes = new LinkedList<>();
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
				String octave = note.element("pitch").element("octave").getStringValue();
				Integer duration = Integer.parseInt(note.element("duration").getStringValue());
				notes.add(new MusicNote(Notes.valueOf(step+octave), 1));
			}
			
			for(MusicNote n : notes) {
				System.out.println("NOTA: " + n.getNote().name + " -> "+ n.getDuration());
			}
		}
		
		byte pixels[] = new byte[10];
		return pixels;
	}
	
	public static class MusicNote {
		private Notes note;
		private Integer duration;
		
		public MusicNote(Notes note, Integer duration) {
			super();
			this.note = note;
			this.duration = duration;
		}
		public Notes getNote() {
			return note;
		}
		public void setNote(Notes note) {
			this.note = note;
		}
		public Integer getDuration() {
			return duration;
		}
		public void setDuration(Integer duration) {
			this.duration = duration;
		}		
	}

}
