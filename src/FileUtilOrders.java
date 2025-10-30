import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
                if (data.length > 6) continue; //Spring ugyldige linjer over

                String pizzaer = data[0];                                                                               //Sætter variabel til data på index 0
                int afhentning = Integer.parseInt(data[1]);                                                             //Sætter variabel til data på index 1
                LocalTime ordreLavet = LocalTime.parse(data[2], timeFmt);                                               //Sætter variabel til data på index 2
                String navn = data[3];                                                                                  //Sætter variabel til data på index 3
                boolean pizzaKlar = Boolean.parseBoolean(data[4]);                                                      //Sætter variabel til data på index 4

                double totalPris = 0.0;                                                                                 //Beskyt mod at totalPris mangler
                if (data.length > 5) {
                    totalPris = Double.parseDouble(data[5]);
                }
                //if (pizzaKlar) continue;                                                                              //Spring ordrer over som allerede er hentet(true)

                double prisIkkeKlar = pizzaKlar ? totalPris : 0.0;

                Ordrer ordre = new Ordrer(pizzaer, afhentning, ordreLavet, navn, pizzaKlar, totalPris, prisIkkeKlar);   //Indsætter det forrige data til et pizzaMenuObj objekt
                ordreList.add(ordre);                                                                                   //Indsætter det objekt ind i array listen
            }
        } catch (IOException e) {                                                                                       //Checker for fejl
            System.out.println("Kunne ikke læse fil: " + e.getMessage());
        }
        return ordreList;                                                                                               //Returnerer array listen
    }

    public static void visSolgtePizzaerPrType(List<Ordrer> ordrerList) {
        Map<String, Integer> pizzaTaelling = new LinkedHashMap<>();

        for (Ordrer ordre : ordrerList) {
            if (ordre.getPizzaKlar()) {                                                                                 // kun færdige ordrer (true)
                String[] dele = ordre.getPizzaer().split("\\s+");                                                 // fx "2xVesuvio 1xCarbonara"
                for (String d : dele) {
                    if (!d.contains("x")) continue;
                    String[] antalNavn = d.split("x", 2);
                    if (antalNavn.length != 2) continue;

                    try {
                        int antal = Integer.parseInt(antalNavn[0]);
                        String navn = antalNavn[1];
                        pizzaTaelling.put(navn, pizzaTaelling.getOrDefault(navn, 0) + antal);
                    } catch (NumberFormatException ignored) {}
                }
            }
        }

        System.out.println("Solgte pizzaer i dag:");
        if (pizzaTaelling.isEmpty()) {
            System.out.println("  (ingen færdige ordrer endnu)");
        } else {
            for (Map.Entry<String, Integer> e : pizzaTaelling.entrySet()) {
                System.out.println("  " + e.getKey() + ": " + e.getValue() + " stk.");
            }
        }
    }

}


