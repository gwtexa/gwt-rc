package com.google.rc.shared;

import java.util.Arrays;

public class ShUtil {

	public static void main(String[] args) {
		String hex = "4348537ad2e20044";
		byte[] bytes = fromHex(hex);
		//System.out.println(new String(bytes));
		System.out.println(Arrays.toString(bytes));
		System.out.println(toHex(bytes));
		System.out.println(toHex("".getBytes()));
	}
	
	public static String toHex(String s) {
		return toHex(s.getBytes());
	}
	
	public static String toHex(byte[] bytes) {
		StringBuilder output = new StringBuilder();
		for (byte b: bytes) {
			String h = Integer.toHexString(b & 0xff);
			if (h.length() == 1) {
				output.append("0");
			}
			output.append(h);
		}
		return output.toString();
	}
	
	public static String fromHexS(String hex) {
		return new String(fromHex(hex));
	}
	
	public static byte[] fromHex(String hex) {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException("Hex string length must be even");
		}
	    byte[] output = new byte[hex.length()/2];
	    for (int i = 0; i < hex.length(); i+=2) {
	        String str = hex.substring(i, i+2);
	        output[i/2] = (byte) Integer.parseInt(str, 16);
	    }
	    return output; 
	}
	
}
