package org.greatlearning.dicegame;

import org.greatlearning.dicegame.model.Dice;
import org.greatlearning.dicegame.strategy.model.DiceGameStrategy;
import org.greatlearning.dicegame.strategy.GLGameStrategy;
import org.greatlearning.dicegame.model.Game;
import org.greatlearning.dicegame.utility.ConsoleInput;
import org.greatlearning.dicegame.utility.ConsoleOutput;
import org.greatlearning.dicegame.utility.model.InputInterface;
import org.greatlearning.dicegame.utility.model.OutputInterface;

public class Main {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			throw new Exception("Invalid Arguments!");
		}
		int players = Integer.parseInt(args[0]);
		int targetPoints = Integer.parseInt(args[1]);

		InputInterface inputInterface = new ConsoleInput();
		OutputInterface outputInterface = new ConsoleOutput();

		Dice dice = new Dice(6);

		DiceGameStrategy gameStrategy = new GLGameStrategy(players, targetPoints, inputInterface, outputInterface, dice);

		Game game = new Game(gameStrategy);
		game.startGame();
	}
}
