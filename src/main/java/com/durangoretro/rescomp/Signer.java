package com.durangoretro.rescomp;

import java.nio.charset.Charset;

public class Signer {
		private static final String SIGNATURE_STAMP = "SIGNATURE:[";
		
		
		private static byte[] calculateChecksum(byte[] rom) {
			byte[] signature = new byte[2];
			int start = 64*1024-rom.length;
			for(int i=0; i<rom.length; i++) {
				if(start+i>=0xdf00 && start+i<=0xdfff) {
					continue;
				}
				//System.out.print("["+String.format("%02X", rom[i])+"]");
				signature[0]=(byte)((signature[0]+rom[i])%256);
				signature[1]=(byte)((signature[1]+signature[0])%256);
			}
			return signature;
		}

		private static int findSignatureOffset(byte[] rom) {
			return new String(rom, Charset.forName("ASCII")).indexOf(SIGNATURE_STAMP) + SIGNATURE_STAMP.length();
		}
		
		public static void sign(byte[] rom) throws Exception {
			int offset = findSignatureOffset(rom);
			int start = 64*1024-rom.length;
			System.out.println("Validating signature at " + String.format("%02X", start+offset));
			
			byte[] signature = calculateChecksum(rom);
			if(signature[0] == rom[offset] && signature[1] == rom[offset+1]){
				System.out.println("Already signed!!!");
				return;
			}
			else {
				rom[offset] = signature[0];
				rom[offset+1] = signature[1];
			}
		}
	}
