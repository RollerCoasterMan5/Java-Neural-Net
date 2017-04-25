package neuralNetwork;
import java.util.Vector;

public class Net {
	private double error;
	private Vector<Layer> layers;
	
	public Net(Vector<Integer> topology) {
		int numLayers = topology.size();
		layers = new Vector<Layer>();
		for (int i = 0; i < numLayers; i++) {
			layers.addElement(new Layer());
			int numOutputs = i == topology.size() -1 ? 0 : topology.get(i+1);
			
			for (int j = 0; j <= topology.get(i); j++) {
				layers.lastElement().add(new Neuron(numOutputs, j));
			}
			
			layers.lastElement().lastElement().setOutputVal(1.0);
		}
	}
	
	void feedForward(Vector<Double> inputVals) {
		assert(inputVals.size() == layers.get(0).size() - 1);
		
		for (int i = 0; i < inputVals.size(); i++) {
			layers.get(0).get(i).setOutputVal(inputVals.get(i));
		}
		
		for (int i = 1; i < layers.size(); i++) {
			for (int j = 0; j < layers.get(i).size() - 1; j++) {
				layers.get(i).get(j).feedForward(layers.get(i-1));
			}
		}
	}
	
	void backProp(Vector<Double> targetVals) {
		Layer outputLayer = layers.lastElement();
		error = 0.0;
		
		for (int i = 0; i < outputLayer.size() - 1; i++) {
			double delta = targetVals.get(i) - outputLayer.get(i).getOutputVal();
			error += delta * delta;
		}
		error /= outputLayer.size() - 1;
		error = Math.sqrt(error);
		
		for (int i = 0; i < outputLayer.size() - 1; i++) {
			outputLayer.get(i).calcOutputGradients(targetVals.get(i));
		}
		
		for (int i = layers.size() - 2; i > 0; i--) {
			Layer hiddenLayer = layers.get(i);
			Layer nextLayer = layers.get(i+1);
			
			for (int j = 0; j < hiddenLayer.size(); j++) {
				hiddenLayer.get(j).calcHiddenGradients(nextLayer);
			}
		}
		
		for (int i = layers.size() - 1; i > 0; i--) {
			Layer layer = layers.get(i);
			Layer prevLayer = layers.get(i-1);
			
			for (int j = 0; j < layer.size() - 1; j++) {
				layer.get(j).updateInputWeights(prevLayer);
			}
		}
	}
	
	void getResults(Vector<Double> resultVals) {
		resultVals.clear();
		
		for (int i = 0; i < layers.lastElement().size() - 1; i++) {
			resultVals.add(layers.lastElement().get(i).getOutputVal());
		}
	}
}
