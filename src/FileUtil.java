import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<PizzaMenuObj> readPizzaFromFile() {                                      //Funktion for at få array listen med ojekter fra filen

        String deling = "_";                                                                    //Dette er den definerede deler
        String line = "";
        String menukortFile = "menukort.txt";                                                   //Referense til hvilken fil
        List<PizzaMenuObj> menukort = new ArrayList<>();                                        //Laver en array liste

        try(BufferedReader br = new BufferedReader(new FileReader(menukortFile))){              //Læser fra fil
            while((line = br.readLine()) != null) {
                String[] data = line.split(deling);                                             //Bruger deleren til at at skille mellen data på linjen
                int pizzaID = Integer.parseInt(data[0]);                                        //Sætter variabel til data på index 0
                String navn = data[1];                                                          //Sætter variabel til data på index 1
                String toppings = data[2];                                                      //Sætter variabel til data på index 2
                int pris = Integer.parseInt(data[3]);                                           //Sætter variabel til data på index 3
                PizzaMenuObj pizzaMenuObj = new PizzaMenuObj(pizzaID, navn, toppings, pris);    //Indsætter det forrige data til et pizzaMenuObj objekt
                menukort.add(pizzaMenuObj);                                                     //Indsætter det objekt ind i array listen
            }
        } catch (IOException e) {                                                               //Checker for fejl
            e.printStackTrace();
        }
        return menukort;                                                                        //Returnerer array listen
    }

}
