package org.greatlearning.dicegame.utility.model;

import org.greatlearning.dicegame.model.Leaderboard;
import org.greatlearning.dicegame.model.Player;


public interface OutputInterface {
	void welcome();
	void end();
	void playerWon(Player player);
	void wrongKeyPress();
	void playerRoll(int diceRoll, int playerScore);
	void skipNextTurn();
	void anotherTurn();
	void leaderboard(Leaderboard leaderBoard);
}
