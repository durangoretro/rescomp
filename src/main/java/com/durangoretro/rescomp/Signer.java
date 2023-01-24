package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class Signer {
		private static final String SIGNATURE_STAMP = "SIGNATURE:[";
		private static void addSignature(int offset, byte[] rom) throws Exception {
			if(validateChecksum(rom)) {
				System.out.println("Already signed!!!");
				return;
			}
			for(int i=0; i<=256; i++) {
				for(int j=0; j<=256; j++) {
					rom[offset]=(byte)j;
					rom[offset+1]=(byte)i;
					if(validateChecksum(rom)) {
						return;
					}
				}
			}
			System.out.println("NOT FOUND!");
			throw new Exception("Checksum NOT found!");
			
		}
		
		private static boolean validateChecksum(byte[] rom) {
			int suma1=0;
			int suma2=0;
			for(int i=0; i<rom.length; i++) {
				if(rom.length==16*1024 && i>=0xdf80-0xc000 && i<=0xdfff-0xc000) {
					continue;
				}
				suma1=(suma1+rom[i])%256;
				suma2=(suma2+suma1)%256;
			}
			return suma1==0 && suma2==0;
		}

		private static int findSignatureOffset(byte[] rom) {
			return new String(rom, Charset.forName("ASCII")).indexOf(SIGNATURE_STAMP) + SIGNATURE_STAMP.length();
		}
		
		public static void signStamp(String stamp, byte[] rom) throws Exception {
			int offset = findSignatureOffset(rom);
			addSignature(offset, rom);
		}
	}
