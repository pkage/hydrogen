package org.kagelabs.pkbasic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Utility class to generate a line bundle from a file
 * @author Patrick Kage
 *
 */
public class FileExtractor {
	public LineBundle extract(String file) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;
		LineBundle bundle = new LineBundle();
		try {
			while ((line = br.readLine()) != null) {
			   bundle.add(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bundle;
	}
}
