
package blower;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;

import javax.swing.JPanel;

import GameState.GameStateManager;
/**
  * Klasa reprezentująca główny panel gry. 
  * Klasa dziedzicy z klasy JPanel
  * @autor Damian
  */
public class GamePanel extends JPanel implements Runnable, KeyListener{
	
	/**Szerokość*/
	public static final int WIDTH = 640;
        /**Wysokość */
	public static final int HEIGHT = 512;
        /**Wkalowanie rozmiarów panelu ekranu*/
	public static final int SCALE=2;
	
	/**Wątek główny gry*/
	private Thread thread;
        /**Zmienna pomocnicza inicjująca pętlę gry*/
	private boolean running;
        /**Ilość klatek*/
	private int FPS = 60;
        /**Opóźnienie*/
	private long targetTime = 1000 / FPS;
	
	/**Zmienna do wczytania obrazu*/
	private BufferedImage image;
        /**Zmienna graficzna*/
	private Graphics2D g;
	
	/**menadzer stanów gry */
	private GameStateManager gsm;
	/**
        * Ustawienie rozmiarow panelu , i inicjacja wątka 
        */
	public  GamePanel() {
		super();
		setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		setFocusable(true);
		requestFocus();
                start();
	}
	/**
        * Funkcja odpowiedzialna za inicjlizację wątka, i dodanie słuchacza przycisków.
        */
	public void start() {
		
		if(thread == null) {
                    thread = new Thread(this);
                    addKeyListener(this);
                    thread.start();
		}
	}
	/**
        * Funkcja odpowiedzialna za inicację obiektów 
        */
	private void init() {
		
		image = new BufferedImage(WIDTH, HEIGHT,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics(); 
		running = true;
		gsm = new GameStateManager(); 
                	
	}
	
	public void run() {
                //inizjalizacjia wątka
		init();		
		long start;
		long elapsed;
		long wait;		
		//Główna pętla gry
		while(running) {			
                    start = System.nanoTime();	
                    //Uakualnianie ekranu
                    update();
                    draw();
                    drawToScreen();
                    //Ustalanie opóżnienia dla wątka
                    elapsed = System.nanoTime() - start;			
                    wait = targetTime - elapsed / 1000000;                        
                    if(wait < 0) 
                      wait = 5;			
                    try {
                        Thread.sleep(wait);
                    }
                    catch(Exception e) {
			e.printStackTrace();
                    }			
		}		
	}
	/**
        * Funkcja odpowiedzialna za wywołanie metody aktualizującej stan gry określony przez GameStateManager.
        */
	private void update() {
		gsm.update();
	}
        /**
        * Funkcja odpowiedzialna za wywołanie metody wyrysowującej w stanie gry określonym przez GameStateManager.
        */
	private void draw() {
		gsm.draw(g);
	}
        /**
        * Funkcja odpowiedzialna za rysowanie na ekranie.
        */
	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0,WIDTH * SCALE, HEIGHT * SCALE,null);
		g2.dispose();
	}
	
	public void keyTyped(KeyEvent key) {}
        //Wywołanie metody odpowiedzialnej za nasłuchiwanie wciśnięcia przycisku w stanie określonym przez GameStateManager
	public void keyPressed(KeyEvent key) {
		gsm.keyPressed(key.getKeyCode());
	}
        //Wywołanie metody odpowiedzialnej za nasłuchiwanie zwolnienia przycisku w stanie określonym przez GameStateManager
	public void keyReleased(KeyEvent key) {
		gsm.keyReleased(key.getKeyCode());
	}

}
