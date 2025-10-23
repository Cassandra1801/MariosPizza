import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public static List<Pizza> readPizzaFromFile() {

        String deling = "_";
        String line = "";
        String menukortFile = "menukort.txt";
        List<Pizza> menukort = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(menukortFile))){
            while((line = br.readLine()) != null) {
                String[] data = line.split(deling);
                int pizzaID = Integer.parseInt(data[0]);
                String navn = data[1];
                String toppings = data[2];
                int pris = Integer.parseInt(data[3]);
                Pizza pizza = new Pizza(pizzaID, navn, toppings, pris);
                menukort.add(pizza);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return menukort;
    }

}
