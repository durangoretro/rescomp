package com.durangoretro.rescomp;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;


public class MusicGenerator {
	
	private static final int WHOLE_NOTE = 128;

	public static byte[] convertToDurango(File file) throws Exception {
		Integer divisions = 1;
		List<MusicNote> notes = new LinkedList<>();
		SAXReader xmlReader = new SAXReader();
		Document doc = xmlReader.read(file);
		
		// Iterate measures
		Iterator<Element> measures = doc.getRootElement().element("part").elementIterator("measure");
		while(measures.hasNext()) {
			Element measure = measures.next();
			if(measure.element("attributes")!=null && measure.element("attributes").element("divisions")!=null) {
				divisions = Integer.valueOf(measure.element("attributes").element("divisions").getStringValue());
			}
			// Iterate notes in each measure
			Iterator<Element> notesIterator = measure.elementIterator("note");
			while(notesIterator.hasNext()) {
				Element note = notesIterator.next();
				// Note
				if(note.element("rest")==null) {
					String step = note.element("pitch").element("step").getStringValue();
					String octave = note.element("pitch").element("octave").getStringValue();
					Integer duration = Integer.parseInt(note.element("duration").getStringValue());
					notes.add(new MusicNote(Notes.valueOf(step+octave), WHOLE_NOTE/4/divisions*duration));
				}
				// Silence
				else {
					Integer duration = Integer.parseInt(note.element("duration").getStringValue());
					notes.add(new MusicNote(Notes.REST, WHOLE_NOTE/4/divisions*duration));
				}
			}
		}
		
		byte pixels[] = new byte[notes.size()*2+2];
		int i=0;
		for(MusicNote n : notes) {
			pixels[i++] = n.getNote().value.byteValue();
			pixels[i++] = n.getDuration().byteValue();
		}
		pixels[i++]=(byte)0xff;
		pixels[i++]=(byte)0xff;
		
		
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
