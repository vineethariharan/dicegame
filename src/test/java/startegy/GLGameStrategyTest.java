package startegy;

import org.greatlearning.dicegame.model.Dice;
import org.greatlearning.dicegame.model.Leaderboard;
import org.greatlearning.dicegame.strategy.GLGameStrategy;
import org.greatlearning.dicegame.utility.model.InputInterface;
import org.greatlearning.dicegame.utility.model.OutputInterface;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GLGameStrategyTest {
	InputInterface inputInterface = mock(InputInterface.class);
	OutputInterface outputInterface = mock(OutputInterface.class);

	Dice dice = mock(Dice.class);


	@Test
	public void testGameInstance(){
		GLGameStrategy glGameStrategy = new GLGameStrategy(2, 10, inputInterface, outputInterface, dice);
		when(inputInterface.readNextInput(anyString())).thenReturn("r");
		when(dice.roll()).thenReturn(5, 4, 5, 6);
		glGameStrategy.start();
		verify(outputInterface).playerRoll(5, 5);
		Leaderboard expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 5, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 0, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
		verify(outputInterface).playerRoll(4, 4);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 5, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 4, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 10, "(WON)"));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 4, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 10, "(WON)"));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 10, "(WON)"));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
	}

	@Test
	public void testRepeatTurnOnRollingSix(){
		GLGameStrategy glGameStrategy = new GLGameStrategy(2, 10, inputInterface, outputInterface, dice);
		when(inputInterface.readNextInput(anyString())).thenReturn("r");
		when(dice.roll()).thenReturn(6, 4, 5);
		glGameStrategy.start();
		verify(outputInterface).playerRoll(6, 6);
		Leaderboard expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 6, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 0, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
		verify(outputInterface).anotherTurn();
		verify(outputInterface).playerRoll(4, 10);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 10, "(WON)"));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 0, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
		verify(outputInterface).playerRoll(5, 5);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 10, "(WON)"));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 5, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
	}

	@Test
	public void testSkipTurnOnRollingConsecutiveOnes(){
		GLGameStrategy glGameStrategy = new GLGameStrategy(2, 10, inputInterface, outputInterface, dice);
		when(inputInterface.readNextInput(anyString())).thenReturn("r");
		when(dice.roll()).thenReturn(1, 5, 1, 2, 5);
		glGameStrategy.start();

		verify(outputInterface).playerRoll(1, 1);
		Leaderboard expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-1", 1, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-2", 0, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);

		verify(outputInterface).playerRoll(5, 5);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-2", 5, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-1", 1, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);

		verify(outputInterface).playerRoll(1, 2);
		verify(outputInterface).skipNextTurn();
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-2", 5, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-1", 2, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);

		verify(outputInterface).playerRoll(2, 7);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-2", 7, ""));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-1", 2, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);

		verify(outputInterface, times(2)).playerRoll(5, 12);
		expectedLeaderBoard = new Leaderboard();
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(1, "Player-2", 12, "(WON)"));
		expectedLeaderBoard.addPlayer(new Leaderboard.Entry(2, "Player-1", 2, ""));
		verify(outputInterface).leaderboard(expectedLeaderBoard);
	}
}
