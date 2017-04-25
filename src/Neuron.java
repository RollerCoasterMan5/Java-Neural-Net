import java.util.Vector;

public class Neuron {
	private int myIndex;
	private double outputVal;
	private double gradient;
	private static double eta;
	private static double alpha;
	private Vector<Connection> outputWeights;
	
	public Neuron(int numOutputs, int index) {
		outputWeights = new Vector<Connection>();
		
		for (int i = 0; i < numOutputs; i++) {
			outputWeights.add(new Connection());
		}
		
		myIndex = index;
		eta = 0.2;
		alpha = 0.5;
	}
	
	void setOutputVal(double val) {
		outputVal = val;
	}
	
	double getOutputVal() {
		return outputVal;
	}
	
	void feedForward(Layer prevLayer) {
		double sum = 0.0;
		
		for (int i = 0; i < prevLayer.size(); i++) {
			sum += prevLayer.get(i).getOutputVal() * prevLayer.get(i).outputWeights.get(myIndex).weight;
		}
		
		outputVal = transferFunction(sum);
	}
	
	void calcOutputGradients(double targetVal) {
		double delta = targetVal - outputVal;
		gradient = delta * transferFunctionDerivative(outputVal);
	}
	
	void calcHiddenGradients(Layer nextLayer) {
		double dow = sumDOW(nextLayer);
		gradient = dow * transferFunctionDerivative(outputVal);
	}
	
	void updateInputWeights(Layer prevLayer) {
		for (int i = 0; i < prevLayer.size(); i++) {
			Neuron neuron = prevLayer.get(i);
			double oldDeltaWeight = neuron.outputWeights.get(myIndex).deltaWeight;
			
			double newDeltaWeight = eta * neuron.getOutputVal() * gradient + alpha * oldDeltaWeight;
			
			neuron.outputWeights.get(myIndex).deltaWeight = newDeltaWeight;
			neuron.outputWeights.get(myIndex).weight += newDeltaWeight;
		}
	}
	
	private double sumDOW(Layer nextLayer) {
		double sum = 0.0;
		
		for (int i = 0; i < nextLayer.size() - 1; i++) {
			sum += outputWeights.get(i).weight * nextLayer.get(i).gradient;
		}
		
		return sum;
	}
	
	private static double transferFunction(double x) {
		return Math.tanh(x);
	}
	
	private static double transferFunctionDerivative(double x) {
		return 1 - x * x;
	}
}

class Connection {
	public double weight;
	public double deltaWeight;
	
	public Connection() {
		weight = Math.random();
	}
}
