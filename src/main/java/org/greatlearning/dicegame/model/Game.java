package org.greatlearning.dicegame.model;

import org.greatlearning.dicegame.strategy.model.DiceGameStrategy;

public class Game {
	DiceGameStrategy diceGameStrategy;

	public Game(DiceGameStrategy diceGameStrategy){
		this.diceGameStrategy = diceGameStrategy;
	}

	public void startGame() {
		diceGameStrategy.start();
	}
}
