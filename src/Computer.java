
public class Computer {
	private int[] randomNumbers;
	private String[] output_result = new String[6];
	
	public Computer(int[] randomNumbers) {
		this.randomNumbers = randomNumbers;
	}
	
	public int result() {
		/*for (int j = 1; j < randomNumbers.length; j++) {
			System.out.print(randomNumbers[j] + "  ");
		}*/

		String[] results = new String[5];
		//String[] output_result = new String[6];
		int min = 10000;
		int counter = 0;
		int count = 0;
		while (count != 10000) {
			CircularQueue numbersQueue = new CircularQueue(6);
			for (int j = 1; j < randomNumbers.length; j++) {
				numbersQueue.enqueue(randomNumbers[j]);
			}
			int n = 6;
			while (n != 1) {
				int result = 0;
				int rand = (int) (Math.random() * 4);
				int randq1 = (int) (Math.random() * n) + 1;
				for (int i = 0; i < randq1 - 1; i++) {
					numbersQueue.enqueue(numbersQueue.dequeue());
				}
				int temp1 = (int) numbersQueue.dequeue();

				int randq2 = (int) (Math.random() * (n - 1)) + 1;
				for (int i = 0; i < randq2 - 1; i++) {
					numbersQueue.enqueue(numbersQueue.dequeue());
				}
				int temp2 = (int) numbersQueue.dequeue();

				String string = null;
				n = n - 1;
				if (rand == 0) {
					result = temp1 + temp2;
					string = String.valueOf(temp1) + " + "
							+ String.valueOf(temp2) + " = " + result;
				} else if (rand == 1 && temp1 > temp2) {
					result = Math.abs(temp1 - temp2);
					string = String.valueOf(temp1) + " - "
							+ String.valueOf(temp2) + " = " + result;
				}else if (rand == 1 && temp2 > temp1) {
					result = Math.abs(temp1 - temp2);
					string = String.valueOf(temp2) + " - "
							+ String.valueOf(temp1) + " = " + result;
				} else if (rand == 2) {
					result = temp1 * temp2;
					string = String.valueOf(temp1) + " * "
							+ String.valueOf(temp2) + " = " + result;
				} else if (rand == 3 && temp1 > temp2 && temp2 != 0) {
					result = temp1 / temp2;
					string = String.valueOf(temp1) + " / "
							+ String.valueOf(temp2) + " = " + result;
				} else if (rand == 3 && temp2 > temp1 && temp1 != 0) {
					result = temp2 / temp1;
					string = String.valueOf(temp2) + " / "
							+ String.valueOf(temp1) + " = " + result;
				}
				numbersQueue.enqueue(result);
				results[counter] = string;
				counter++;
			}
			counter = 0;
			int new_result = (int) numbersQueue.dequeue();

			if (Math.abs(Operations.targetNumber - new_result) < min) {
				//System.out.println(count);
				min = Math.abs(Operations.targetNumber - new_result);
				output_result[0] = String.valueOf(new_result);
				for (int i = 1; i <= 5; i++) {
					output_result[i] = results[i - 1];
				}
			}
			/*for (int i = 0; i < randomNumbers.length; i++) {
				numbersQueue.enqueue(randomNumbers[i]);
			}*/
			count++;
		}
		System.out.println();
		return Integer.valueOf(output_result[0]);
	}
	
	public void computerSolutionSteps(){
		System.out.println("Computer's solution steps:");
		System.out.println("Computer's Answer : " + output_result[0]);
		for (int i = 1; i < output_result.length; i++) {
			System.out.println("işlem : " + output_result[i]);
		}
	}
}
