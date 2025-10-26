import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static boolean aabent = true;

    public static void main(String[] args) throws InterruptedException {

        while (aabent == true) {

            List<PizzaMenuObj> pizzaMenuObj = new ArrayList<>();
            pizzaMenuObj = FileUtil.readPizzaFromFile();

            for (PizzaMenuObj p : pizzaMenuObj) {
                System.out.println(p);
            }


            /// Alt under her er det der får tiden til at tælle live ========================================

            // Sæt hvor lang tid leveringen tager (f.eks. 2 minutter)
            int leveringMinutter = 2;
            LocalTime leveringstid = LocalTime.now().plusMinutes(leveringMinutter);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            System.out.println("Leveringstidspunkt: " + leveringstid.format(formatter));
            System.out.println("Nedtælling starter...\n");

            while (true) {
                LocalTime now = LocalTime.now();

                // Beregn hvor lang tid der er tilbage
                Duration diff = Duration.between(now, leveringstid);

                long sekunderTilbage = diff.getSeconds();

                if (sekunderTilbage <= 0) {
                    System.out.print("\r🍕 Ordren er leveret!\n");
                    break;
                }

                long minutter = sekunderTilbage / 60;
                long sekunder = sekunderTilbage % 60;

                System.out.print(
                        String.format("\r⏳ %02d:%02d tilbage - 2x pepperoni", minutter, sekunder)
                );

                Thread.sleep(1000); // Opdater hvert sekund
            }

            Scanner input = new Scanner(System.in);
            input.nextLine();
        }
    }
}