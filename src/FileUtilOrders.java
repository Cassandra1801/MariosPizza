import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileUtilOrders {

    public static List<Ordrer> readOrdreFromFile() {                                                                    //Funktion for at få array listen med objekter fra filen
        String deling = "_";                                                                                            //Dette er den definerede deler
        String line = "";
        String ordreFil = "Logs/" + Main.getMaanedNu() + "/log_" + Main.getDato() + ".txt";                             //Reference til hvilken fil
        List<Ordrer> ordreList = new ArrayList<>();                                                                     //Laver en array liste
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm");

        try(BufferedReader br = new BufferedReader(new FileReader(ordreFil))){                                          //Læser fra fil
            while((line = br.readLine()) != null) {
                String[] data = line.split(deling);                                                                     //Bruger deleren til at skille mellem data på linjen

                String pizzaer = data[0];                                                                               //Sætter variabel til data på index 0
                int afhentning = Integer.parseInt(data[1]);                                                             //Sætter variabel til data på index 1
                LocalTime ordreLavet = LocalTime.parse(data[2], timeFmt);                                               //Sætter variabel til data på index 2
                String navn = data[3];                                                                                  //Sætter variabel til data på index 3
                boolean pizzaKlar = Boolean.parseBoolean(data[4]);                                                      //Sætter variabel til data på index 4
                double totalPris = Double.parseDouble(data[5]);
                double prisIkkeKlar = pizzaKlar ? totalPris : 0.0;

                Ordrer ordre = new Ordrer(pizzaer, afhentning, ordreLavet, navn, pizzaKlar, totalPris, prisIkkeKlar);   //Indsætter det forrige data til et pizzaMenuObj objekt
                ordreList.add(ordre);                                                                                   //Indsætter det objekt ind i array listen
            }
        } catch (IOException e) {                                                                                       //Checker for fejl
            System.out.println("Kunne ikke læse fil: " + e.getMessage());
        }
        return ordreList;                                                                                               //Returnerer array listen
    }
}


