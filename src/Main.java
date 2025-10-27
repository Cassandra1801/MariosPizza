import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {

    //Boolean for at holde while loop kørende
    public static boolean aabent = true;

    //Finder datoen
    public static DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static String datoen = LocalDateTime.now().format(formatter);

    public static String getDate() {
        return datoen;
    }

    //Laver file for dagen til at lagre ordrer på dagen
    public static void DagensFil() {
        try {
            File myObj = new File(getDate() + ".txt");                                            // Create File object
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

    //Finder tiden
    public static DateTimeFormatter timeFormatter =  DateTimeFormatter.ofPattern("HH:mm");
    public static String tidenNu = LocalDateTime.now().format(timeFormatter);

    public static String getTime() {
        return tidenNu;
    }

    public static void main(String[] args) throws InterruptedException {

        //Laver fil når dagen starter (DD-MM-YYYY.txt)
        DagensFil();

        //loop der gør at man altid returnerer til startmenu og udskriver pizza menuen
        while (aabent == true) {

            //Udskriver pizza menu
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

            System.out.println(" ");

            System.out.println("=== Start Menu =================================");

            //scanner der tager input til start menu
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();                       //inputtet må lagres som en string variabel, for ellers vil equals ikke læse det

            //Start menu elementer for hvert if statement
            //Laver en ny ordre
            if (input.equals("new")) {                          //Bruger equals til at sammenligne, ikke sige det er det (there is a difference somehow)
                System.out.println("Laver ny");

                String hvilkePizza;
                int tid;
                String navn;

                Scanner scPizza = new Scanner(System.in);
                Scanner scTid = new Scanner(System.in);
                Scanner scNavn = new Scanner(System.in);

                //input for bestillingen
                System.out.println("Bestilling:         (I format ANTALxTYPE, sepparer typer med et mellemrum) ");
                hvilkePizza = scPizza.nextLine();

                //input for tiden på bestillingen
                System.out.println("Tid:");
                tid = Integer.parseInt(scTid.nextLine());

                //input for navn på bestillingen
                System.out.println("Navn:");
                navn = scNavn.nextLine();

                System.out.println(hvilkePizza + " " + tid + " min " + navn + " ");

                // true = append mode
                try (FileWriter myWriter = new FileWriter(getDate() + ".txt", true)) {
                    myWriter.write(hvilkePizza + "_" + tid + "_" + getTime() + "_" + navn + "_" + "false" + "\n");
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                System.out.println("Ordre er lavet");
                System.out.println(" ");


                /// Tilføjer til filen fra inputc

            } else if (input.equals("sluk")) {
                System.out.println("slukker");
                aabent = false;

            } else if (input.equals("help")) { //mulige kommandoer

                System.out.println("help");
                System.out.println("sluk");
                System.out.println("new");

            } else{
                System.out.println("Prøv igen. Brug help kommandoen."); // Bruges hvis de laver fx en stavefejl.

            }
        }
    }
}