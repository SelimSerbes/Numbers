import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Operations {
	public static int targetNumber;
	private Scanner scn = new Scanner(System.in);
	private Scanner scn2 = new Scanner(System.in);
	private int playerResultControl;
	public void timing() throws InterruptedException{
		System.out.print("Duration: ");
		for (int i = 30; i >= 0; i--) {
			System.out.print(i+" ");	
			TimeUnit.SECONDS.sleep(1);
		}
	}
	public void random(int[] random_numbers) {
		int rnd=(int)(Math.random()*899)+100;
		random_numbers[0]=rnd;
		Operations.targetNumber = rnd;
		for (int i = 1; i < 6; i++) {
			rnd=(int)(Math.random()*9)+1;
			random_numbers[i]=rnd;
		}
		rnd=(int)(Math.random()*4)+1;
		random_numbers[6]=rnd*25;
	}
	public void game_loop() throws InterruptedException {
		boolean isPlayersSolutionWrong = false;
		Round round = new Round(0);
		while(round.getPlayer1Score() < 100 && round.getComputerScore() < 100) {
			int numbers[]=new int[7];
			random(numbers);
			System.out.println("--- Round "+round.getRoundCounter()+" ----------------------");
			System.out.println();
			System.out.println("Target number: " + targetNumber);
			System.out.print("\nNumbers: ");
			for (int i = 1; i < numbers.length; i++) {
				System.out.print(numbers[i]+" ");
			}
			System.out.println("\n");
			//timing();
			System.out.println("\n----------------------------------\n");
			Computer computer = new Computer(numbers);
			System.out.println("Result numbers: ");
			System.out.print("   Player: ");
			int playerSolution = scn.nextInt();
			int computerSolution = computer.result();
			System.out.println("   Computer: " + computerSolution);
			if(true){//deðiþtirilecek ahmete sor
				System.out.print("Enter player solution: ");
				String operation = scn2.nextLine();
				if(control(operation,numbers)){
					postfixEvaluation(Infix_to_postfix(operation));
					if(playerResultControl == playerSolution){
						System.out.println("---- Correct");
						round.scoreIncrement(playerSolution, true);
						isPlayersSolutionWrong = false;
					}
					else{
						System.out.println("---- Wrong");
						isPlayersSolutionWrong = true;
					}
				}
				else{
					System.out.println("Invalid input!\n");
				}
			}
			if(Math.abs(targetNumber - computerSolution) <= Math.abs(targetNumber - playerSolution) || 
					isPlayersSolutionWrong){
				computer.computerSolutionSteps();
				round.scoreIncrement(computerSolution, false);
			}
			System.out.println("Player score: " + round.getPlayer1Score());
			System.out.println("Computer score: " + round.getComputerScore());
		}
		
	}
	public boolean control(String playerSolution, int[] randomNumbers){
		boolean flag = true;
		String[] operants={"+", "-", "*", "/", "(", ")"};
		String controlString = playerSolution;
		for (int i = 0; i < operants.length; i++) {
			controlString=controlString.replace(operants[i], " ");
		}
		controlString = controlString.replace("    ", " ").replace("   ", " ").replace("  ", " ");
		controlString = controlString.trim();
		String[] numbersArray = controlString.split(" ");
		//System.out.println("numbersArray");
		/*for (int i = 0; i < numbersArray.length; i++) {
			System.out.print(numbersArray[i] + " ");
		}*/
		//System.out.println("randomNumbers");
		/*for (int i = 0; i < randomNumbers.length; i++) {
			System.out.print(randomNumbers[i] + " ");
		}*/
		for (int i = 0; i < numbersArray.length; i++) { //number control
			for (int j = 1; j < randomNumbers.length; j++) {
				if(!numbersArray[i].equals(String.valueOf(randomNumbers[j]))){
					flag=false;
				}
				else{
					flag=true;
					break;
				}
			}
			if(!flag)
				break;
		}
		
		controlString = playerSolution;
		for (int i = 0; i < numbersArray.length; i++) {
			if(numbersArray[i].length()>1){
				controlString = controlString.replace(numbersArray[i], " ");
				System.out.println(i + " adým="+controlString);
			}
		}
		for (int i = 0; i < numbersArray.length; i++) {
			controlString = controlString.replace(numbersArray[i], " ");
			System.out.println(i + " adým="+controlString);
		}
		controlString = controlString.replace("    ", " ").replace("   ", " ").replace("  ", " ");
		controlString = controlString.trim();
		String[] operatorArray = controlString.split(" ");
		controlString=controlString.replace(" ", "");
		/*System.out.println("controlString: " + controlString);
		System.out.println("operatorArray");
		for (int i = 0; i < operatorArray.length; i++) {
			System.out.println(operatorArray[i]);
		}*/
		int operantCounter = 0;
		for (int j = 0; j < controlString.length(); j++) {
			for (int k = 0; k < operants.length; k++) {
				if(String.valueOf(controlString.charAt(j)).equals(operants[k])){
					operantCounter++;
				}
			}
		}
		if(operantCounter != controlString.length()){
			flag=false;
		}
		for (int i = 0; i < operatorArray.length; i++) { // operant control
			if(!(operatorArray[i].contains("(")) && !(operatorArray[i].contains(")")) && operatorArray[i].length() > 1){
				flag = false;
			}
			if(operatorArray[i].contains("(")){
				for (int j = 0; j < 4; j++) {
					if(operatorArray[i].contains("("+operants[j])){
						flag=false;
					}
				}
			}
			if(operatorArray[i].contains(")")){
				for (int j = 0; j < 4; j++) {
					if(operatorArray[i].contains(operants[j]+")")){
						flag=false;
					}
				}
			}
			if(operatorArray[i].contains(")(") || operatorArray[i].contains("()")){
				flag=false;
			}
		}
		int parenthesisCount = 0;
		for (int i = 0; i < playerSolution.length(); i++) {
			if(playerSolution.charAt(i)=='(')
				parenthesisCount++;
			if(playerSolution.charAt(i)==')'){
				parenthesisCount--;
			}
			if(parenthesisCount<0){
				flag=false;
			}
		}
		if(parenthesisCount != 0){
			flag=false;
		}
		return flag;
	}
	public String Infix_to_postfix(String operation) {
		System.out.println("-- Infix to Postfix Conversion Steps --\n");
		Stack s = new Stack(operation.length());
		String sum = "";
		int count = 0;
		// int count2=0;
		int counter = 0;
		int step = 1;
		for (int i = 0; i < operation.length(); i++) {
			boolean flag2 = false;
			String operant = operation.substring(i, i + 1);
			if (!operant.equals("+") && !operant.equals("-") && !operant.equals("/") && !operant.equals("*")
					&& !operant.equals("(") && !operant.equals(")")) {
				if (counter == 0) {
					sum = sum + operant;
				} else {
					sum = sum + operant;
				}
				counter++;
				if (i == operation.length() - 1) {
					while (!s.isEmpty()) {
						sum = sum + " " + (String) s.pop();
					}
					flag2 = true;
				}
			} else if (operant.equals("+") || operant.equals("-") || operant.equals("*") || operant.equals("/")
					|| operant.equals("(") || operant.equals(")")) {
				// boolean flag = false;
				if (count != 0 && (s.peek().equals("*") || s.peek().equals("/")) && !operant.equals("(")) {
					sum = sum + " ";
					sum = sum + (String) s.pop();
					count = 0;
				}
				if (operant.equals("*") || operant.equals("/"))
					count++;
				if (s.size() > 0 && operant.equals("+") && s.peek().equals("-"))
					sum = sum + " " + (String) s.pop() + " ";
				/*
				 * if (count2 != 0 && (s.peek().equals("-") && !operant.equals("("))) { sum =
				 * sum + " "; sum = sum + (String) s.pop(); count2 = 0; } if
				 * (operant.equals("-")) count2++;
				 */
				if (operant.equals("(")) {
					s.push(operant);
					counter = 0;
				} else if (operant.equals(")")) {
					int size = s.size();
					sum = sum + " ";
					for (int j = 0; j < size; j++) {
						if (s.peek().equals("(")) {
							s.pop();
							// flag = true;
							// break;
						} else {
							sum = sum + (String) s.pop();
						}
						sum = sum + " ";
					}
					count = 0;
					// count2=0;
				} else {
					s.push(operant);
					sum = sum + " ";
					counter = 0;
				}
				sum = sum.replace("  ", " ");
				sum = sum.replace("   ", " ");
				System.out.print("Step " + step + " - " + operation + "\n");
				
				if(step<=9) 
					System.out.print("         ");			
				else 
					System.out.print("          ");				
				for (int j = 0; j < i + 1; j++) {
					System.out.print("^");
				}
				Stack temp = new Stack(10);
				int size_temp = s.size();
				System.out.print("\n__________\n| ");
				for (int j = 0; j < size_temp; j++) {

					System.out.print(s.peek() + " ");
					temp.push(s.pop());
				}
				System.out.println("\n——————————");
				while (!temp.isEmpty()) {
					s.push(temp.pop());
				}
				System.out.println("\n" + sum + "\n");
				step++;
				/*
				 * if (flag == true) { while (!s.isEmpty()) { sum = sum + (String) s.pop() +
				 * " "; } System.out.print("Step " + step + " - " + operation + "\n");
				 * System.out.print("         "); for (int j = 0; j < i + 1; j++) {
				 * System.out.print("^"); } System.out.print("\n__________\n| ");
				 * System.out.println("\n——————————"); System.out.println("\n" + sum); step++; }
				 */
				// DÝSPLAY
			}
			
			if (flag2 == true) {
				
				sum = sum.replace("  ", " ");
				sum = sum.replace("   ", " ");
				while (!s.isEmpty()) {
					sum = sum + (String) s.pop() + " ";
				}
				System.out.print("Step " + step + " - " + operation + "\n");
				if(step<=9) 
					System.out.print("         ");				
				else 
					System.out.print("          ");			
				for (int j = 0; j < i+1; j++) {
					System.out.print("^");
				}
				System.out.print("\n__________\n| ");
				System.out.println("\n——————————");
				System.out.println("\n" + sum);
				step++;
			}
		}

		// System.out.print(sum);
		return sum;
	}


	public void postfixEvaluation (String postfixForm){
		int counter = 0, stepCounter = 1;
		String[] postfix = postfixForm.split(" ");
		boolean printControl = false;
		Stack operationsStack = new Stack(postfixForm.length());
		Stack operationsStackTemp = new Stack(postfixForm.length());
		//char[] operationArray = {'+', '-', '*', '/'};
		System.out.println("Postfix expression:\n"+ postfixForm + "\n");
		System.out.println("-- Postfix Evaluation Steps --\n");
		for (int i = 0; i < postfix.length; i++) {
			if(postfix[i].equals("+")){
				int sum = 0;
				sum += (int)operationsStack.pop();
				sum += (int)operationsStack.pop();
				operationsStack.push(sum);
				printControl = true;
			}
			else if(postfix[i].equals("-")){
				int firstNumber = (int)operationsStack.pop();
				int secondNumber = (int)operationsStack.pop();
				int difference = secondNumber - firstNumber;
				operationsStack.push(difference);
				printControl = true;
			}
			else if(postfix[i].equals("*")){
				int product = 1;
				product *= (int)operationsStack.pop();
				product *= (int)operationsStack.pop();
				operationsStack.push(product);
				printControl = true;
			}
			else if(postfix[i].equals("/")){
				int divider = (int)operationsStack.pop();
				int divi = (int)operationsStack.pop();
				int division = divi / divider;
				operationsStack.push(division);
				printControl = true;
			}
			else{
				if(i != postfix.length-1){
					if(postfix[i+1].equals("+") || postfix[i+1].equals("-") || postfix[i+1].equals("*") || postfix[i+1].equals("/")){
						printControl = true;
					}
					int a = Integer.parseInt(postfix[i]);
					operationsStack.push(a);
				}
			}
			if(printControl){
				System.out.println("Step " + stepCounter++);
				for (int j = 0; j < postfix.length; j++) {
					System.out.print(postfix[j] + " ");
				}
				System.out.println();
				counter = 2*i +1;
				for (int j = 0; j <= i; j++) {
					if(postfix[j].length() == 2)
						counter++;
					else if(postfix[j].length() == 3)
						counter = counter +2;
				}
				for (int j = 0; j < counter; j++) {
					System.out.print("¯");
				}
				//counter = 0;
				System.out.println("\n _ _ _ _ _ _ _ _ _ _ _ _ _");
				System.out.print("| ");
				int tempLength = operationsStack.size();
				for (int j = 0; j < tempLength; j++) {
					operationsStackTemp.push(operationsStack.pop());
				}
				tempLength = operationsStackTemp.size();
				for (int j = 0; j < tempLength; j++) {
					System.out.print(operationsStackTemp.peek() + " ");
					operationsStack.push(operationsStackTemp.pop());
				}
				System.out.println();
				System.out.println(" ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ ¯ \n");
			}
			printControl = false;
		}
		playerResultControl = (int)operationsStack.peek();
	}
	
}
