package com.fhtw.mes1.java_embedded.battleship;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class extends the Player class by a SocketPlayer and offers related methods
 * @author stocki
 *
 */
public class SocketPlayer extends Player {
	final private static int DEFAUTL_PORT = 7777;
	private static int port;
	static ServerSocket serverSocket;
	static Socket clientSocket;
	static PrintWriter clientSocketOutput;
	static BufferedReader clientSocketInput;

	/**
	 * Constructor
	 * @param name
	 * @param battleField
	 */
	public SocketPlayer(String name, BattleField battleField) {
		super(name, battleField);
		SocketPlayer.port = DEFAUTL_PORT;
	}

	/**
	 * set the port number of the socket
	 * @param port
	 */
	public void setPort(int port) {
		SocketPlayer.port = port;
	}

	/**
	 * get the port number of the socket
	 * @return port
	 */
	public static int getPort() {
		return port;
	}

	/**
	 * opens the socket connection and waits for client to start the game 
	 * @throws IOException
	 */
	public static void createSocket() throws IOException {
		// http://docs.oracle.com/javase/tutorial/networking/sockets/clientServer.html
		serverSocket = new ServerSocket(getPort());
		clientSocket = serverSocket.accept();

		System.out.println("Successfully established connection to socket player 2.");

		clientSocketOutput = new PrintWriter(clientSocket.getOutputStream(), true);
		clientSocketInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

		sendToSocketPlayer("Welcome to the Battle Ship Game!");
		sendToSocketPlayer("Type 'start' if you are ready to start the game.");

		String inputLine;
		while ((inputLine = readFromSocketPlayer()) != null) {
			if ("start".equals(inputLine)) {
				break;
			} else {
				sendToSocketPlayer("Type 'start' if you are ready to start the game.");
			}
		}
	}
	
	/**
	 * close the socket connection
	 */
	public static void closeSocket(){
	    
	    if (clientSocket != null)
	    {
	      try {
	        clientSocketInput.close();
	        clientSocketOutput.close();
	        if (clientSocket != null)
	        	clientSocket.close();
	        if (serverSocket != null)
	        	serverSocket.close();
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
	    }
	  }

	/**
	 * sends message to the socket player
	 * @param msg
	 */
	public static void sendToSocketPlayer(String msg) {
		clientSocketOutput.println(msg);
	}

	/**
	 * reads in a message from the socket player
	 * @return clientSocketInput.readLine()  message from the socket player
	 * @throws IOException
	 */
	public static String readFromSocketPlayer() throws IOException {
		return clientSocketInput.readLine();
	}
}
