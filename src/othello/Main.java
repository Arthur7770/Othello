package othello;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import othello.players.AlphaBetaPlayer;
import othello.players.NegamaxPlayer;
import othello.players.Player;

public class Main {


	public static void main(String[] args) {
		Player[] players = extractArgs(args);
		Player p1 = players[0];
		Player p2 = players[1];
		Player[][] board = initializeBoard(p1, p2);
		State game = new State(board, p1, p2);
		System.out.println("joueur 1: " + p1);
		System.out.println("joueur 2: " + p2);
		while(!game.isOver()) {
			State.previousSituations.add(game.toString());
			Player player = game.getCurrentPlayer();
			System.out.println(game.toString());
			game = game.play(player.play(game));
		}
		endGame(game, p1, p2);

	}

	/**
	 * The method will interpret the arguments and return a {@link Player} instance depending on the arguments given
	 * <ul><li>argument 0 {@code int}: player 1 depth search</li>
	 * <li>argument 1 {@code int}: player 2 depth search</li>
	 * <li>argument 2 {@code boolean}: true if player should be {@link AlphaBetaPlayer} instance,
	 * {@link NegamaxPlayer} otherwise</li></ul>
	 * If arguments are missing or invalid, default value will be use {@code (4, 4, true)}
	 * @param args program arguments, given when user write the command to execute the program
	 * @return Player 1 and 2 instance
	 */
	public static Player[] extractArgs(String[] args) {
		Player p1;
		Player p2;
		int depthP1 = 4;
		int depthP2 = 4;
		boolean useAlphaBeta = true;
		try {
			if(args.length >= 3) { // les paramètres > 3 sont ignorés
				depthP1 = Integer.parseInt(args[0]);
				depthP2 = Integer.parseInt(args[1]);
				useAlphaBeta = Boolean.parseBoolean(args[2]);
			}
		} catch (NumberFormatException e) {
			System.out.println("Les arguments de lancement ne pas corrects (pas des nombres entiers)");
			System.out.println("Utilisation des paramètres par défaut ( 4 4 true )");
		} finally {
			if(useAlphaBeta) {
				p1 = new AlphaBetaPlayer(depthP1);
				p2 = new AlphaBetaPlayer(depthP2);
			} else {
				p1 = new NegamaxPlayer(depthP1);
				p2 = new NegamaxPlayer(depthP2);
			}
		}
		return new Player[]{p1, p2};
	}
	
	public static Player[][] initializeBoard(Player p1, Player p2){
		Player[][] board = new Player[7][7];
		board[0][0] = p2;
		board[0][6] = p1;
		board[6][0] = p1;
		board[6][6] = p2;
		return board;
	}

	public static void endGame(State game, Player p1, Player p2) {
		System.out.println(game.toString());
		System.out.println(game.getN1()+"   "+ game.getN2());
		System.out.println(game.getWinner() + " a gagné la partie");
		System.out.println("Score joueur 1 -> " + game.getScore(p1));
		System.out.println("Score joueur 2 -> "+ game.getScore(p2));
	}

}
