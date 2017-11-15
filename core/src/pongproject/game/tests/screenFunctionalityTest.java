package pongproject.game.tests;

import pongproject.game.Pong;

public class screenFunctionalityTest {

	
	
	private Pong game;
	
	
	
	public screenFunctionalityTest(Pong gameTest) {
			game = gameTest;
		
	}
	
	

	public void testScreens() {

		if(game.getMenuScreen().isSelected()) {
			
			System.out.println("The menu screen is selected and the show method has successfully executed");

		}else if(game.getGameScreen().isSelected()) {
			
			System.out.println("The game screen is selected and the show method has successfully executed");
 
		}else if(game.getHighScoreScreen().isSelected()) {
				
			System.out.println("The high score screen is selected and the show method has successfully executed");
					

		}else {
			
			System.out.println("ERROR: No screen selected, this should not be possible");
		}
	}

	
	
}
