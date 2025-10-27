import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtilOrders {
    public static List<Ordrer> readOrdreFromFile() {                                      //Funktion for at få array listen med objekter fra filen

        String deling = "_";                                                                    //Dette er den definerede deler
        String line = "";
        String ordreFil = Main.getDate() + ".txt";                                                   //Reference til hvilken fil
        List<Ordrer> ordreList = new ArrayList<>();                                        //Laver en array liste

        try(BufferedReader br = new BufferedReader(new FileReader(ordreFil))){              //Læser fra fil
            while((line = br.readLine()) != null) {
                String[] data = line.split(deling);                                             //Bruger deleren til at skille mellem data på linjen
                int pizzaID = Integer.parseInt(data[0]);                                        //Sætter variabel til data på index 0
                String navn = data[1];                                                          //Sætter variabel til data på index 1
                String toppings = data[2];                                                      //Sætter variabel til data på index 2
                double pris = Double.parseDouble(data[3]);                                      //Sætter variabel til data på index 3
                Ordrer ordre = new Ordrer();    //Indsætter det forrige data til et pizzaMenuObj objekt
                ordreList.add(ordre);                                                     //Indsætter det objekt ind i array listen
            }
        } catch (IOException e) {                                                               //Checker for fejl
            e.printStackTrace();
        }
        return ordreList;                                                                        //Returnerer array listen
    }
}
