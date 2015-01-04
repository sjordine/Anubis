package br.unicamp.ic.jbio.msf;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MSFParser {

	private enum ParseState {
		START, PILEUP, SEQUENCES
	}

	public static List<Entry<String,String>> load(String filePath) throws IOException {

		String line;
		String[] elements;
		ParseState state = ParseState.START;

		HashMap<String, StringBuilder> sequences = new HashMap<String, StringBuilder>();
		List<Map.Entry<String,String>> returnValue = new ArrayList<Map.Entry<String,String>>();
		List<String> sequenceNames = new ArrayList<String>();

		if (filePath != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath)));

			while ((line = reader.readLine()) != null) {
				// process line
				switch (state) {
				case START:
					if (line.toLowerCase().contains("pileup")
							|| line.toLowerCase().contains("pile up")) {
						state = ParseState.PILEUP;
					}
					break;
				case PILEUP:
					if (line.contains("//")) {
						state = ParseState.SEQUENCES;
					}
					break;
				case SEQUENCES:
					// Console.WriteLine(line);
					elements = line.split("\\s+");
					if (elements.length > 1) {
						String name = elements[0];
						StringBuilder targetSequence = null;

						if (!sequences.containsKey(name)) {
							targetSequence = new StringBuilder();
							sequences.put(name, targetSequence);
							sequenceNames.add(name);
						} else {
							targetSequence = sequences.get(name);
						}

						for (int i = 1; i < elements.length; i++) {
							if (elements[i].length() > 0) {
								// Valid sequence - add
								targetSequence.append(elements[i]);
							}
						}
					}

					break;
				}
			}
			
			//Convert

            for(String sequence:sequenceNames)
            {
                String targetSequence = SequenceUtil.NormalizeGaps(sequences.get(sequence).toString());
                
                Map.Entry<String,String> entry =
                	    new AbstractMap.SimpleEntry<String, String>(sequence, targetSequence);

                returnValue.add(entry);

                
            }


		}
		
		return returnValue;
	}

}
