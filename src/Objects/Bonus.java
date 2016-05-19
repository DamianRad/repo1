
package Objects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

/**
 * Bonus- klasa reprezentująca obiekt bonusu.
 * @author Damian
 */
public class Bonus {
    /**Zmienna dla bonusu*/
    private BufferedImage image; 
    /**Zmienna określająca położenie X bonusu*/
    private int posX = 300;
    /**Zmienna określająca położenie Y bonusu*/
    private int posY = 0;
    /**Zmienna określająca kiedy bonus ma zniknąć*/   
    private boolean disapear = false;
    /**Obiekt gracza, tworzony w celu pobrania współrzędnych*/   
    private Player player;
    /**Zmienna określająca ilość zdobywanyhc bonusów*/   
    public int amountOfBonus=0;
    /**Prostokąt o rozmiarze bonusu tworzony w celu wykrycia kolizji z graczem*/   
    private Rectangle bonusRect;
    /**Prostokąt o rozmiarze gracza tworzony w celu wykrycia kolizji z bonusem*/   
    private Rectangle playerRect;
    /**Zmienna określająca dół przedziału z którego jest losowana pozycja bonusu*/  
    private int minimum = 200;
    /**Zmienna określająca górę przedziału z którego jest losowana pozycja bonusu*/  
    private int maximum = 400;
    /**
     * Wczytywanie obrazka dla bonusu
     * @param gsm menadzer stanów
     */
    public Bonus(Player player){
        this.player=player;
        
        try{            
            image = ImageIO.read(getClass().getResourceAsStream("/img/Bonus.gif"));
            
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
     /**
     * Funkcja odpoweidzialna za rysowanie spadających bonusów
     * @param g menadzer stanów
     * @param fuelLevel enadzer stanów
     * @param firstPress enadzer stanów
     */
    public void drawBonus(Graphics g,int fuelLevel,boolean firstPress){
        //Prostokąt o wymiarach bonusu
        bonusRect = new Rectangle(posX,posY,10,20); 
        //Prostokąt o wymiarach gracza
        playerRect = new Rectangle(player.x,player.y,70,53); 
              
        if(posY<400){
            //Wyrysuj jeżeli gracz zaczął grę (wcisną strzałkę w górę),i jeżeli nie jest to koniec poziomu, 
            if(fuelLevel<100 && firstPress){
                g.drawImage(image, posX, posY, null);                           
            }
            else{
                posY=0;
            }
            //Wykrywanie kolizji 
            if(playerRect.contains(bonusRect)){                                 
                disapear = true;  
                //Zwiększenie ilości zdobytych punktów bonusowych
                amountOfBonus+=1;
                posY=0; 
                //Losowa pozycja bonusu
                posX=minimum + (int)(Math.random()*((maximum-minimum)+1));
            }
        }
        //Jeżeli bonus przekroczy dolna krawędź ekranu
        else{
            disapear=false;
            posY=0;
            posX=minimum + (int)(Math.random()*((maximum-minimum)+1));            
        }
    }//Koniec drawBonus()
    public void update(){
        //Aktualizacja pozycji spadających bonusów
         posY+=1; 
         
    }
        
    public void setBonusPosition(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
}//Koniec klasy Bonus
