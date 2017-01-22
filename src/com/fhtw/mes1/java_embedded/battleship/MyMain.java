package com.fhtw.mes1.java_embedded.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddShipException;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.BattleShipGameException;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.CoordinateException;
import com.fhtw.mes1.java_embedded.battleship.Exceptions.AddBattleFieldException;
import com.fhtw.mes1.java_embedded.battleship.lib.BattleShipInstantiationFailed;
import com.fhtw.mes1.java_embedded.battleship.lib.ICoordinate;

public class MyMain {

	private enum GameStates {
		SETUP, RUNNING, WON
	}

	public static void main(String[] args) throws BattleShipGameException, BattleShipInstantiationFailed,
			CoordinateException, AddShipException, AddBattleFieldException {

		GameStates gameState = GameStates.SETUP;
		Game game = new Game();
		Player player1 = null;
		Player player2 = null;
		Player player = null;
		try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));) {
			System.out.println(getStartMessage() +"\nPlease adapt the ship map files 'Player1.txt' and 'Player2.txt' before you start the game.");
			while (true) {
				switch (gameState) {
				case SETUP:
					System.out.println("\nPlayer 1 Setup:");
					System.out.println("What's the name of Player 1?\nName: >");
					String namePlayer1 = inputReader.readLine();
					player1 = new ConsolePlayer(namePlayer1,
							new BattleField(game.getFieldSizeX(), game.getFieldSizeY()));
					player1.loadShipMap("Player1.txt");
					game.setPlayer1(player1);
					System.out.println(namePlayer1 + " is ready!\n");
					System.out.println("Player 2 Setup:");
					System.out.println("What's the name of Player 2?\nName: >");
					String namePlayer2 = inputReader.readLine();
					System.out.println("Select Player Type: weather 'C' for Console or 'R' for Remote");

					String type = "";
					while (true) {
						type = inputReader.readLine();
						if ("C".equals(type)) {
							player2 = new ConsolePlayer(namePlayer2,
									new BattleField(game.getFieldSizeX(), game.getFieldSizeY()));
							break;
						} else if ("R".equals(type)) {
							player2 = new SocketPlayer(namePlayer2,
									new BattleField(game.getFieldSizeX(), game.getFieldSizeY()));
							System.out.println("...waiting for " + player2.getName() + " to connect...");
							break;
						} else {
							System.out.println(
									"Wrong argument -> Choose\n    C ... to play in the same Console as Player 1\n    R ... to play in a Remote Console");
						}
					}
					player2.loadShipMap("Player2.txt");
					game.setPlayer2(player2);

					if (player2 instanceof SocketPlayer) {

						SocketPlayer.createSocket();
						SocketPlayer.sendToSocketPlayer(getStartMessage());
					}

					System.out.println(namePlayer2 + " is ready!\n");
					game.start();
					gameState = GameStates.RUNNING;
					System.out.println("BATTLESHIP GAME STARTED\n");
					if (player2 instanceof SocketPlayer)
						SocketPlayer.sendToSocketPlayer(
								String.format("BATTLESHIP GAME STARTED\n"));
					player = player1;
					break;
				case RUNNING:
					while (!(game.gameFinished)) {
						if (player == player1) {
							System.out.println(player.getName() + " - it's your turn");
							System.out.println(player2.battlefieldMapToString());
						} else {
							if (player instanceof SocketPlayer) {
								SocketPlayer.sendToSocketPlayer(String.format(player.getName() + " - it's your turn"));
								SocketPlayer.sendToSocketPlayer(String.format((player1.battlefieldMapToString())));
							} else {
								System.out.println(player.getName() + " - it's your turn");
								System.out.println(player1.battlefieldMapToString());
							}
						}
						while (true) {
							if (player instanceof SocketPlayer)
								SocketPlayer.sendToSocketPlayer(
										String.format("What do you think, " + player.getName() + " > "));
							else
								System.out.println("What do you think, " + player.getName() + " > ");
							String guessString = null;
							if (player instanceof ConsolePlayer)
								guessString = inputReader.readLine();
							else
								guessString = SocketPlayer.readFromSocketPlayer();
							try {
								ICoordinate guess = new Coordinate(guessString);
								if (game.makeMove(player, guess)) {
									System.out.println("WOW... your bomb hit a ship!\n\n");
									if (player instanceof SocketPlayer)
										SocketPlayer
												.sendToSocketPlayer(String.format("WOW... your bomb hit a ship!\n\n"));
								} else {
									System.out.println("NOPE... this bomb splashed directly into the water...\n\n");
									if (player instanceof SocketPlayer)
										SocketPlayer.sendToSocketPlayer(String
												.format("NOPE... this bomb splashed directly into the water...\n\n"));
								}
								break;
							} catch (CoordinateException e) {
								if (player instanceof SocketPlayer) {
									SocketPlayer.sendToSocketPlayer(e.getMessage());
								} else {
								System.out.println(e.getMessage());}
							} catch (NullPointerException e) {
								System.out
										.println("Game exit forced with control signal or Remote client closed window");
								return;
							}
						}
						boolean allShipsDestroyed = false;
						if (player == player1) {
							allShipsDestroyed = player2.getBattleField().allShipsDestroyed();
						} else {
							allShipsDestroyed = player1.getBattleField().allShipsDestroyed();
						}
						if (allShipsDestroyed) {
							gameState = GameStates.WON;
							break;
						}
						//swap player
						if (player == player1) {
							player = player2;
						} else {
							player = player1;
						}
					}
					break;
				case WON:
					player.setHasWon();
					game.setGameFinished();
					System.out.println("Congratulations, " + player.getName() + "! You bombed all ships!");
					System.out.println(String.format("%s won the game!", game.getWinnerName()));
					if (player2 instanceof SocketPlayer) {
						SocketPlayer.sendToSocketPlayer(
								String.format("Congratulations, " + player.getName() + "! You bombed all ships!"));
						SocketPlayer.sendToSocketPlayer(String.format("%s won the game!", game.getWinnerName()));
						SocketPlayer.sendToSocketPlayer(
								String.format("\n\n ...stroke return to quit the game...\n\n"));
						SocketPlayer.readFromSocketPlayer();
						SocketPlayer.closeSocket();
					}
					return;
				default:
					System.out.println("An unexpected error has occured! Game closed...");
					break;
				}
			}
		} catch (BattleShipGameException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		} catch (AddBattleFieldException e) {
			System.err.println(e.getMessage());
			if(player2 instanceof SocketPlayer){
				SocketPlayer.closeSocket();
			}
			return;
		}
		//close socket
		finally{	
			if(player2 instanceof SocketPlayer){
				SocketPlayer.closeSocket();
			}
		}
	}

