package br.com.rockeit.agendamentoconsultas.utils;

import java.util.Arrays;

public class Encrypt {
	
	public static int convertBinToDec(String pBinario) {
		return Integer.parseInt(pBinario, 2);
	}

	public static String convertDecToBin(int pDecimal, int pTamanho) {
		String bin = String.format("%0" + pTamanho + "d", 0) + Integer.toBinaryString(pDecimal);
		bin = bin.substring(bin.length() - pTamanho);
		return bin;
	}
	
	public static String encrypt86(String pString, int pParam) throws Exception {

		if (pParam < 33 || pParam > 63) {
			throw new Exception("pParam (33-63)");
		}

		
		int vLength = 30;
		
		if(pString.length() < vLength) {
			pString = String.format("%-30s", pString);
		}
		
		String vString = pString;
		
		int vTotCharsNull = vLength % 3;
		String vCharsNull = "";
		if (vTotCharsNull > 0) {
			char[] chars = new char[3 - vTotCharsNull];
			Arrays.fill(chars, '\0');
			vCharsNull = String.valueOf(chars);

			vLength = vLength + 3 - vTotCharsNull;
		}
		vString = vCharsNull + vString;

		char[] v2 = vString.toCharArray();
		char[] v3 = vString.toCharArray();
		for (int i = 0; i < vLength / 2; i += 2) {
			
			char vChar1 = v2[vLength - i - 1];
			
			char vChar2 = v2[i];
			v3[i] = vChar1;
			v3[vLength - i - 1] = vChar2;
		}

		String vStringMisturada = String.valueOf(v3);

		String vStrBin = "";
		for (int i = 0; i < vLength; i++) {
			int vAsc1 = vStringMisturada.codePointAt(i);
			vStrBin += convertDecToBin(vAsc1, 8);
		}

		String vStrComp = "";
		for (int i = 0; i < vStrBin.length(); i += 6) {
			int c = convertBinToDec(vStrBin.substring(i, i + 6));
			vStrComp += (char) (pParam + c);
		}

		return vStrComp;
	}
}
