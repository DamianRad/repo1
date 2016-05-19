
package Objects;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Player - klasa odpowiadająca za ustawiajaca wszystkie parametry gracza, jego animację, oraz obsługę przycisków.
 * @author Damian
 */

public class Player {
    /**Startowa prędkośc gracza w poziomie*/
    private int velX=0;    
    /**Startowa prędkośc gracza w pionie*/
    private int velY=0;
    /**Startowa pozycja  gracza w poziomie*/
    public int  x = 300;  
    /**Startowa pozycja  gracza w pionie*/
    public int  y = 260;
    /**Dodatkowa zmienna pomocnicza do wykrywania czy strzłka w górę została wciśnięta*/
    public boolean isUpPressed=false;
    /**Dodatkowa zmienna pomocnicza do wykrywania czy strzłka w lewo została wciśnięta*/
    public boolean isLeftPressed=false;
    /**Dodatkowa zmienna pomocnicza do wykrywania czy strzłka w prawo została wciśnięta*/
    public boolean isRightPressed=false;
    /**Dodatkowa zmienna pomocnicza do wykrywania czy strzałka w lewo lub prawo została wciśnięta*/
    public boolean isLeftOrRightPressed=false;
    public BufferedImage image = new BufferedImage(10, 10, 10);
    public BufferedImage image2;
    
    public AffineTransformOp opLeft;
    public AffineTransformOp opRight;
    
    public Player(){
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/img/Blower.gif"));            
            image2 = ImageIO.read(getClass().getResourceAsStream("/img/BlowerWitoutFire.gif"));
        }catch(Exception e){
        }
        //Rotacja obrazka gracza w zależności od wciśniętego przycisku
        double rightRotationRequired = Math.toRadians (45);
        double leftRotationRequired = Math.toRadians (-45);
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform txLeft = AffineTransform.getRotateInstance(leftRotationRequired, locationX, locationY);
        AffineTransform txRight = AffineTransform.getRotateInstance(rightRotationRequired, locationX, locationY);
        opLeft = new AffineTransformOp(txLeft, AffineTransformOp.TYPE_BILINEAR);
        opRight = new AffineTransformOp(txRight, AffineTransformOp.TYPE_BILINEAR);
        
    }
    public void draw(Graphics2D g){
       if(isUpPressed){
           //////////Ciag dalszy animacji///////////////
           if(isLeftPressed){   
                g.drawImage(opLeft.filter(image, null), x, y, null);
           }else if(isRightPressed){
                g.drawImage(opRight.filter(image, null), x, y, null);
           }
           else
            g.drawImage(image, x, y,null);
       }else{
        g.drawImage(image2,x, y, null);
       }        
      
       
    }
    
    public void playerControl(int k){
               
        if(k == KeyEvent.VK_UP){
            isUpPressed = true; 
            velY = -1;
        }
        if(k == KeyEvent.VK_DOWN){   
           velY = 2;
        }
        if(k == KeyEvent.VK_LEFT){  
            isLeftPressed=true;
            velX = -2;
        }
        if(k == KeyEvent.VK_RIGHT){  
            isRightPressed=true;
            velX = 2;
        }
    }
    public void keyReleased(int k){
        
        if(k == KeyEvent.VK_UP){
            isUpPressed=false;
            velY = 1;     
        }
        if(k == KeyEvent.VK_DOWN){
           velY = 1;
        }
        if(k == KeyEvent.VK_LEFT){
            isLeftPressed=false;
            velX = 0;
        }
        if(k == KeyEvent.VK_RIGHT){
            isRightPressed=false;
            velX = 0;
        }
    }
    public void update(){
        x+=velX;       
        y+=velY;
        //Wykrywanie kolizji z krawędziami okna
        if(x<0){
            x=0;
        }
        if(x>600){
            x=600;
        }
  
    }   
    /**
    * Funkcja ustawiająca pozycję gracza
    * @param x pozycja X
    * @param y pozycja Y
    */
    public void setPlayerPosition(int x, int y){
        this.x=x;
        this.y=y;        
    }
    /**
    * Funkcja ustawiająca prędość gracza
    * @param velX prędkość w poziomie
    * @param velY prędkość w poionie
    */
    public void setPlayerVelocity(int velX,int velY){
        this.velX=velX;
        this.velY=velY;
    }
    /**
    * Funkcja ustawiająca pozycję końcową gracza po zakończeni poziomu
    * @param x prędkość w poziomie
    * @param y prędkość w poionie
    */
    public void setFinishPosition(int x , int y){
        this.x = x;
        this.y = y;
        this.velX=0;
        this.velY=0;
    }
}
