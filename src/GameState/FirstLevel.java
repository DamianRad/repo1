
package GameState;
import FileManager.FileManager;
import HUD.Hud;
import Objects.Bonus;
import Objects.Player;
import Objects.Wind;
import Background.Background;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Klasa główna odpowiedzialna za właściwą rozgrywkę. 
 * Klasa dziedzicząca po klasie GameState.
 * @author Damian
 */

public class FirstLevel extends GameState{
    /**Obiekt tła*/
    private Background bg;
    /**Obiekt gracza*/
    private Player player;
    /**Obiekt interfejsu graficznego*/
    private Hud hud;
    /**Obiekt bonusu*/
    private Bonus bonus;
    /**Obiekt przeszkody*/
    private Wind wind;
    /**Zmienna ustawiająca zapisu format daty*/
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    /**Data w momencie rozpoczęcia pomiaru czasu trwania poziomu*/
    Date startDate ;
    /**Data zakończenia poziomu*/
    Date finishDate;
    //Łączny czas dmuchania na danym poziomie
    /**Data ropoczęcia dmuchania*/
    Date startBlowingDate;
    /**Data zakończenia dmuchania*/
    Date finsihBlowingDate;
    /**Całkowity czas dmuchania*/
    long totalBlowingTime=0;
    //Zmienne przechowujące wartości ilości paliwa i siły ciągu plecaka
    /**Zmienna przechowująca aktualny poziom paliwa*/
    private double fuelLevel = 0;
    /**Zeminna przechowująca aktualny poziom siły ciągu plecaka*/
    private double strengthLevel = 0;
    /**Ustawienie opóżnienia poruszania sie wskażnika w górę*/
    private double strengthDelayUp;
     /**Ustawienie opóżnienia poruszania sie wskażnika w dół*/
    private double strengthDelayDown; 
     /**Ustawienie opóżnienia poruszania sie wskażnika*/
    private double strengthDx=2;
    /**Zmienna pomocnicza określająca czy został ciśniety przycisk zmiany ciągu paliwa*/
    private boolean ifIsPress = false;
    /**Zmienna pomocnicza określająca czy został zwolniony przycisk zmiany ciągu paliwa*/
    private boolean ifIsReleased = false;    
    /**Zmienna pomocnicza określająca czy został raz ciśniety przycisk zmiany ciągu paliwa*/
    private boolean firstPress = false;
    /**Zmienna pomocnicza określająca czy status ukonczenia poziomu*/
    private boolean levelEnd = false;
    private boolean startMeasureNextBlowingTime = true;
    /**Zmienna pomocnicza określająca skok zwiekszający prędkość przewijania ekranu*/
    private double vectUp = 0.08;        
    /**Zmienna pomocnicza określająca prędkość przewijania ekranu*/
    private double vect;
    //Skok przesuwania ekranu
    private double vectDx = 0.09;
    /**Zmienna pomocnicza określająca aktualny poziom*/
    private int currentLevel;    
    /**Zmienna pomocnicza określająca aktualną ilość punktów*/
    private double points;
    /**Obiek służący do operacji na plikach*/
    FileManager fileManag;
    /**Zmienna pomocnicza określająca ilość punktów przy jakiej ma się pojawić wiatr utrudniający rozgrywke*/
    private int pointsForWind=50;
    /**
     * Konstruktor - inizjalizacja obiektów.
     * @param gsm menadzer stanów
     */
    public FirstLevel(GameStateManager gsm){ 
         this.gsm = gsm;
         //Ustawienie pierwszego poziomu
         currentLevel = 1;        
         //Inizjalizacja obiektów
         fileManag = new FileManager();                 
         player = new Player();            
         bg = new Background("/img/BackGroundLvl1v2.gif", 1);         
         hud = new Hud();
         bonus = new Bonus(player);
         wind = new Wind();
         
    }
    //Rysowanie
    public void draw(Graphics2D g){
       //Rysowanie tła
       bg.draw(g);
       //Rysowanie gracza
       player.draw(g);
       //Rysowanie HUD
       bonus.drawBonus(g,(int)fuelLevel,firstPress);
       //Rysowanie interejsu w grze
       hud.drawHud(g,(int)fuelLevel,strengthLevel,bonus.amountOfBonus,currentLevel,fileManag.readCurrentLogin());       
       g.setColor(Color.black); 
       //Rysowanie rampy startowej (po pierwszym wcisnieciu górnej strzałki w górę znika)
       if(!firstPress){                                            
         g.drawString("STARTUJ !!! ", 300, 100);
         hud.drawPlatform(g);         
       }  
       //Po zdobyciu ustalonej liczby punktów pojawia się utrudnienie w postaci wiatru 
       //powodujące utrudnienie utrzymania wskażnika we wskazanym polu
       if(hud.getAmountOfPoints()>pointsForWind){        
        wind.draw(g);    
        strengthDelayUp=strengthDx;
        strengthDelayDown=strengthDx;
       }
       else{           
           strengthDelayUp=0.5;
           strengthDelayDown=0.5;
       }
       //Po zakoniczeniu danego poziomu ustawiana jest statyczna pozycja postaci
       if(levelEnd){
            player.setFinishPosition(400, 150);
       }
       
       
    }//Koniec draw()
    
