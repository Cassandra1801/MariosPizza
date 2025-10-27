import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {
    public static boolean aabent = true;

    public static void main(String[] args) throws InterruptedException {

        DagensFil();

        while (aabent == true) {

            List<PizzaMenuObj> pizzaMenuObj = new ArrayList<>();
            pizzaMenuObj = FileUtil.readPizzaFromFile();

            for (PizzaMenuObj p : pizzaMenuObj) {
                System.out.println(p);
            }

            /*
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

             */

            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();                       //inputtet må lagres som en string variabel, for ellers vil equals ikke læse det

            if (input.equals("new")) {                          //Bruger equals til at sammenligne, ikke sige det er det (there is a difference somehow)
                System.out.println("Laver ny");


                String hvilkePizza;
                int tid;
                String navn;

                Scanner scPizza = new Scanner(System.in);
                Scanner scTid = new Scanner(System.in);
                Scanner scNavn = new Scanner(System.in);

                hvilkePizza = scPizza.nextLine();
                tid = Integer.parseInt(scTid.nextLine());
                navn = scNavn.nextLine();

                System.out.println(hvilkePizza + " " + tid + " min " + navn + " ");



                /// Tilføjer til filen fra inputc

            } else if (input.equals("sluk")) {
                System.out.println("Slukker");
                aabent = false;
            } else if (input.equals("Help")) {
                System.out.println("new");
                System.out.println("sluk");
                System.out.println("help");
            } else {
                System.out.println("Prøv i stedet følgende:");
                System.out.println("new");
                System.out.println("sluk");
                System.out.println("help");
            }
        }
    }



                                                                                              //Laver file for dagen til at lagre ordrer på dagen
    public static void DagensFil() {

    DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    String date = LocalDateTime.now().format(formatter);

        try {
            File myObj = new File(date + ".txt");                                            // Create File object
            if (myObj.createNewFile()) {                                                    // Try to create the file
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();                                                            // Print error details
        }
    }
}