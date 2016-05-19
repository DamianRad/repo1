package GameState;

import TileMap.Background;

import java.awt.*;
import java.awt.event.KeyEvent;


/**
 * Menu - klasa odpowiedzialna za zarządzenie menu głównym gry
 * klasa dziedzicząca po klasie GameState
 * @author Damian
 */
public class MenuState extends GameState {
	/**Obiekt dla tła menu*/
	private Background bg;       
	/**Ustawienie koloru tekstu w menu*/
        private Color textColor = new Color(255, 45, 73);
        /**Aktualnie wybrana pozycja w menu*/
	private int currentChoice = 0;
        /**Tablica przechowująca opcje wyświtlane w menu*/
	private String[] options = {
		"Start",
                "Rank",
		"Sklep",
		"Wyjdź"
	};
	/**Zmienna dla czcionki*/
	private Font font;
	/**
        * Konstruktor ustawiajacy tło i czcionkę.
        * @param gsm szerokość okna
        */
	public MenuState(GameStateManager gsm) {		
		this.gsm = gsm;
		bg = new Background("/img/Menuv3.gif", 1);
		font = new Font("Arial", Font.BOLD, 15);		
	}
		
        @Override
	public void update() {
                
	}
	
        @Override
	public void draw(Graphics2D g) {
                
		bg.draw(g);					
		g.setFont(font);
                //Rysowanie opcji w menu
		for(int i = 0; i < options.length; i++) {
                        //Jeżeli pozycja została wybrana zmień kolor pozycji na czarny
			if(i == currentChoice) {
				g.setColor(Color.BLACK);
			}
                        //Jeżeli pozycja nir została wybrana zmień kolor pozycji na czerwony
			else {
				g.setColor(textColor);
			}
			g.drawString(options[i], 300, 180 + i * 30);
		}
                
		
	}//Koniec draw()
	
	private void select() {
		if(currentChoice == 0) {
                        gsm.currentState = 2;  
                        //W przypdaku wybrania opcji start nadpisywany jest trzeci element listy
                        //Po to aby zasze po wybraniu tej opcji rozgrywka ropoczynała się od początku
                        gsm.gameStates.set(3, new FirstLevel(gsm));
		}
		if(currentChoice == 1) {
			gsm.currentState = 1;
		}
                if(currentChoice == 2) {
			gsm.currentState = 5;
		}
		if(currentChoice == 3) {
			System.exit(0);
		}
	}//Koniec select()
	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER){
			select();
		}
		if(k == KeyEvent.VK_UP) {
                        //Wciśnięcie strzełki w górę powoduje zaznaczanie wyższej opcji
			currentChoice--;
			if(currentChoice == -1) {
				currentChoice = options.length - 1;
			}
		}
		if(k == KeyEvent.VK_DOWN) {
                        //Wciśnięcie strzełki w górę powoduje zaznaczanie wyższej opcji
			currentChoice++;
			if(currentChoice == options.length) {
				currentChoice = 0;
			}
		}
	}//Koniec keyPressed()
	public void keyReleased(int k) {}
        	
}