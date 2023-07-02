package com.durangoretro.rescomp;

public enum Notes {
	F3("F3", 0),
	FS3("FS3", 1),
	G3("G3", 2),
	GS3("GS3", 3),
	A3("A3", 4),
	AS3("AS3", 5),
	B3("B3", 6),

	C4("C4", 7),
	CS4("CS4", 8),
	D4("D4", 9),
	DS4("DS4", 10),
	E4("E4", 11),
	F4("F4", 12),
	FS4("FS4", 13),
	G4("G4", 14),
	GS4("GS4", 15),
	A4("A4", 16),
	AS4("AS4", 17),
	B4("B4", 18),

	C5("C5", 19),
	CS5("CS5", 20),
	D5("D5", 21),
	DS5("DS5", 22),
	E5("E5", 23),
	F5("F5", 24),
	FS5("FS5", 25),
	G5("G5", 26),
	GS5("GS5", 27),
	A5("A5", 28),
	AS5("AS5", 29),
	B5("B5", 30),

	C6("C6", 31),
	CS6("CS6", 32),
	D6("D6", 33),
	DS6("DS6", 34),
	E6("E6", 35),
	F6("F6", 36),
	FS6("FS6", 37),
	G6("G6", 38),
	GS6("GS6", 39),
	A6("A6", 40),
	AS6("AS6", 41),
	B6("B6", 42),

	BLENCE("BLENCE",  43);
	
	public final String name;
	public final Integer value;
	
	Notes(String n, Integer v) {
		this.name=n;
		this.value=v;
	}

}