/*	private static void printMsgToPlayer(Player player, String msg) {
		if (player == null) {
			System.out.println(msg);
			SocketPlayer.sendToSocketPlayer(msg);
		} else if (player instanceof SocketPlayer) {
			SocketPlayer.sendToSocketPlayer(msg);
		}else {
			System.out.println(msg);
		}

	}
*/
	
	/**
	 * return the start message including ascii art as string
	 * @return start message as string
	 */
	private static String getStartMessage() {
		StringBuilder msg = new StringBuilder();
		msg.append("                    ()\n");
		msg.append("                     ()\n");
		msg.append("                # #  ( )\n");
		msg.append("             ___#_#___|__\n");
		msg.append("          __|____________|  _\n");
		msg.append("   _=====| | |            | | |==== _\n");
		msg.append("   =====| |.---------------------------. | |====\n");
		msg.append("   <--------------------'   .  .  .  .  .  .  .  .   '--------------/\n");
		msg.append("   \\______________________________________________________________/\n");
		msg.append("   \\                                                             /\n");
		msg.append("   \\        BATTLESHIP - by Stefan Stockinger                  /\n");
		msg.append("   \\_________________________________________________________/\n");
		msg.append("   \\_______________________________________________________/\n");
		msg.append("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\n");
		msg.append("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww\n");
		msg.append("\n\nWelcome to the BattleShip Game\n");
	 return msg.toString();
	}
}