    //Obsługa wciśnięcia przycisku
    public void keyPressed(int k){
        //Pauza po wcisnięciu przycisku escape
        if(k==KeyEvent.VK_ESCAPE){
            gsm.currentState=4;           
        }
        //Obsługa przycisku enter
        if(k==KeyEvent.VK_ENTER){
            //Jeżeli ostatni poziom został ukończonu , nastąpi przejście do menu , i ustawienie wartości
            //pierwszego poziomu na domyslne
            if(currentLevel==5 && levelEnd){
                //finishDate = new Date();
                long diff = getDateDiffrence(startDate,finishDate,TimeUnit.SECONDS);
                //Pobranie ilości zdobytych punktów            
                points=hud.getAmountOfPoints();
                //Zapisanie danych do pliku (totalBlowingTime jest dzielony przez 1000 w celu usyskania sekund)
                fileManag.saveUserData(currentLevel,(int)points,(int)diff,(int)totalBlowingTime/1000,bonus.amountOfBonus,dateFormat.format(startDate));
                currentLevel=1; 
                nextLevel(currentLevel);
                gsm.currentState=0;
            }
        }
        //Spacja jest obługiwana tylko wtedy kiedy poziom został zakończony
        if(fuelLevel>=100 && !(currentLevel==5 && levelEnd)){
            //Po ukończeniu poziomu aktualnego, wciśniecię spacji powoduje przejście do kolejnego poziomu
            if(k == KeyEvent.VK_SPACE){  
                //Data zakończenia poziomu
                finishDate = new Date();
                /**Różnica pomiędzy czasem rozpoczęcia poziomu a czasem zakończenia*/
                long diff = getDateDiffrence(startDate,finishDate,TimeUnit.SECONDS);
                //Pobranie ilości zdobytych punktów            
                points=hud.getAmountOfPoints();
                //Zapisanie danych do pliku (totalBlowingTime jest dzielony przez 1000 w celu usyskania sekund)
                fileManag.saveUserData(currentLevel,(int)points,(int)diff,(int)totalBlowingTime/1000,bonus.amountOfBonus,dateFormat.format(startDate));
                //Zmiana statusu aktualnego poziomu                            
                currentLevel+=1;    
                //Przejście do kolejengo poziomu
                nextLevel(currentLevel);                                
            }
            
        }
        //Zablokowanie możliwości kontrolowania postaci po zakonczenoiu poziomu(postać ustawiana jest w statycznej pozycji) 
        if(!levelEnd){
            player.playerControl(k);       
        }      
        //Obsługa wciśnięcia przycisku strzałki w górę
            if(k == KeyEvent.VK_UP){ 
                 
                //Wciśniecie strzałki w górę powoduje rozpoczęcie pomiaru czasu trwania poziomu
                if(!firstPress){
                    startDate = new Date();                    
                } 
                
                //Rozpoczęcie pomiaru czasu dmuchnięcia
                if(startMeasureNextBlowingTime){
                    startBlowingDate = new Date();
                }
                
                startMeasureNextBlowingTime=false;
                //Ustawienie zmiennych pmocniczych
                firstPress = true;                
                ifIsPress=true;                                                
                ifIsReleased=false; 
                //Ustawieni prędkości przesuwania ekranu 
                vect=vectUp;                                                   //Przyspieszanie przewijania ekranu przy wznoszeniu
                //Zwiększanie prędkości przesuwania ekranu 
                vectUp+=vectDx;                                                //Prędkośc tła czy wznoszeniu
            }              
    }//Koniec keyPressed()
    //Obsługa w przypadku zwolnienia przycisku
    public void keyReleased(int k){      
      player.keyReleased(k);  
      
      if(k == KeyEvent.VK_UP){          
          ifIsReleased=true;
          //Zakończenie pomiaru czasu dmuchnięcia
          finsihBlowingDate = new Date();
          //Zapisanie różnicy pomiędzy czasem zakończenia dmuchania i rozpoczęcia do zmiennej totalBlowingTime
          totalBlowingTime+=getDateDiffrence(startBlowingDate,finsihBlowingDate, TimeUnit.MILLISECONDS); 
          //Ustawienie flagi pomiaru czasu dmuchania na true
          startMeasureNextBlowingTime=true;
      }
      
    }//Koniec KeyReleased()
    
