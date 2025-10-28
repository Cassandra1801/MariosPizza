import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Main {

    //Boolean for at holde while loop kørende
    public static boolean aabent = true;

    public static DateTimeFormatter MAANED_FMT = DateTimeFormatter.ofPattern("yyyy-MM");
    public static DateTimeFormatter DATO_FMT = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public static DateTimeFormatter TID_FMT = DateTimeFormatter.ofPattern("HH:mm");

    public static String getMaanedNu()  {return LocalDate.now().format(MAANED_FMT);}
    public static String getDato()      {return LocalDate.now().format(DATO_FMT);}
    public static String getTid()       {return LocalDateTime.now().format(TID_FMT);}

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
        File f = new File(monthFolder, "log_" + getDato() + ".txt");                                  // Create File object
        try {
            if (f.createNewFile()) {                                                                   // Try to create the file
                System.out.println("File created: " + f.getPath());
            } else {
                System.out.println("File already exists." + f.getPath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();                                                                          // Print error details
        }
        return f;
    }

    //Append en linje til dagens fil
    public static void appendLine(File file, String line) {
        try (FileWriter myWriter = new FileWriter(file , true)) {
            myWriter.write(line + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //Mappe + dagsfil
        File monthFolder = MappeForMaaned();
        File dagensFil = DagensFil(monthFolder);

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

            System.out.println("Indtast New, for ny ordre. Sluk for at systemet slukker og Help for andet.");

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
                System.out.println("Bestilling: "); //I format ANTALxTYPE, separer typer med et mellemrum
                hvilkePizza = scPizza.nextLine();

                //input for tiden på bestillingen
                System.out.println("Tid:");
                tid = Integer.parseInt(scTid.nextLine());

                //input for navn på bestillingen
                System.out.println("Navn:");
                navn = scNavn.nextLine();

                System.out.println(hvilkePizza + " " + tid + " min " + navn + " ");

                //Skriver i filen
                String linje = hvilkePizza + "_" + tid + "_" + getTid() + "_" + navn + "_" + "false";
                appendLine(dagensFil, linje);

                System.out.println("Ordre er lavet");
                System.out.println(" ");


                //Tilføjer til filen fra input

            } else if (input.equals("Sluk")) {
                System.out.println("Slukker");
                aabent = false;

            } else if (input.equals("Help")) {                                                         //mulige kommandoer

                System.out.println("Help");
                System.out.println("Sluk");
                System.out.println("New");

            } else{
                System.out.println("Prøv igen. Brug help kommandoen.");                              // Bruges hvis de laver fx en stavefejl.

            }
        }
    }
}
