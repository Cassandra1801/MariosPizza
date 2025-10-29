import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.IllegalCharsetNameException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;



public class Main {


    public static boolean aabent = true;                                                                                //Boolean for at holde while loop kørende

    public static DateTimeFormatter MAANED_FMT = DateTimeFormatter.ofPattern("yyyy-MM");
    public static DateTimeFormatter DATO_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter TID_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static String getMaanedNu() {
        return LocalDate.now().format(MAANED_FMT);
    }

    public static String getDato() {
        return LocalDate.now().format(DATO_FMT);
    }

    public static String getTid() {
        return LocalDateTime.now().format(TID_FMT);
    }

    public static File MappeForMaaned() {
                                                                                                                        //Lav filstruktur; logs/2025-10/log_2025-10-27.txt
        String baseFolder = "Logs";
        String folderPath = baseFolder + "/" + getMaanedNu();
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }

    public static File DagensFil(File monthFolder) {
        File f = new File(monthFolder, "log_" + getDato() + ".txt");                                               // Create File object
        try {
            if (f.createNewFile()) {                                                                                    // Try to create the file
                System.out.println("File created: " + f.getPath());
            } else {
                System.out.println("File already exists." + f.getPath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();                                                                                        // Print error details
        }
        return f;
    }


    public static void appendLine(File file, String line) {                                                             //Append en linje til dagens fil
        try (FileWriter myWriter = new FileWriter(file, true)) {
            myWriter.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        File monthFolder = MappeForMaaned();                                                                            //Mappe for månederne
        File dagensFil = DagensFil(monthFolder);                                                                        //Filer for dags dato

        Scanner sc = new Scanner(System.in);                                                                            //Scanner der tager input til start menu



        while (aabent == true) {                                                                                        //Loop der gør at man altid returnerer til startmenu og udskriver pizza menuen


            List<PizzaMenuObj> pizzaMenuObj = new ArrayList<>();                                                        //Udskriver pizza menu
            pizzaMenuObj = FileUtil.readPizzaFromFile();

            for (PizzaMenuObj p : pizzaMenuObj) {
                System.out.println(p);
            }

            List<Ordrer> ordrerList = new ArrayList<>();
            ordrerList = FileUtilOrders.readOrdreFromFile();                                                            //Udskriver ordrer ud
            System.out.println("\nAktive ordrer:");
            for (Ordrer o : ordrerList) {
                System.out.println(o);
            }


            System.out.println(" ");

            System.out.println("Indtast New, for ny ordre. Help for andet. SLuk for sluk systemet. Done for færdig ordre.");

            String input = sc.nextLine();                                                                               //Inputtet lagres som en string variabel, for ellers vil equals ikke læse det


                                                                                                                        //Start menu elementer for hvert if statement
            if (input.equalsIgnoreCase("New")) {                                                            //Bruger equals til at sammenligne, ikke sige det er det (there is a difference somehow)
                System.out.println("Laver ny ordre");                                                                   //Laver en ny ordre


                System.out.println("Bestilling (indtast pizzanummer adskilt med mellemrum): ");                         //Input for bestillingen
                String pizzaInput = sc.nextLine();


                String hvilkePizza = findFlerePizzaer(pizzaInput, pizzaMenuObj);
                System.out.println("Du har valgt: " + hvilkePizza);                                                     //Viser hvilken pizza der blev valgt


                System.out.println("Tid:");                                                                             //Input for tiden på bestillingen
                int tid = Integer.parseInt(sc.nextLine());


                System.out.println("Navn:");                                                                            //Input for navn på bestillingen
                String navn = sc.nextLine();


                String linje = hvilkePizza + "_" + tid + "_" + getTid() + "_" + navn + "_" + "false";                   //Finder og formaterer pizzaerne
                appendLine(dagensFil, linje);                                                                           //Skriver i dags dato filen

                System.out.println("Skrive Done, når ordren er lavet\n");
                System.out.println(" ");


            } else if (input.equalsIgnoreCase("Done")) {
                System.out.println("Indtast navn på den ordre, der er færdig: ");
                String navn = sc.nextLine();


            } else if (input.equalsIgnoreCase("Sluk")) {
                System.out.println("Slukker");
                aabent = false;


            } else if (input.equalsIgnoreCase("Help")) {                                                    //Mulige kommandoer


                System.out.println("Help");
                System.out.println("Sluk");
                System.out.println("New");

            } else {
                System.out.println("Prøv igen. Brug Help kommandoen.");                                                 //Bruges hvis de laver fx en stavefejl.

            }
        }
    }

    public static PizzaMenuObj findPizzaById(int id, List<PizzaMenuObj> menu) {
        for (PizzaMenuObj p : menu) {
            if (p.getPizzaID() == id) {
                return p;
            }
        }
        return null;                                                                                                    //Hvis ikke fundet
    }

    public static String findFlerePizzaer(String input, List<PizzaMenuObj> menu) {
        String[] dele = input.split("\\s+");                                                                      //Split mellemrum
        Map<Integer, Integer> antalMap = new HashMap<>();

        for (String d : dele) {                                                                                         //Tæller hvor mange gange hver pizzanummer optræder
            try {
                int antal;
                int id;

                if (d.contains("x")) {                                                                                  //Hvis brugeren skriver fx "3x2" betyder det 3 stk. af pizza nr. 2
                    String[] parts = d.split("x");
                    antal = Integer.parseInt(parts[0]);
                    id = Integer.parseInt(parts[1]);
                } else {
                    antal = 1;                                                                                          //Ellers bare ét stk. af nummeret
                    id = Integer.parseInt(d);
                }

                antalMap.put(id, antalMap.getOrDefault(id, 0) + antal);
            } catch (Exception e) {
                System.out.println("Ugyldigt input: " + d);
            }
        }

        StringBuilder sb = new StringBuilder();                                                                         //Bygger resultattekst
        double totalPris = 0;

        for (Map.Entry<Integer, Integer> entry : antalMap.entrySet()) {
            int id = entry.getKey();
            int antal = entry.getValue();

            PizzaMenuObj p = findPizzaById(id, menu);                                                                   //Find pizza fra menukort
            if (p != null) {
                double pizzaPris = p.getTotalPris();
                totalPris += pizzaPris * antal;                                                                         //Læg prisen sammen
                sb.append(antal).append("x").append(p.getNavn()).append(" ");
            }
        }
        System.out.println("Samlet pris: " + totalPris + " kr.");                                                       //Udskriver total
        return sb.toString().trim();
    }

    public static void ordreKlar(File dagensFil, String navn) {
        try {
            List<String> linjer = new ArrayList<>();
            Scanner scanner = new Scanner(dagensFil);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                                                                                                                        // Hvis linjen indeholder kundens navn og stadig er false → sæt den til true
                if (line.contains("_" + navn + "_false")) {
                    line = line.replace("_" + navn + "_false", "_" + navn + "_true");
                    System.out.println("Ordren for " + navn + " er nu markeret som færdig!");
                }
                linjer.add(line);
            }
                                                                                                                        // Skriv filen tilbage (overskriv hele filen)
            FileWriter writer = new FileWriter(dagensFil, false);
            for (String l : linjer) {
                writer.write(l + System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("Fejl ved opdatering af ordre: " + e.getMessage());
        }
    }
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
