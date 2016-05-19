
package HUD;



import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
/**
 * Klasa reprezentująca część graficzną odpowiedzialna za wyrysowanie
 * elementów HUD ,np. stan ilość paliwa, punkty
 * @author Damian
 */
public class Hud {
    //Obiekty do przechowywania obrazów   
    private BufferedImage imagePowerFuel;
    private BufferedImage imageStartPlatform;
    private BufferedImage imagePoints;
    /**Pozycja wskaźnika siły X*/
    private int pointerX;
    /**Pozycja wskaźnika siły Y*/
    private int pointerY;
    /**Górna krawędź zielonego pola*/
    public  int upperEdgeOfGreenArea=260;
    /**Wyskokość zielonego pola*/
    public  int heightOfGreenArea=70;
    /**Dolna krawedź zielonego pola*/
    private int lowerEdgeOfGreenArea = upperEdgeOfGreenArea + heightOfGreenArea;
    /**Zmienna ustalająca czcionkę*/
    private Font font;
    private Font fontAlert;
    private Color colorAlert;
    /**liczba punktów*/
    private double points = 0;
    /**liczba punktów bonusowych*/
    private int bonusPoints = 0;
    /**Zmienna pomocnicza określająca kiedy zaprzestać liczeniu punktow*/
    private boolean stopPointsCounting = false;        
    /**Pole do przechowywania nazwy gracza*/
    private String playerName;
    
    
    public Hud(){
         font = new Font("Calibri",Font.BOLD ,15 );
         fontAlert = new Font("Verdana",Font.ITALIC,16);
         try{
           imagePowerFuel = ImageIO.read(getClass().getResourceAsStream("/img/PowerFuel.gif"));
           imageStartPlatform = ImageIO.read(getClass().getResourceAsStream("/img/StartPlatform.gif"));
           imagePoints = ImageIO.read(getClass().getResourceAsStream("/img/PointsEtc.gif"));
       }catch(Exception e){
           e.printStackTrace();
       }
    }
    /**
     * Rysowanie HUD
     * @param g parametr graficzny
     * @param fuelLevel poziom paliwa
     * @param strengthLevel poziom siły
     * @param bonusPoints liczba punktów bonusowych
     * @param currentLevel numer poziomu
     * @param userLogin login użytkownika
     */
    public void drawHud(Graphics2D g, int fuelLevel , double strengthLevel,int bonusPoints, int currentLevel,String userLogin){
       playerName=userLogin;
       this.bonusPoints = bonusPoints;
       
       g.setFont(fontAlert);
       //Rysowanie tła dla wskaźników
       g.drawImage(imagePowerFuel, null,0, 240);                                         // Wyrysowanie ramek 
       //Rysowanie tła dla pola z punktami i nazwą użytkownika
       g.drawImage(imagePoints, null,170, 310);
       //Rysowanie wskaźnika
       drawStrength(g,  strengthLevel);
       //Rysowanie benzyny
       drawFuel(g, fuelLevel);  
       //Wypisywanie punktów , punktów bonusowych , aktualnego poziomu , loginu gracza
       drawLevelInfo(g,playerName,bonusPoints,currentLevel);
       //Sprawdzenie , czy gracz skończył poziom czy całą grę
       if(fuelLevel == 100 && currentLevel==5){
           drawGameOverScreen(g);
       }       
       else if(fuelLevel == 100){
            drawFinishScreen(g,fuelLevel);
       }
                                                          //Rysowanie punktów
    }//Koniec drawHud()
    /**
     * Rysowanie HUD
     * @param g parametr graficzny
     * @param fuelLevel poziom paliwa
     */
    public void drawFuel(Graphics2D g, int fuelLevel){
       //Wskaźnik ilości paliwa       
       g.setColor(Color.blue);      
       g.fillRect(60, 250, 30, 100);
       g.setColor(Color.white);
       //if(player.y < GamePanel.HEIGHT/2){       
       g.fillRect(60,250, 30, fuelLevel);             
    }//Koniec drawFuel()
    /**
     * Rysowanie HUD
     * @param g parametr graficzny
     * @param strengthLevel poziom siły
     */
    public void drawStrength(Graphics2D g, double strengthLevel){
       //Rysowanie wskażnika paliwa
       pointerX = 10;
       //Zmiana położenia wskaźnika w zależności od zmiany wartości zmiennej strengthLevel
       pointerY = (int)(345 - strengthLevel);
       //Rysowanie ostrzeżeń pomagających utrzymać siłę ciągu na odpowiednim poziomie
       
       if(pointerY>lowerEdgeOfGreenArea){
           g.drawString("Zwiększ siłę ciągu", 280, 50);
       }
       else if(pointerY<upperEdgeOfGreenArea){
           g.drawString("Zmiejsz siłę ciągu", 300, 50);
       }
       if(pointerY>upperEdgeOfGreenArea && pointerY<lowerEdgeOfGreenArea){           
           g.drawString("Dobrze ci idzie", 300, 50);            
       }
       
       g.setColor(Color.white);
       g.fillRect(10, 250, 30, 100);
        drawGreenArea(g,pointerX,pointerY);
        
       g.setColor(Color.black);       
       g.fillRect(pointerX,pointerY, 30, 5);                              //Wskaźnik
    }//Koniec drawStrength()
    /**
     * Rysowanie HUD
     * @param g parametr graficzny
     * @param points liczba punktów bonusowych
     */
    public void drawPoints(Graphics2D g , double points){                
        g.drawString("" + (int)points, 345, 340);
    }//Koniec drawPoints()
    /**
     * Wypisanie loginu graczas,punktów, liczby bonusów i aktualnego poziomu
     * @param g parametr graficzny
     * @param bonusPoints liczba punktów bonusowych
     * @param currentLevel numer poziomu
     * @param userLogin login użytkownika
     */
    public void drawLevelInfo(Graphics2D g,String userLogin,int bonusPoints,int currentLevel){
        g.setColor(Color.BLACK);
        g.drawString(userLogin, 430, 340);
        g.drawString("Bonusy: "+bonusPoints,540,340);
        drawLevel(g,currentLevel);
        drawPoints(g,points); 
        
    }//Koniec drawLevelInfo
    /**
     * Rysowanie numeru poziomu
     * @param g parametr graficzny
     * @param level akryalny poziom
     */
    public void drawLevel(Graphics2D g, int level){
        g.drawString(""+level,260, 340);
    }//Koniec drawLevel
    /**
     * Rysowanie platformy startowej
     * @param g parametr graficzny
     */
    public void drawPlatform(Graphics2D g){
        g.drawImage(imageStartPlatform, null, 260, 250);
    }
    /**
     * Rysowanie zielonego pola wewnątrz 
     * @param g parametr graficzny
     * @param pointerPosx
     * @param pointerPosy
     */
    public void drawGreenArea(Graphics2D g,int pointerPosx,int pointerPosy){
        
            //Jeżeli wskaźnik znajduje sie w zielonym polu to punkty sa zwiekszane 
        
            if(pointerPosy>upperEdgeOfGreenArea && pointerPosy<lowerEdgeOfGreenArea){                                                                                                                             
                if(points >= 0 && (!stopPointsCounting)){                    
                     points+=0.1;
                }                
            }
                
               
        g.setColor(Color.GREEN);                                                
        g.fillRect(10,upperEdgeOfGreenArea,30,heightOfGreenArea);                                //Wyrysowanie zielonego pola
    }//Koniec drawGreenArea()
    /**
     * Rysowanie ekranu po zakończeniu poziomu
     * @param g parametr graficzny
     * @param fuelLevel
     */
    public void drawFinishScreen(Graphics2D g,int fuelLevel){    
            
            stopPointsCounting=true;
            g.drawString("KONIEC POZIOMU ",300,100);
            g.drawString("Na tym poziomie zdobyłeś: ",250,130);
            g.drawString("+ BlowerPoints:  " + (int)points, 250, 150);
            g.drawString("+ Bonusy:  " + bonusPoints, 250, 180);
            g.drawString("Wciśnij 'Spację' , aby kontunuować", 230, 210);
 
    }//Koniec drawFinishScreen()
    /**
     * Rysowanie ekranu końca gry  po ukonczeniu całej gry
     * @param g parametr graficzny
     */
    public void drawGameOverScreen(Graphics2D g){
            g.drawString("KONIEC  GRY ",300,100);
            g.drawString("Na tym poziomie zdobyłeś: ",250,130);
            g.drawString("+ BlowerPoints:  " + (int)points, 250, 150);
            g.drawString("+ Bonusy:  " + bonusPoints, 250, 180);
            g.drawString("Wciśnij  'Enter ', aby zakończyć i przejśc do menu", 230, 210);
    }//Koniec drawGameOverScreen()
    /**
     * Ustawienie rozmiarów zielonego pola
     * @param upperEdgeOfGreenArea
     * @param heightOfGreenArea
     */
    public  void setSizeOfGreenArea(int upperEdgeOfGreenArea, int heightOfGreenArea){
        
        this.upperEdgeOfGreenArea = upperEdgeOfGreenArea;
        this.heightOfGreenArea = heightOfGreenArea;
        this.lowerEdgeOfGreenArea = upperEdgeOfGreenArea +heightOfGreenArea;
    }
    /**
     * Liczby punktów
     */
    public void setPoints(){
        points = 0;
        stopPointsCounting=false;
    }
    /**
     * Ustawienie pozycji wskaźnika 
     * @param pointerX
     * @param pointerY
     */
    public void setPointerPosition(int pointerX , int pointerY){
        this.pointerX = pointerX;
        this.pointerX = pointerY;
    }
    /**
     * Funkcja zwracająca ilość punktów
     * @return zwraza ilość punktów
     */
    public double getAmountOfPoints(){
        return points;
    }
    
    public void update(){
        
    }
}