    public void init(){
        
    }
    //Aktualizowanie
    public void update() {
        //Aktualizowanie pozycji bonusu
        bonus.update();
        //Przesuwanie ekranu o zadany wektor
        bg.setVector(0, vect);                                          
        //Aktualizowanie pozycji gracza
        player.update();    
        //Zwolnienie przycisku
        if(ifIsReleased) {
            //W momencie pciśnięcia przycisku wskażnik ma opadać
            if(strengthLevel >0)                                        
                strengthLevel-=strengthDelayUp;                                       
        }
        //Wciśnieęcie przycisku
        else if(ifIsPress) {
            //W momencie wciśnięcia przycisku wskażnik ma się wzności
            if(strengthLevel <90) {                                                         
                strengthLevel+=strengthDelayDown;                                           
            }
            //W momencie wciśnięcia przycisku ma zmniejszać się ilość paliwa 
            if(fuelLevel<100 ){
                fuelLevel+=0.2;
            }
            else{
               //Jeżeli koniec paliwa to koniec poziomu
               levelEnd=true;                                  
            }             
        }
        //Jeżeli gracz jest powyżej ustalenej pozycji ekranu, tło jest przewijane
        if(player.y < 380 ) {                                             
            bg.update();
        }
        else{
        //Jeżeli gracz jest poniżej dolnej krawędzi ekranu następuje ponowne rozpoczęcie gry
            nextLevel(currentLevel);
        }
    }//Koniec update()
    /**
     * Funkcja odpowiedzialna za przejście do kolejnego poziomu
     * @param currentLevel numer poziomu
     */
    public void nextLevel(int currentLevel){
        switch(currentLevel){
            case 1:
                setNextLevel(300, 260, 260, 70,2,50);
                bg.reloadBackGroundImage("/img/BackGroundLvl1v2.gif");
                break;
            case 2:
                setNextLevel(300, 260, 270, 60,2,50);
                bg.reloadBackGroundImage("/img/BackGroundLvl2v2.gif");
                break;
            case 3:
                setNextLevel(300, 260, 270, 50,2,50);
                bg.reloadBackGroundImage("/img/BackGroundLvl3v2.gif");
                break;
            case 4:
                setNextLevel(300, 260, 270, 30,1,30);
                bg.reloadBackGroundImage("/img/BackGroundLvl4v2.gif");
                break;
            case 5:
                setNextLevel(300, 260, 270, 20,1,30);
                bg.reloadBackGroundImage("/img/BackGroundLvl5v2.gif");
                break;
            case 6:
                
                
                break;
        }
    }//Koniec nextLevel()
    /**
     * Funkcja odpowiedzilana ustawienie parametrów startowych dla kolejnego poziomu.
     * @param startPlayerPosX pozycja gracza x
     * @param startPlayerPosY pozycja gracza y
     * @param upperEdgeOfGreenArea górna krawędz zielonego pola
     * @param heightEdgeOfGreenArea wysokośc zielonego pola
     * @param windStrength siła wiatru
     * @param pointsForWind liczba punktów przy jakiej ma się pojawić przeszkoda
     */
    public void setNextLevel(int startPlayerPosX, int startPlayerPosY, int upperEdgeOfGreenArea , int heightEdgeOfGreenArea, int windStrength,int pointsForWind){
        //Ustawianie parametrów dla kolejnego poziomu
        points=0;
        strengthLevel =0;       
        fuelLevel=0;
        strengthDx=windStrength;
        this.pointsForWind=pointsForWind;
        firstPress=false;
        vect=0;
        vectUp=0.08;
        levelEnd=false;
        totalBlowingTime=0;
        player.setPlayerPosition(startPlayerPosX, startPlayerPosY);
        //ustawienie prędkosci gracza
        player.setPlayerVelocity(0, 0);
        //ustawieni rozmiaru zielonego pola
        hud.setSizeOfGreenArea(upperEdgeOfGreenArea, heightEdgeOfGreenArea);
        //hud.setPointerPosition(0, 0);        
        hud.setPoints();
        bg.setVector(0, vect);
        bonus.amountOfBonus=0;
               
    }//Koniec setNextLevel()
    /**
     * Funkcja zwracająca różnicę między czasem zakończenia poziomu a czasem rozpoczęcia
     * @param startData rozpoczecie pomiaru czasu 
     * @param finishData zakończenie pomiaru czasu
     * @param timeUnit parametr potrzebny do konwersji otrzymanej róznicy na milisekundy
     * @return zwraca różnicę między czasem zakończenia poziomu a czasem rozpoczęcia
     */
    public long getDateDiffrence(Date startData , Date finishData , TimeUnit timeUnit){
        long diffInSeconds = finishData.getTime()-startData.getTime();
        return timeUnit.convert(diffInSeconds, TimeUnit.MILLISECONDS);
    }//Koniec getDateDiffrence()
    public long getTotalBlowingTime(Date releaseData,TimeUnit timeUnit){
        long blowingTime = releaseData.getTime();
        return timeUnit.convert(blowingTime, TimeUnit.MILLISECONDS);
    }//Koniec getTotalBlowingTime()
}//Koniec klasy FirstLevel()

