package com.skillstorm.im.services;

public class URLParserService {

	public int extractIdFromURL(String url) {
//		System.out.println(url); // /12/123
		String[] splitString = url.split("/"); // [12, 123]
		int id = Integer.parseInt(splitString[1]);
		return id; // Throws an exception if this isn't a int
	}
	
	public int[] extractIdsFromURL(String url) {
		String[] splitString = url.split("/");
		int[] ids = new int[splitString.length];
		for (int i = 1; i < splitString.length; i++) {
			ids[i-1] = Integer.parseInt(splitString[i]);
		}
		return ids;
	}

}
