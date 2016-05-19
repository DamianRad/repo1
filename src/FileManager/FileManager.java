
package FileManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
/**
 * Klasa będąca menadżerem plików.
 * Zawiera różne funkcje do wymaganych działań na plikach.
 * @author Damian
 */
public class FileManager {
    /**Obiekt służący do tworzenia nowego pliku*/
    File dataFile;
    /**Obiekt służący do zapisywania do pliku*/
    BufferedWriter writer;
    /**Obiekt służący do odczytywania z pliku*/
    BufferedReader reader;
    /**
     * Funkcja odpowiedzialna za tworzenie folder o nazwie gracza , oraz 5 plików do składowania danych o osiągnięciach
     * @param fileName nazwa folderu w ktorym mają znaleźć się pliki do składowania danych o osiągnięciach
     */
    public void makeUserDataFile(String fileName){
            /**Ścieżka do plików z danymi*/
            String dataFilePath ; 
            try{
                //Tworzenie 5 plików z danymi
                for(int i=0; i < 5;i++){
                    dataFilePath = System.getProperty("user.dir")+File.separator +"userDataFiles"+File.separator+"usersData"+File.separator + fileName+File.separator+"lvl"+(i+1)+".txt";
                    dataFile = new File(dataFilePath);
                    dataFile.getParentFile().mkdirs(); 
                    //Stwórz nowy plik
                    dataFile.createNewFile();
                }
                //wyświetlnienie komunikatu , o poprawnym utworzeniu folderu(który ztaje się kontem gracza) oraz plików z danymi
                JOptionPane.showMessageDialog(null, "Twoje konto zostalo pomyslnie utworzone, teraz możesz się zalogować.");
            }catch(Exception excep){
                System.out.println("Plik się nie utworzył");                        
            }
    }//Koniec makeUserDataFile()
    /**
     * Funkcja odpowiedzialna za zapisywanie danych do pliku o podanej nazwie w folderze userDataFiles(z dopisywaniem do zawartości pliku)
     * @param fileName 
     * @param dataToWrite dane do zapisu
     */
    public void saveUserLoginInfo(String fileName ,String dataToWrite ){
            /**Ścieżka do plików z danymi*/
            String dataFilePath = System.getProperty("user.dir")+File.separator +"userDataFiles"+File.separator;
            try{
                writer = new BufferedWriter(new FileWriter(dataFilePath+fileName+".txt",true));
                writer.write(dataToWrite);
                writer.newLine();
            }catch(IOException excep){
                System.out.println("Nie udało się zapisać danych do plkiu");
            }
                    
            if(writer!=null){
                try{
                    writer.close();
                }catch(IOException except){
                    System.out.println("Nie udało się zamknąc strumienia");
                }
            }
    }//Koniec saveUserLoginInfo()
    /**
     * Funkcja odpowiedzialna za zapisywanie nazwy aktualnie zalogowanego gracza do pliku o podanej nazwie
     * @param fileName nazwa pliku 
     * @param currentLogin login aktualnie zalogowanego uzytkownika
     */
    public void saveCurrentLogin(String fileName,String currentLogin){
        String dataFilePath = System.getProperty("user.dir")+File.separator +"userDataFiles"+File.separator;
            try{
                writer = new BufferedWriter(new FileWriter(dataFilePath+fileName+".txt",false));
                writer.write(currentLogin);
            }catch(IOException excep){
                System.out.println("Nie udało się zapisać danych do plkiu");
            }
                    
            if(writer!=null){
                try{
                    writer.close();
                }catch(IOException except){
                    System.out.println("Nie udało się zamknąc strumienia");
                }
            }
    }//Koniec saveCurrentLogin() 
    /**
     * Funkcja odpowiedzialna za zapisywanie danych z osiągnięciami gracza do dopowiedniego pliku
     * @param level numer aktualnie zakńczonego poziomu
     * @param points liczba punktów zdobytych na danym poziomie
     * @param totalTime całkowity czas gry
     * @param blowingTime czas dmuchania
     * @param data data zakończenia poziomu
     */
    public void saveUserData(int level,int points,int totalTime,int blowingTime,int bonus,String data){
        
        /**Zmienna przechowująca login aktualnie zalogowanego gracza*/
        String folderName = readCurrentLogin();
        /**Zmianna przechowująca ścieżkę do pliku z osiągnięciami gracza*/
        String pathToUserFolder = System.getProperty("user.dir")+File.separator+"userDataFiles"+File.separator+"usersData"+File.separator+folderName+File.separator+"lvl"+Integer.toString(level)+".txt";
        
        try{
            
            writer = new BufferedWriter(new FileWriter(pathToUserFolder,true));
                //Zapisywanie danych do pliku
                writer.write(Integer.toString(points)+";"+Integer.toString(totalTime)+";"+Integer.toString(blowingTime)+";"+Integer.toString(bonus)+";"+data);
            //Dodanie nowej lini w pliku po zapisaniu danych
            writer.newLine();
            
        }catch(IOException e){
            System.out.println("Nie udało się zapisać osiągnięć uzytkownika");
        }
        if(writer!=null){
                try{
                    writer.close();
                }catch(IOException except){
                    System.out.println("Nie udało się zamknąc strumienia");
                }
            }
    }
    /**
     * Funkcja pobierająca login aktualnie zalogowanego użytkownika.
     * @return zwróć login aktualnie zalogowanego uzytkownika
     */
    public String readCurrentLogin(){
        String currentLogin="";
        String path=System.getProperty("user.dir")+File.separator+"userDataFiles"+File.separator+"ID.txt";
        try{
            reader=new BufferedReader(new FileReader(path));
            currentLogin = reader.readLine();
        }catch(IOException e){
            System.out.println("Nie udało się odczytać loginu aktualnie zalogowanego użytkownika");
        }
        
        return  currentLogin;
    }//Koniec readCurrentLogin()
    /**
     * Funkcja zwarająca liczbę lini w plkiu z danymi (lvl1,lvl2,itp). 
     * @param level numer poziomu
     * @return liczba lini w pliku
     */
    public int getAmountOfLines(int level){
        //ścieżka do pliku z danymi o osiągnięciach
        String path = System.getProperty("user.dir")+"/userDataFiles/usersData/"+readCurrentLogin()+"/lvl"+Integer.toString(level)+".txt";
        /**Zmienna przechowująca liczbę lini jaka znajduje się w pliku.*/
        int lines = 0;
        try{
            String line;
            reader = new BufferedReader(new FileReader(path));
            while((line=reader.readLine())!=null){
                lines++;
            }
            reader.close();
        }catch(IOException e){
            
        }
        return lines;        
    }//Koniec getAmountOfLInes()
}
