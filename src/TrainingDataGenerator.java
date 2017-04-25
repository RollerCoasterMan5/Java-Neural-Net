import java.io.BufferedWriter;
import java.io.FileWriter;

public class TrainingDataGenerator {
	public static void main(String[] args) throws Exception {
		BufferedWriter file = new BufferedWriter(new FileWriter("C:\\Users\\CHRIS-LAPTOP\\workspace\\Neural Network\\src\\TrainingData.txt"));
		
		file.write("topology: 2 4 1\n");
		for (int i = 0; i < 2000; i++) {
			int num1 = (int)(Math.random() * 2);
			int num2 = (int)(Math.random() * 2);
			int t = num1 ^ num2;
			file.write("in: " + num1 + " " + num2 + "\n");
			file.write("out: " + t + "\n");
		}
		
		file.close();
		
		System.out.println("Done generating training data!");
	}
}
