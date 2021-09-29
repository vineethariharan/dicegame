package org.greatlearning.dicegame.utility;

import org.greatlearning.dicegame.model.Leaderboard;
import org.greatlearning.dicegame.model.Player;
import org.greatlearning.dicegame.utility.model.OutputInterface;

import java.util.*;
import java.util.stream.Collectors;

public class ConsoleOutput implements OutputInterface {
	@Override
	public void welcome() {
		System.out.println("Welcome to The Game of Dice\n");
	}

	@Override
	public void leaderboard(Leaderboard leaderBoard) {
		String format = "| %6s | %10s | %9s | %5s";
		System.out.println("Current Standings:");
		System.out.printf((format) + "%n", "Rank", "Player", "Score", "");
		leaderBoard.getPlayerList().forEach(playerEntry -> {
			System.out.printf(format + "%n",
				playerEntry.getRank(),
				playerEntry.getPlayerName(),
				playerEntry.getPlayerScore(),
				playerEntry.getPlayerWinStatus());
		});
		System.out.println();
	}


	@Override
	public void playerWon(Player player) {
		System.out.println("Congrats! "+player.getPlayerName()+" has won.");
	}

	@Override
	public void wrongKeyPress() {
		System.out.println("Can't process that input. Try Again!");
	}

	@Override
	public void playerRoll(int diceRoll, int playerScore) {
		System.out.println("You rolled a "+diceRoll+". Your current score is "+playerScore+".");
	}

	@Override
	public void skipNextTurn() {
		System.out.println("You rolled two consecutive 1s. Skip next turn.");
	}

	@Override
	public void anotherTurn() {
		System.out.println("You rolled a 6. You get another turn.");
	}

	@Override
	public void end() {
		System.out.println("Thank you for playing!");
	}
}
