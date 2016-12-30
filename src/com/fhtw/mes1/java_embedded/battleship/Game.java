package com.fhtw.mes1.java_embedded.battleship;

import com.fhtw.mes1.java_embedded.battleship.Player;

public class Game {

	Player Player1;
	Player Player2;
	
	Game() {
		Player1 = null;
		Player2 = null;
	}
	
	public void setPlayer1(Player player){
		Player1 = player;
	}
	
	public void setPlayer2(Player player){
		Player2 = player;
	}
	public void start(){
		if((Player1 != null)  && (Player2 != null))
			;
			//startGame();
	}
	
	
	/*Game game=new Game());
	game.setPlayer1();
	game.setPlayer2(new SocketPlayer(8111,…),…);
	game.start();
*/
}


