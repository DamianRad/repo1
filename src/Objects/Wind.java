
package Objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
    /**
    * Klasa odpowiedzialna za wyświetlanie przeszkody w postaci wiatru.
    * @autor Damian
    */
public class Wind {
    /**Zmienna dla obrazka wiatru*/
    private BufferedImage imageWind; 
    /**
    * Konstruktor w którym wczytywan jest obrazek wiatru
    */
    public Wind(){
        try{
            imageWind=ImageIO.read(getClass().getResourceAsStream("/img/wind.png"));
        }catch(Exception e){
            
        }
    }
    public void draw(Graphics2D g2d){
        //Uwaga        
        g2d.setColor(Color.RED);
        g2d.drawString("U W A G A", 20, 50);
        //Wyrysowywanie obrazka symbolizującego pojawienie się wiatru
        g2d.drawImage(imageWind, null, 0, 70);
    }
}//Koniec klasy Wind
