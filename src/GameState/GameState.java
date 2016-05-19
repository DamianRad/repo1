package GameState;
import java.awt.Graphics2D;
/**
 * Klasa będąca szablonem dla wszystkich klas które są stanami w grze np.MenuState,Instruction,RankingState.
 * Klasy te dziedziczą z tej klasy i muszą zawierać , zawarte w niej funkcje.
 * @author Damian
 */
public abstract class GameState {
	/**Menadżer stanów.*/
	protected GameStateManager gsm;
        /**
        * Funkcja uaktualniająca stan gry.
        */
	public abstract void update();
        /**
        * Funkcja rysująca w stanie gry.
        * @param g parametr graficzny
        */
	public abstract void draw(Graphics2D g);
        /**
        * Funkcja obsługująca wciśnięcia przycisków w aktualnym stanie gry.
        * @param k aktualnie wciśnięty przycisk
        */
	public abstract void keyPressed(int k);
        /**
        * Funkcja obsługująca zwolnieina przycisków w aktualnym stanie gry.
        * @param k aktualnie zwolniony
        */
	public abstract void keyReleased(int k);
	
}
