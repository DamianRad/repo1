
package BlowerMain;

import FileManager.FileManager;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Klasa odpoweidzialna za cały proces logowania w grze.
 * Jest ona również klasą zawierającą metodę główną.
 * Klasa ta dziedziczy z klasy JFrame
 * @author Damian
 */
public class Login extends JFrame{
    /**Etykieta z napisem dla loginu*/
    JLabel loginLabel = new JLabel("Login ");
    /**Etykieta z napisem dla hasła*/
    JLabel passwordLabel = new JLabel("Hasło ");
    /**Przycisk do logowania*/
    JButton loginButton = new JButton("Zaloguj się");
    /**Przycisk do rejestracji*/
    JButton singUpButton = new JButton("Zarejestruj się");
    /**Tło ekranu logowania*/
    Image image;
    /**Panel ekranu logowania.Nadpisywanie metody paintComponent w celu wyrysowania tła i informacji rejestracyjnej*/
    JPanel panel = new JPanel(){
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); 
            //Rysowania tła
            g.drawImage(image, 0, 0, null);    
            //Ustawienie czcionki
            g.setFont(new Font("Verdana", Font.PLAIN, 14));
            //Wypisywanie infromacji pomocniczej
            g.drawString("W celu założenia nowego konta wpisz dane w powyższe pola ,a następnie wciśnij przycisk 'Zarejestruj się' ", 20, 400);
        }
        
    };
    /**Pole tekstowe do logowania*/
    JTextField loginField = new JTextField(10);
    /**Pole tekstowe dla hasła*/    
    JPasswordField passField = new JPasswordField(10);
    /**Zmienna przechowująca login użytkownika*/
    String userName;
    /**Zmienna przechowująca login hasło*/
    String userPass;
           
    /**Okno gry*/
    //Game game = new Game();
    
    /**Zmienna przechowująca ścieżkę do pliku z danymi*/
    String dataFilePath;  
    /**Obiekt do operacji na plikach*/
    FileManager fileManag; 
    /**Obiekt do zapisywania*/
    BufferedWriter writer;    
    /**Obiekt do odczytywania loginu*/
    BufferedReader loginReader;
    /**Obiekt do odczytywania hasła*/
    BufferedReader passReader;
    /**Zmienna przechowująca login aktualnie zalogowanego gracza*/
    String whoIsLoginID;
    /**
    * Konstruktor - ustawienia pozycje komponentów w oknie logowania,
    * wczytuje tło, dodaje panel.
    * @author Damian
    */
    public Login(){
                
        //Ustawienie nazwy okna
        super("Blower-logowanie");
        try {
            image=ImageIO.read(getClass().getResourceAsStream("/img/Logowanie.gif"));
        }catch(IOException e){
            
        }
        //Tworzenie obiektu odpowiedzialnego za poprawne zapisywanie danych to odpowiednich plików
        fileManag = new FileManager();
        //Ustawienie rozmiarów okna
        setSize(800,600);
        //Ustawienie okna na środku ekranu
        setLocationRelativeTo(null);
        //Ustawienie odpowiedniej warstwy
        panel.setLayout(new GridBagLayout());
        //Justowanie elementów w oknie 
        /**Obiekt potrzebny do odpowiedniego ustawienia komponentów w oknie logowania*/
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;                               
        gbc.insets = new Insets(5,5,5,5);
                        
        //Ustawianie pozycji poszczególnych komponentów okna logowania 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(loginLabel,gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(loginField,gbc);        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passField,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(loginButton,gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(singUpButton,gbc);
        
        //Dodanie panelu
        getContentPane().add(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        actionLoginButtons();
    }//Koniec kontruktora Login
    /**
    * Funkcja odpowiedzialna za logowanie oraz rejestrację.
    */
    public void actionLoginButtons(){
        //Logowanie
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //Pobranie danych z pół tekstowych
                userName = loginField.getText();
                userPass = passField.getText();
                if(checkCorrectOfData(userName,userPass)){
                    //Wyświtlenie okna gry
                    Game game = new Game();
                    game.setVisible(true);
                    //Zamknięcie okna logowanie                   
                    dispose();          
                } else {
                    //Wyświetlenie komunikatu popup gdy podane dane są nieprawidłowe
                    JOptionPane.showMessageDialog(null,"Niewłaściwe login lub hasło");
                    //Usuwanie zawoartości pól tekstowych po nieprawidłowym zalogowaniu
                    loginField.setText("");
                    passField.setText("");            
                }

            }            
        });    
        //Rejestracja
        singUpButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!loginField.getText().equals("") && !passField.getText().equals("")){
                    //Sprawdzenie czy istenie użytkownik o podanym loginie
                    if(checkLogin(loginField.getText())){
                        //Tworzenie pliku na dane z osiągnięciami gracza
                        fileManag.makeUserDataFile(loginField.getText());
                        //Zapisanie loginu gracza do pliku z loginami
                        fileManag.saveUserLoginInfo("Logins", loginField.getText());
                        //Zapisanie hasła gracza do pliku z hasłami
                        fileManag.saveUserLoginInfo("Passwords", passField.getText());
                        //Zapsanie ID akualnie zalogowanego użytkownika
                        //fileManag.saveCurrentLogin("ID", whoIsLoginID);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Użytkownik o podanym loginie już istnieje");
                    } 
                }
                else{
                    JOptionPane.showMessageDialog(null, "Wprowadź login oraz hasło, aby założyć nowe konto");
                }
            }
                   
        });
        
    }//Koniec actionLoginButtons()
    /**
    * Funkcja odpowiedzialna za sprawdzanie poprawności wpisywanych danych logowania
    * @param userLogin login użytkownika
    * @param userPassword hasło użytkownika
    */
    public boolean checkCorrectOfData(String userLogin,String userPassword){
        
        String pathLogin = System.getProperty("user.dir")+File.separator+"userDataFiles"+File.separator+"Logins.txt";
        String pathPass = System.getProperty("user.dir")+File.separator+"userDataFiles"+File.separator+"Passwords.txt";
        boolean correctLogin=false;
                
        try{
            
            loginReader = new BufferedReader(new FileReader(pathLogin));
            passReader = new BufferedReader(new FileReader(pathPass));
            String lineLogin;
            String linePass;
            //Sprawdzanie poprawności podanych danych
            while((lineLogin=loginReader.readLine())!=null){
                linePass=passReader.readLine();
                
                if(lineLogin.equals(userLogin) && linePass.equals(userPassword)){
                    correctLogin=true;        
                    //Zapisywanie do pliku loginu akutalnie zalogowanego użytkownika
                    fileManag.saveCurrentLogin("ID", loginField.getText());
                    break;
                }
            }
        }catch(IOException e){
            
        }
        return correctLogin;
    }//Koniec checkCorrectData()
    /**
    * Funkcja odpowiedzialna za sprawdzanie czy istnieje już użytkownik o podanym loginie.
    * @param userLogin login użytkownika
    * @return zwraca wartość true jeżeli istnieje , false , jeżeli nie znaleźono uzytkownika o podanym loginie
    */
    public boolean checkLogin(String userLogin){
        
        String pathLogin = System.getProperty("user.dir")+File.separator+"userDataFiles"+File.separator+"Logins.txt";
       
        boolean theSameLogin=true;
 
        try{
            loginReader = new BufferedReader(new FileReader(pathLogin));
           
            String lineLogin;
            //Sprawdzanie poprawności podanych danych
            while((lineLogin=loginReader.readLine())!=null){
                //Jeżeli istnieje juz użytkownik o podanym loginie nie można utworzyć konta
                if(lineLogin.equals(userLogin)){
                    theSameLogin=false;
                    break;
                }
            }
        }catch(IOException e){
            
        }
        return theSameLogin;
    }//Koniec checkLogin()
    //metoda główna
    public static void main(String[] args) {		  
                Login login = new Login();             
    }
    
    
    
}
