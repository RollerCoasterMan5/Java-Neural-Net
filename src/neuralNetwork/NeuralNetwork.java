package neuralNetwork;
import java.util.Vector;

public class NeuralNetwork {
	public static void main(String[] args) {
		TrainingData trainData = new TrainingData(NeuralNetwork.class.getResource("/neuralNetwork/TrainingData.txt").getPath());
		
		Vector<Integer> topology = new Vector<Integer>();
		trainData.getTopology(topology);
		Net myNet = new Net(topology);
		
		Vector<Double> inputVals = new Vector<Double>(),
					   targetVals = new Vector<Double>(),
					   resultVals = new Vector<Double>();
		int trainingPass = 0;
		
		while (!trainData.isEof()) {
			trainingPass++;
			System.out.println();
			System.out.print("Pass " + trainingPass);
			
			if (trainData.getNextInputs(inputVals) != topology.get(0)) {
				break;
			}
			showVectorVals(": Inputs:", inputVals);
			myNet.feedForward(inputVals);
			
			myNet.getResults(resultVals);
			showVectorVals("Outputs:", resultVals);
			
			trainData.getTargetOutputs(targetVals);
			showVectorVals("Targets:", targetVals);
			assert(targetVals.size() == topology.lastElement());
			
			myNet.backProp(targetVals);
		}
		System.out.println("Done!");
	}
	
	static void showVectorVals(String label, Vector<Double> vals) {
		System.out.print(label);
		for (int i = 0; i < vals.size(); i++) {
			System.out.print(" " + vals.get(i));
		}
		System.out.println();
	}
}
