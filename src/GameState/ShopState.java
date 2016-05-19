
package GameState;

import Background.Background;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Klasa główna odpowiedzialna za sklep. 
 * Klasa dziedzicząca po klasie GameState.
 * @author Damian
 */
public class ShopState extends GameState{
    /**Obiekt tła*/
    private Background bg; 
    /**Zamienna dla obrazka przycisku esc*/
    private BufferedImage keyEsc;
    /**Zmienna ustawiająca czcionkę*/
    private Font font;
    public ShopState(GameStateManager gsm){
        this.gsm = gsm;
        font = new Font("Arial", Font.BOLD, 12);
        bg=new Background("/img/Sklep.gif/", 1);
        try{
            keyEsc = ImageIO.read(getClass().getResourceAsStream("/imgKey/escp.png"));
        }catch(Exception e){
            
        }
    }
    

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g) {
        //Rysowanie sklepu
        bg.draw(g);
        g.setFont(font);
        g.drawString("Sklep obecnie nie został jeszcze zrealizowany.", 200, 100);
        g.drawString("W przyszłości za zdobyte punkty będzie można okdblokować różne ulepszenia.", 100, 150);
        g.drawImage(keyEsc, null, 540, 320);
        g.drawString("- Wróć", 560, 330);
    }

    @Override
    public void keyPressed(int k) {
        //Przejcie do menu po wciśnięciu esc
        if(k==KeyEvent.VK_ESCAPE){
            gsm.currentState=0;
        }
    }

    @Override
    public void keyReleased(int k) {
        
    }
    
}
