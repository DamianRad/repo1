package BlowerMain;

import javax.swing.JFrame;
/**
 * Klasa odpowiedzialna za utawienie parametrów okna głównego gry.
 * Klasa dzidziczy po klasie JFrame
 * @autor Damian
 */

public class Game extends JFrame{
        
       
	public Game(){
            setTitle("Bower");
            setContentPane(new GamePanel());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setResizable(false);                
            pack();
            setLocationRelativeTo(null);
           
        }
	
}//Koniec klasy Game