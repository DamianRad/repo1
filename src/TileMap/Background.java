package TileMap;

import blower.GamePanel;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
    /**
    * Klasa odpowiedzialna za wyświetlanie i przesuwanie tła.
    * @autor Damian
    */
public class Background {
	/**Zmienna dla tła obrazka*/
	private BufferedImage image;
	/**Zmienna określająca pozycję obrazka w poziomie*/
	private double x=0;
        /**Zmienna określająca pozycję obrazka w pionie*/
	private double y;
        /**Zmienna określająca skok pozycji obrazka w pionie*/
	private double dy;
	/**Zmienna określająca szybkośc przesuwania się obrazka*/
	private double moveScale;
	        
	public Background(String s, double ms) {
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(s));
			moveScale = ms;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	 /**
        * Funkcja odpowiedzialna za ustawianie pozycji obrazka
        * @param x pozycja obrazka tła w poziomie
        * @param y pozycja opracka tła w pionie
        */
	public void setPosition(double x, double y) {
		this.y = (y * moveScale) % GamePanel.HEIGHT;
	}
	 /**
        * Funkcja odpowiedizalna za ustawienie skoku przesuwania tła
        * @param dx pozycja obrazka tła w poziomie
        * @param dy pozycja opracka tła w pionie
        */
	public void setVector(double dx, double dy) {
		this.dy = dy;
	}
	/**
        * Funkcja odpowiedzialna za uaktualnianie pozycji tła.
        */
	public void update() {
		y += dy;
	}
        /**
        * Funkcja odpowiedzialna za rysowanie tła.
        * @param  g obiekt graficzny 
        */
	public void draw(Graphics2D g) {
		
		setPosition(x,y); 
              
		g.drawImage(image, (int)x, (int)y, null);
		if(y < 0) {
			g.drawImage(image,(int)x,(int)y + GamePanel.HEIGHT,null);
		}
                
		if(y > 0) {
			g.drawImage(image,(int)x ,(int)y - GamePanel.HEIGHT,null);
		}
                 
               
	}//Koniec draw()
        /**
        * Funkcja przeładowanie obrazka tla ekranu.
        * @param  path scieżka do nowego obrazka 
        */
        public void reloadBackGroundImage(String path){
            try {
                image = ImageIO.read(getClass().getResourceAsStream(path));			
            }
            catch(Exception e) {
		e.printStackTrace();
            }
        }//Koniec reloadBackGroundImage()
	
}//Koniec klasy BackGround

