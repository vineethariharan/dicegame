package org.greatlearning.dicegame.strategy;

import org.greatlearning.dicegame.model.Dice;
import org.greatlearning.dicegame.model.Leaderboard;
import org.greatlearning.dicegame.model.Player;
import org.greatlearning.dicegame.strategy.model.DiceGameStrategy;
import org.greatlearning.dicegame.utility.model.InputInterface;
import org.greatlearning.dicegame.utility.model.OutputInterface;

import java.util.*;
import java.util.stream.Collectors;

public class GLGameStrategy implements DiceGameStrategy {
	InputInterface input;
	OutputInterface output;

	private final int players;
	private final int targetPoints;
	private final Set<Player> playingOrder;
	private final Map<Player, Integer> playerLeaderBoard;
	private final Map<Player, Integer> playerPreviousRoll;
	private final Set<Player> playerTurnSkipList;
	private final Map<Player, Integer> playerScores;
	private final Dice dice;

	public GLGameStrategy(int players, int targetPoints, InputInterface inputInterface, OutputInterface outputInterface, Dice dice) {
		this.input = inputInterface;
		this.output = outputInterface;
		this.players = players;
		this.targetPoints = targetPoints;
		this.playingOrder = new HashSet<>();
		this.playerScores = new HashMap<>();
		this.playerLeaderBoard = new HashMap<>();
		this.playerTurnSkipList = new HashSet<>();
		this.playerPreviousRoll = new HashMap<>();
		this.dice = dice;
	}

	@Override
	public void start() {
		output.welcome();
		createPlayerOrder();
		resetScores();

		while (playerLeaderBoard.size() < players) {
			playingOrder.forEach(player -> {
				if (hasPlayerWon(player)) return;
				if (isPlayerTurnSkipped(player)) return;
				if (getUserInput(player)) return;
				playTurn(player);
			});
		}

		endGame();
	}

	private void resetScores() {
		playingOrder.forEach(player -> {
			playerScores.put(player, 0);
		});
	}

	private boolean getUserInput(Player player) {
		String userInput = input.readNextInput("Next Turn: "+ player.getPlayerName()+". " +
			"(press 'r' to roll the dice, 's' to skip, and 'q' to quit)");
		switch(userInput){
			case "s":
				showLeaderBoard();
				return true;
			case "q":
				endGame();
				System.exit(0);
				break;
			case "r":
				return false;
			default:
				output.wrongKeyPress();
				return getUserInput(player);
		}
		return true;
	}

	private void endGame() {
		output.end();
	}

	private void playTurn(Player player) {
		int diceRoll = dice.roll();

		if (updateScores(player, diceRoll)) return;

		while (diceRoll == 6) {
			output.anotherTurn();
			diceRoll = dice.roll();
			if (updateScores(player, diceRoll)) return;
		}

	}

	private boolean updateScores(Player player, int diceRoll) {
		playerScores.put(player, playerScores.get(player) + diceRoll);
		output.playerRoll(diceRoll, playerScores.get(player));

		if (hasPlayerWon(player)) return true;

		showLeaderBoard();

		if (diceRoll == 1 && playerPreviousRoll.getOrDefault(player, 0) == 1) {
			output.skipNextTurn();
			playerTurnSkipList.add(player);
			return true;
		}

		playerPreviousRoll.put(player, diceRoll);
		return false;
	}


	private boolean isPlayerTurnSkipped(Player player) {
		if (playerTurnSkipList.contains(player)) {
			playerTurnSkipList.remove(player);
			return true;
		}
		return false;
	}

	private boolean hasPlayerWon(Player player) {
		if (playerLeaderBoard.get(player) != null) return true;

		if (playerScores.get(player) >= targetPoints) {
			output.playerWon(player);
			putPlayerOnLeaderboard(player);
			showLeaderBoard();
			return true;
		}

		return false;
	}

	private void showLeaderBoard() {
		output.leaderboard(createLeaderBoard());
	}

	private Leaderboard createLeaderBoard() {
		Leaderboard leaderboard = new Leaderboard();
		playerLeaderBoard.entrySet().stream()
			.sorted((e1, e2)->e1.getValue().compareTo(e2.getValue()))
			.forEach(entry->{
				Player player = entry.getKey();
				int rank =  entry.getValue();
				leaderboard.addPlayer(new Leaderboard.Entry(rank, player.getPlayerName(), playerScores.get(player), "(WON)"));
			});
		int rank = playerLeaderBoard.size();
		List<Player> playerRanking = playerScores.entrySet().stream()
			.sorted((e1, e2)->e2.getValue().compareTo(e1.getValue()))
			.map(Map.Entry::getKey)
			.collect(Collectors.toList());
		for (Player player : playerRanking) {
			if (playerLeaderBoard.get(player) == null) {
					leaderboard.addPlayer(new Leaderboard.Entry(++rank, player.getPlayerName(), playerScores.get(player), ""));
			}
		}
		return leaderboard;
	}

	private void putPlayerOnLeaderboard(Player player) {
		int rank = playerLeaderBoard.size() + 1;
		playerLeaderBoard.put(player, rank);
	}


	private void createPlayerOrder() {
		Random randNum = new Random(System.currentTimeMillis());

		while (playingOrder.size() < players) {
			int playerNumber = randNum.nextInt(players) + 1;
			String playerName = "Player-"+playerNumber;
			playingOrder.add(new Player(playerNumber, playerName));
		}
	}


}
