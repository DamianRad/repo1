package GameState;

import java.util.ArrayList;
import java.awt.Graphics2D;
/**
 * Klasa odpowiadająca za zarządzanie stanami w grze
 * @author Damian
 */
public class GameStateManager {
	/**Lista przechowująca stany*/
	public ArrayList<GameState> gameStates;
        /**Zmienna przechowująca aktualne stan*/
	public int currentState;
	/**Zmienna przechowująca domyślny stan po włączeniu gry*/
	public static final int MENUSTATE = 0;
	
	/**
        * Konstruktor w którym inicjalizowana jest lista ze stanami
        */
	public GameStateManager() {
		//currentState = 0 - Menu
                //currentState = 1 - Ranking 
                //currentState = 2 - Instrukcja
                //currentState = 3 - Rozgrywka
                //currentState = 4 - Pauza
                //currentStaee = 5 - Sklep
		gameStates = new ArrayList<GameState>();		
		currentState = MENUSTATE;                                   //Ustawienie pierwszego stanu po włączeniu gry                                                       
		gameStates.add(new MenuState(this));
                gameStates.add(new RankingState(this));
                gameStates.add(new Instruction(this));
                gameStates.add(new FirstLevel(this));
                gameStates.add(new PauseState(this));
                gameStates.add(new ShopState(this));
		
	}
	
	
	/**
        * Funkcja aktualizująca w stanie
        */
	public void update() {
		gameStates.get(currentState).update();
	}
	/**
        * Funkcja rysująca dla stanu
        * @param g parametr graficzny
        */
	public void draw(Graphics2D g) {
		gameStates.get(currentState).draw(g);
	}
	/**
        * Funkcja obsługująca wciśnięcia przycisku dla stanu
        * @param k aktualnie wciśnieęty przycisk
        */
	public void keyPressed(int k) {
		gameStates.get(currentState).keyPressed(k);
	}
	/**
        * Funkcja obsługująca zwolnienie przycisku przycisku dla stanu
        * @param k aktualnie zwolniony przycisk
        */
	public void keyReleased(int k) {
		gameStates.get(currentState).keyReleased(k);
	}

	
}//Koniec klasy GameStateManager