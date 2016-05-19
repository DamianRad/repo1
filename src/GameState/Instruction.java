
package GameState;

import Background.Background;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Instrukcja - klasa odpowiedzialna za wyświetlanie instrukcji
 * klasa dziedzicząca po klasie GameState
 * @author Damian
 */
public class Instruction extends GameState{
    /**Zmienna dla tła*/
    private Background bg;
    /**Ustawienie koloru tekstu w instrukcji*/
    private Color textColor = new Color(255, 45, 73);
    /**Zmienna dla obrazka przycisku esc*/
    private BufferedImage espImage;
    /**Zmienna dla obrazka przycisku lewej strzałki*/
    private BufferedImage leftImage;
    /**Zmienna dla obrazka przycisku prawej strzałki*/
    private BufferedImage rightImage;
    /**Zmienna dla obrazka przycisku strzałki w górę*/
    private BufferedImage upImage;
    /**Zmienna dla obrazka z power*/
    private BufferedImage powerBarIcon;
    /**Zmienna dla obrazka z ikoną wiatru*/
    private BufferedImage windIcon;
    /**Zmienna dla obrazka z ikoną bonusu*/
    private BufferedImage bonusIcon;
    /**Zmienna czionki w instrukcji*/
    private Font font;
    
    /**
     * Konstruktor klasy - pobranie obrazków dla intrukcji, jak i samo jej wyrysowanie.
     * @param gsm obiekt menadzera gry , potrzebny w celu przejścia do kolejnego stanu
     */
    public Instruction(GameStateManager gsm){
        this.gsm = gsm;
        try {
                //Pobieranie obrazków
		bg = new Background("/img/Instruction.gif", 1);	                
		espImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/escp.png"));
                leftImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/left.png"));
                rightImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/right.png"));
                upImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/up.png"));
                powerBarIcon = ImageIO.read(getClass().getResourceAsStream("/img/ImgInst.gif"));
                windIcon = ImageIO.read(getClass().getResourceAsStream("/img/wind.gif"));
                bonusIcon = ImageIO.read(getClass().getResourceAsStream("/img/Bonus.gif"));
                //Ustawieni czcionki
		font = new Font("Arial", Font.BOLD, 10);
                
	}
	catch(Exception e) {
		e.printStackTrace();
	}

    }

    

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g){
                
                bg.draw(g);
                //Ustawienie koloru czcionki
                g.setColor(textColor);
                //Rysowanie intrukcji
                g.drawString("Sterowanie", 50, 80);
                g.drawString("Zwiększ siłę ciągu silnika:", 50, 100);
                g.drawImage(upImage, null, 200, 90);
                g.drawString("Sterowanie w poziomie:", 50, 130);
                g.drawImage(leftImage, null, 180, 120);
                g.drawImage(rightImage, null, 220, 120);
                g.drawString("Przejście do ekranu pauzy :", 50, 160);
                g.drawImage(espImage, null, 200, 150);
                g.drawString("Zasady gry", 400, 80);
                g.drawString("Za utrzymywanie wskaźnika siły ciągu w wyznaczonym ", 250, 110);
                g.drawString("polu przyznawane są punkty(w środkowej części ekranu", 250, 120);
                g.drawString("wyświetlane są komunikaty ułatwiające tą czynność)", 250, 130);
                g.drawImage(powerBarIcon, null, 570, 70);
                g.drawString("W lewym górnym rogu wyświetla się symbol oznaczający ", 250, 180);
                g.drawString("pojawienie się  wiatru utrudniającego rozgrywkę:", 250, 190);
                g.drawImage(windIcon, null,550 , 160);
                g.drawString("W trakcie gry zbieraj spadające bonusy:", 250, 240);
                g.drawImage(bonusIcon, null,570 , 230);
                g.drawString("W momencie przekroczenia dolnej krawdędzi ekranu", 250, 280);
                g.drawString("poziom jest ładowoany od nowa", 250, 290);
                g.drawString("Wciśnij 'Enter' aby rozpocząć grę ", 230, 320);
		g.setFont(font);

    }//Koniec draw()
    @Override
    public void keyPressed(int k) {
          if(k == KeyEvent.VK_ENTER){
              gsm.currentState = 3;
          }
          if(k == KeyEvent.VK_ESCAPE){
              gsm.currentState = 0;
          }
    }//Koniec keyPressed

    @Override
    public void keyReleased(int k) {}
}//Koniec klasy Instruction()
