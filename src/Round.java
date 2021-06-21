
public class Round {
	private int player1Score;
	private int computerScore;
	private int roundCounter;
	
	public Round(int roundCounter) {
		this.roundCounter = roundCounter;
		this.player1Score = 0;
		this.computerScore = 0;
	}
	public int getPlayer1Score() {
		return player1Score;
	}
	public void setPlayer1Score(int player1Score) {
		this.player1Score = player1Score;
	}
	public int getComputerScore() {
		return computerScore;
	}
	public void setComputerScore(int computerScore) {
		this.computerScore = computerScore;
	}
	public int getRoundCounter() {
		return ++roundCounter;
	}
	public void setRoundCounter(int roundCounter) {
		this.roundCounter = roundCounter;
	}
	public void scoreIncrement(int playerSolution, boolean whichPlayer){
		int difference = Operations.targetNumber - playerSolution;
		if(whichPlayer){
			if(difference < 10)
				player1Score += (15 - difference);
			else
				player1Score += 5;
		}
		else{
			if(difference < 10)
				computerScore += (15 - difference);
			else
				computerScore += 5;
		}
	}
}
