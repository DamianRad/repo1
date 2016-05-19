
package GameState;

import FileManager.FileManager;
import TileMap.Background;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 * Ranking - klasa odpowiedzialna wyświetlenia rankingu
 * klasa dziedzicząca po klasie GameState
 * @author Damian
 */

public class RankingState extends GameState{
    /**Obiekt dla obrazka tła*/
    private Background bg; 
    /**Obiekty dla obrazków pomocniczych*/
    private BufferedImage keyUpImage,keyDownImage,enterImage,escpImage;        
    /**Zmienna okreslająca która opcja jest aktualnie wybrana*/
    private int currentOption=0;
    /**Tablica przechowująca opcje w rankingu*/
    private String[] levels = {"Lvl1","Lvl2","Lvl3","Lvl4","Lvl5"};
    /**Obiekt pozwalający na operację na plikach w zależnośći od  wymagań gry*/
    private FileManager fileManag;
    /**Obiekt do odczytywania*/
    private BufferedReader reader;
    /**Tablica przechowująca wyniki pobrane z plików*/
    private String[] results;
    /**Zmienna przechowująca liczbę lini w aktualnie czytanym pliku*/
    private int lines;
    /**Ustawienie koloru tła w rankingu*/
    private Color textColor = new Color(255, 45, 73);
    /**Ustawienie czcionki tekstu*/
    private Font font;
    /**
    * Konstruktor inicjalizujący obiekty i zmienne pomocnicze.
    * Wywoływana jest w nim funkcja wczytując zawartość pierwszego pliku do tablicy z wynikami.
    * Klasa dziedzicząca po klasie GameState
    * @author Damian
    */
    public RankingState(GameStateManager gsm){
        this.gsm=gsm;
        fileManag = new FileManager();
        font = new Font("Arial", Font.BOLD, 10);
        bg=new Background("/img/Ranking.gif", 1);
        //Pierwsza pozycja w menu jako domyślenie wybrana
        currentOption=0;
        try{
            keyUpImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/up.png"));
            keyDownImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/down.png"));
            enterImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/enter.png"));
            escpImage = ImageIO.read(getClass().getResourceAsStream("/imgKey/escp.png"));
        }catch(Exception e){
            
        }
        
        //Odczytaj zawartość pierwszego pliku z wynikami w celu odczytania pierwszych wartości z pliku , i zainizializowania tablicy 
        readLvlResults(1);
    }
    

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D g) {
        //Rysowanie tła
        bg.draw(g);
        
        //Rysowanie opcji poziomów do wybrania
        for(int i = 0 ; i < levels.length ; i++){

            if(i==currentOption){    
                g.setColor(Color.BLACK);
            }
            else{
                g.setColor(textColor);
            }
            g.drawString(levels[i],50,100+50*i);

        }
        g.setColor(textColor);
        
        g.setFont(font);
        //Rysowanie nagłówka lini
        g.drawString("Punkty       Czasy gry [s]            Czas dmuchania[s]            Bonusy              Data", 130, 70);
        //Wyrysowanie wyników
        for(int i=0;i<results.length;i++){
             g.drawString(results[i], 150, 100+20*i);
        }
        g.drawImage(keyUpImage, null, 100, 320);
        g.drawImage(keyDownImage, null, 130, 320);
        g.drawString(" - Wybierz poziom", 150, 330);
        g.drawImage(enterImage, null, 240, 320);
        g.drawString(" - Zatwierdź wybrany poziom aby wyświetlić wyniki", 280, 330);
        g.drawImage(escpImage, null, 540, 320);
        g.drawString("- Wróć", 560, 330);
    }
    /**
    * Funkcja odpowiedzialna za wywołanie funkcji odczytującej wyniki, w zależności   
    * od wyboru dokonanego przez użytkownika.
    */
    public void select(){
        if(currentOption==0){
            readLvlResults(1);
        }
        if(currentOption==1){
            readLvlResults(2);
        }
        if(currentOption==2){
            readLvlResults(3);
        }
        if(currentOption==3){
            readLvlResults(4);
        }
        if(currentOption==4){
            readLvlResults(5);
        }
    }//Koniec select()
    @Override
    public void keyPressed(int k) {
        if(k==KeyEvent.VK_ENTER){
            select();
        }
        if(k==KeyEvent.VK_UP){
            
            currentOption--;
            if(currentOption==-1){
                currentOption=levels.length-1;
            }
        }
         if(k==KeyEvent.VK_ESCAPE){
            gsm.currentState=0;
        }
        if(k==KeyEvent.VK_DOWN){
            
            currentOption++;
            if(currentOption==levels.length){
                currentOption=0;
            }
        }
    }//Koniec keyPressed()

    @Override
    public void keyReleased(int k) {
        if(k==KeyEvent.VK_ENTER){
            
        }
    }
    /**
    * Funkcja wczytując zawartość pliku do tablicy z wynikami. Parametr określa numer pliku z którego mają być wczytane dane.
    * Format nazwy pliku: level.txt
    * @param level
    */
    public void readLvlResults(int level){
        try{
                       
            String path=System.getProperty("user.dir")+"/userDataFiles/usersData/"+fileManag.readCurrentLogin()+"/lvl"+Integer.toString(level)+".txt";
            reader = new BufferedReader(new FileReader(path));
            /**Zmienna do przechowywania aktualnie odczytywanej z pliku lini*/
            String line;
            //Pobranie ilości lini w pliku
            lines = fileManag.getAmountOfLines(level);
            //Inicjalizacja tablicy dla wyników
            results = new String[lines];
            //Licznik dla indeksów tablicy
            int i = 0;      
            /**Zmienna słuzaca do przechowywania aktal*/
            //Odczytywanie zawartości pliku i zapisywanie go do tablicy w wynikami
            while((line=reader.readLine())!=null){
                results[i]=line;     
                i++;
            }
            //Sortowanie wyników w tablicy z wynikami , na podstawie ilości zdobytych punktów
            String buf;
            //Index separatora w bieżącej lini
            int indexOfSepInCurrentLine;
            //Index separatora w kolejenej lini
            int indexOfSepInNextLine;
            //Flaga zatrzymania sortowania
            boolean stopSort=true;
            while(stopSort){
                stopSort=false;
                for(int j =0;j<results.length-1;j++){
                        //Znajdowanie indeksu pierwszego wystąpienia separatora w bieżącej lini
                        indexOfSepInCurrentLine=results[j].indexOf(";");
                        //Znajdowanie indeksu pierwszego wystąpienia separatora w kolejenj lini
                        indexOfSepInNextLine=results[j+1].indexOf(";");
                        //Konwersja
                        if(Integer.parseInt(results[j].substring(0, indexOfSepInCurrentLine))<Integer.parseInt(results[j+1].substring(0, indexOfSepInNextLine))){
                            buf=results[j];
                            results[j]=results[j+1];
                            results[j+1]=buf;
                            stopSort=true;
                        }
                }
            }
            /**Zmienna przechowująca aktalnie zmienioną linię*/
            String repleceLine;
            //Zamiana średnikow na szećś tabulatorów, w celu polepszenia wyglądu wyświetlania wyników
            for(int k = 0 ; k < results.length;k++){
                repleceLine=results[k].replaceAll(";", "                        ");
                results[k]=repleceLine;
            }
            
            //Zamknięcie strumienia odczyty
            reader.close();
        }catch(IOException e){
            System.out.println("Nie udało się odczytać zawartości pliku");
        }
        
    }
    
    
}
