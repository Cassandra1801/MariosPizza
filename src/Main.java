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

    //Opdaterer boolean for om en bestilling er klar til true, for et bestemt kunde ID
    public static void updateOrdreFaerdig(String navn) throws IOException {
        Path filePath = Path.of("Logs/" + getMaanedNu() + "/" + "log_" + getDato() + ".txt");                       //Finder den rigtige fil og kalder locationen filePath
        List<String> lines = Files.readAllLines(filePath);

        String targetName = navn;       // data[3]
        String newValue = "true";       // new value for data[4]
        int targetFieldIndex = 4;       // zero-based index (so 4 = 5th field)

        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("_");                                                            //Læser hver linje, og deler den op med _.

            // !!!! Vi har en fejl, da man kan have flere af samme navne !!!!
            if (fields[3].equalsIgnoreCase(targetName)) {                                                               //Leder i data[3], for at finde det der tilsvarer input
                fields[targetFieldIndex] = newValue;                                                                    //Opdaterer data[4] som er booleanen
                String updatedLine = String.join("_", fields);                                                  //laver teksten linjen skal opdateres til
                lines.set(i, updatedLine);                                                                              //Opdaterer linje i til updatedLine
                break;                                                                                                  //Stopper om den finder den rigtige ordre
            }
        }

        // Write updated lines back to the file
        Files.write(filePath, lines);                                                                                   //Indsætter det i filen
        System.out.println("Ordre er blevet opdateret: " + targetName);
    }

    public static void trykEnKnap() {
        Scanner scAny = new Scanner(System.in);
        System.out.println("");
        System.out.println("Tryk en knap for at fortsætte.");
        scAny.nextLine();
    }

    public static class PizzaBestilling {
        String pizzaTekst;
        double totalPris;

        public PizzaBestilling(String pizzaTekst, double totalPris) {
            this.pizzaTekst = pizzaTekst;
            this.totalPris = totalPris;
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

    public static PizzaBestilling findFlerePizzaer(String input, List<PizzaMenuObj> menu) {
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
        return new PizzaBestilling(sb.toString().trim(), totalPris);
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


    /// MAIN KLASSEN //////////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args) throws IOException {

        File monthFolder = MappeForMaaned();                                                                            //Mappe for månederne
        File dagensFil = DagensFil(monthFolder);                                                                        //Filer for dags dato

        Scanner sc = new Scanner(System.in);                                                                            //Scanner der tager input til start menu


        while (aabent == true) {                                                                                        //Loop der gør at man altid returnerer til startmenu og udskriver pizza menuen

            ///Udskriver Menuen ======================================================================
            List<PizzaMenuObj> pizzaMenuObj = new ArrayList<>();                                                        //Udskriver pizza menu
            pizzaMenuObj = FileUtil.readPizzaFromFile();

            for (PizzaMenuObj p : pizzaMenuObj) {
                System.out.println(p);
            }

            ///Printer Sorterede Ordrer ==============================================================
            List<Ordrer> ordrerList = FileUtilOrders.readOrdreFromFile();
            ordrerList.sort(Comparator.comparing(Ordrer::getDifference).reversed());                                    //Sorterer

            // Holder på indtjening
            double totalForDag = 0;
            for (Ordrer ordre : ordrerList) {
                if (ordre.getPizzaKlar()) {
                    // Lægger indtjeningen sammen
                    totalForDag += ordre.getTotalPris();
                }
            }

            // Printer samlede resultat
            System.out.println("Omsætningen for i dag: " + totalForDag);

            System.out.println("\nAktive ordrer:");
            for (Ordrer o : ordrerList) {
                System.out.println(o.getDifference() + " | " + o.getPizzaer() + " | " + o.getNavn());
            }

            for (Ordrer o : ordrerList) {
                // getOrdreFaerdig returnere timer og minuter
                // Fejl i DATA
                LocalTime test = o.getOrdreFaerdig();
                int minutes = test.getMinute();

                // Når fejlen er rettet, kan i bare bruge følgende:
                LocalTime.now().minusMinutes(minutes);
                System.out.println(minutes);
                // !! Jeg formoder at i stadig kan bruge følgende:
                Duration remaining = Duration.between(LocalTime.now(), o.getOrdreFaerdig());

                if (!o.getPizzaKlar() && remaining.isNegative()) {                                                      //Tiden er gået
                    System.out.println("Ordre klar: " + o.getNavn() + " | " + o.getPizzaer());
                } else {
                    System.out.println(o.getDifference() + " | " + o.getNavn() + " | " + o.getPizzaer());
                }
            }

            System.out.println(" ");

            System.out.println("INDTAST EN KOMMANDO - Brug 'Help' for at se muligheder - Tryk enter for at opdatere.");

            String input = sc.nextLine();                                                                               //Inputtet lagres som en string variabel, for ellers vil equals ikke læse det


            ///Command Line Mulighederne ============================================================

            if (input.equalsIgnoreCase("New")) {                                                            //Bruger equals til at sammenligne, ikke sige det er det (there is a difference somehow)
                System.out.println("Laver ny ordre");                                                                   //Laver en ny ordre


                System.out.println("Bestilling (indtast pizzanummer adskilt med mellemrum): ");                         //Input for bestillingen
                String pizzaInput = sc.nextLine();

                PizzaBestilling bestilling = findFlerePizzaer(pizzaInput, pizzaMenuObj);
                System.out.println("Du har valgt: " + bestilling.pizzaTekst);                                           //Viser hvilken pizza der blev valgt


                System.out.println("Tid:");                                                                             //Input for tiden på bestillingen
                int tid = Integer.parseInt(sc.nextLine());


                System.out.println("Navn:");                                                                            //Input for navn på bestillingen
                String navn = sc.nextLine();


                String linje = bestilling.pizzaTekst + "_" + tid + "_" + getTid() + "_" + navn + "_" + "false" + "_" + bestilling.totalPris;                   //Finder og formaterer pizzaerne
                appendLine(dagensFil, linje);                                                                           //Skriver i dags dato filen

                System.out.println("Skriv Done, når ordren er færdig\n");

                trykEnKnap();                                                                                           //Tryk en knap for at forstætte (for at se output)

            } else if (input.equalsIgnoreCase("Done")) {
                System.out.println("Indtast navn på den ordre, der er færdig: ");
                String navn = sc.nextLine();
                updateOrdreFaerdig(navn);                                                                               //Kalder funktionen som opdaterer ordren via input

                trykEnKnap();                                                                                           //Tryk en knap for at forstætte (for at se output)

            } else if (input.equalsIgnoreCase("Sluk")) {
                System.out.println("Slukker");
                aabent = false;

            } else if (input.equalsIgnoreCase("Omsætning")) {
                System.out.println(totalForDag);


            } else if (input.equalsIgnoreCase("Help")) {                                                    //Mulige kommandoer
                System.out.println("New\nDone\nSluk\nOmsætning\nHelp");

                trykEnKnap();                                                                                           //Tryk en knap for at forstætte (for at se output)

            } else if (input.equalsIgnoreCase("")) {

            } else {
                System.out.println("Prøv igen. Brug Help kommandoen.");                                                 //Bruges hvis de laver fx en stavefejl.

                trykEnKnap();                                                                                           //Tryk en knap for at forstætte (for at se output)

            }
        }
    }
}