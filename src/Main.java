import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;



public class Main {
    public static boolean aabent = true;

    public static void main(String[] args) throws InterruptedException {

        while (aabent == true) {

            List<Pizza> pizza = new ArrayList<Pizza>();
            pizza = FileUtil.readPizzaFromFile();

            for (Pizza p : pizza) {
                System.out.println(p);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                while (true) {

                    LocalTime now = LocalTime.now();
                    System.out.print("\rKlokken er: " + now.format(formatter)); // \r overskriver linjen
                    Thread.sleep(1000); // venter 1 sekund før næste opdatering
                }
            }
        }
    }
}