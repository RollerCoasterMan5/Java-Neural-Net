import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class TrainingData {
	BufferedReader dataFile;
	
	public TrainingData(String fileName) {
		try {
			dataFile = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}
	
	void getTopology(Vector<Integer> topology) {
		String[] line;
		
		try {
			line = dataFile.readLine().trim().split(" ");
		} catch (IOException e) {
			line = null;
		}
		
		if (line == null) {
			return;
		}
		
		if (isEof() || line[0].compareTo("topology:") != 0) {
			topology.clear();
			return;
		}
		
		for (int i = 1; i < line.length; i++) {
			topology.add(Integer.valueOf(line[i]));
		}
	}
	
	int getNextInputs(Vector<Double> inputVals) {
		inputVals.clear();
		
		String[] line;
		
		try {
			line = dataFile.readLine().trim().split(" ");
		} catch (IOException e) {
			line = null;
		}
		
		if (line == null) {
			return -1;
		}
		
		if (line[0].compareTo("in:") == 0) {
			for (int i = 1; i < line.length; i++) {
				inputVals.add(Double.valueOf(line[i]));
			}
		}
		
		return inputVals.size();
	}
	
	int getTargetOutputs(Vector<Double> targetOutputVals) {
		targetOutputVals.clear();
		
		String[] line;
		
		try {
			line = dataFile.readLine().trim().split(" ");
		} catch (IOException e) {
			line = null;
		}
		
		if (line == null) {
			return -1;
		}
		
		if (line[0].compareTo("out:") == 0) {
			for (int i = 1; i < line.length; i++) {
				targetOutputVals.add(Double.valueOf(line[i]));
			}
		}
		
		return targetOutputVals.size();
	}
	
	boolean isEof() {
		try {
			return !dataFile.ready();
		} catch (IOException e) {
			return true;
		}
	}
}
