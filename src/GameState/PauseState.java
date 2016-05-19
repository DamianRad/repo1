
package GameState;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Pauza - klasa odpowiedzialna za wyswitlenie ekranu pauzy.
 * klasa dziedzicząca po klasie GameState
 * @author Damian
 */

public class PauseState extends GameState{
    /**Zmienna dla tła*/
    private BufferedImage imageBackground;
    /**Ustawienie czcionki tekstu wyświetlanego w stanie pauzy*/
    private Color textColor = new Color(255, 45, 73);
    /**
    * Konstruktor klasy Pauza odpowiedzialny wczytanie obrazka tła dla ekranu pauzy.
    * @param gsm ustawienie menadzera stanów gry
    */
    public PauseState(GameStateManager gsm){
      this.gsm=gsm;
      try{
          imageBackground=ImageIO.read(getClass().getResourceAsStream("/img/PauzaLine.gif"));
      }catch(Exception e){
          
      }
      
    }
    
    public void update() {
        
    }
  
    public void draw(Graphics2D g) {
        //Wyrysowanie ekranu pauzy
           
        g.drawImage(imageBackground, null, 0, 50);
        g.setColor(textColor);
        g.drawString("P A U Z A", 270, 80);
        g.drawString("Jeżeli chcesz kontynuować wcisnij 'Spacja'", 180, 100);
        g.drawString("Wyjście do menu wcisnij 'Esc'", 200, 120);
        g.drawString("(Jeżeli opuścisz grę twoje osiągnięcia na tym poziomie nie zostaną zapisane)", 0, 140);
        
    }//Koniec draw()
    public void keyPressed(int k) {
        //Przejśce do menu po wciśniećiu przycisku
        if(k==KeyEvent.VK_ESCAPE){
            
            gsm.currentState=0;
                       
        }
        //Kontynuacja gry
        if(k==KeyEvent.VK_SPACE){
            gsm.currentState=3;
            
        }
    }//Koniec keyPressed()
    
    public void keyReleased(int k) {}
    
}
