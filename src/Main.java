import enigma.core.Enigma;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Enigma.getConsole("Game", 80, 30, 12, 0);
		
		Operations work = new Operations();
		work.game_loop();
		
		//work.Operation("1+6*9/4*(75-9)");
		//System.out.println(work.Operation("1+6*9/4*(75-9)"));
		//String a = work.Operation("(6+6*9)/4*100-9");
		//work.postfixEvaluation(a);
	}

	
}