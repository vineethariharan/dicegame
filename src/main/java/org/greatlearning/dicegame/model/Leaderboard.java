package org.greatlearning.dicegame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Leaderboard {
	private final List<Entry>	playerList;
	public Leaderboard(){
		this.playerList = new ArrayList<>();
	}
	public void addPlayer(Entry entry){
		this.playerList.add(entry);
	}

	@Data
	@AllArgsConstructor
	public static class Entry {
		private final int rank;
		private final String playerName;
		private final int playerScore;
		String playerWinStatus;
	}
}

