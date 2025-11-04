import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.nio.file.Files;
import java.nio.file.Path;



public class Main {

    /// === !!!! SKAL VÆRE TRUE !!!! ======= Boolean Som Holder Systemet Kørende =======================================
    public static boolean aabent = true;                                                                                //Boolean for at holde while loop kørende


    /// GetMaanedNu, GetDato, GetTid, Formateret ======================================================================
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

    /// Laver Mapperne For Måneden Hvis Den Ikke Eksisterer ============================================================
    public static File MappeForMaaned() {
        String baseFolder = "Logs";                                                                                     //Lav filstruktur; logs/2025-10/log_2025-10-27.txt
        String folderPath = baseFolder + "/" + getMaanedNu();
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder;
    }


    /// Laver Dagens Fil Hvis Den Ikke Allerede Eksisterer =============================================================
    public static File DagensFil(File monthFolder) {
        File f = new File(monthFolder, "log_" + getDato() + ".txt");                                                    //Create File object
        try {
            if (f.createNewFile()) {                                                                                    //Try to create the file
                System.out.println("File created: " + f.getPath());
            } else {
                System.out.println("File already exists." + f.getPath());
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();                                                                                        //Print error details
        }
        return f;
    }

    /// Ændrer Identificerede Ordrers Færdig Status Til True ===========================================================
    public static void updateOrdreFaerdig(String navn) throws IOException {
        Path filePath = Path.of("Logs/" + getMaanedNu() + "/" + "log_" + getDato() + ".txt");                           //Finder den rigtige fil og kalder locationen filePath
        List<String> lines = Files.readAllLines(filePath);

        String targetName = navn;       // data[3]
        String newValue = "true";       // new value for data[4]
        int targetFieldIndex = 4;       // zero-based index (so 4 = 5th field)

        for (int i = 0; i < lines.size(); i++) {
            String[] fields = lines.get(i).split("_");                                                                  //Læser hver linje, og deler den op med _.

            // !!!! Vi har en fejl, da man kan have flere af samme navne !!!!
            if (fields[3].equalsIgnoreCase(targetName)) {                                                               //Leder i data[3], for at finde det der tilsvarer input
                fields[targetFieldIndex] = newValue;                                                                    //Opdaterer data[4] som er booleanen
                String updatedLine = String.join("_", fields);                                                          //Linjen opdaterer teksten
                lines.set(i, updatedLine);                                                                              //Opdaterer linje til updatedLine
                break;                                                                                                  //Stopper når den finder den rigtige ordre
            }
        }


        Files.write(filePath, lines);                                                                                   //Write updated lines back to the file, Indsætter det i filen
        System.out.println("Ordre er blevet opdateret: " + targetName);
    }


    /// En Pause Funktion For At Kontrollerer Output ====================================================================
    public static void trykEnKnap() {
        Scanner scAny = new Scanner(System.in);
        System.out.println("");
        System.out.println("Tryk på en knap for at fortsætte.");
        scAny.nextLine();

    }


    /// Danner Object  =======================================================================
    public static class PizzaBestilling {
        String pizzaTekst;
        double totalPris;

        public PizzaBestilling(String pizzaTekst, double totalPris) {
            this.pizzaTekst = pizzaTekst;
            this.totalPris = totalPris;
        }

    }

    /// Identificerer En Pizza =========================================================================================
    public static PizzaMenuObj findPizzaById(int id, List<PizzaMenuObj> menu) {
        for (PizzaMenuObj p : menu) {
            if (p.getPizzaID() == id) {
                return p;
            }
        }
        return null;                                                                                                    //Hvis ikke fundet
    }


    /// Tæller Antal Pizzaer ===========================================================================================
    public static PizzaBestilling findFlerePizzaer(String input, List<PizzaMenuObj> menu) {
        String[] dele = input.split("\\s+");                                                                            //Split mellemrum
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


    /// Beregner Omsætningen For Dagen =================================================================================
    public static double BeregnOmsaetning(String pathen) {
        String path = pathen;
        List<Ordrer> ordrerList = FileUtilOrders.readOrdreFromFile(path);

        double totalForDag = 0;
        for (Ordrer ordre : ordrerList) {
            if (ordre.getPizzaKlar()) {
                totalForDag += ordre.getTotalPris();                                                                    //Lægger indtjeningen sammen
            }
        }
        return totalForDag;
    }

    /// Transformerer Et Dato Input til En Path ========================================================================
    public static String IndtastetDato() {
        Scanner scanner = new Scanner(System.in);


        System.out.print("Indtast ønskede dato: (DD-MM-YYYY): ");                                                       //Læser Input
        String dateStr = scanner.nextLine();

        String[] parts = dateStr.split("-");                                                                            //Deler Den Op
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];

        String path = "Logs/" + year + "-" + month + "/log_" + dateStr + ".txt";                                        //Danner Path

        return path;
    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// MAIN KLASSEN ///////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void main(String[] args) throws IOException {

        /// Laver Filen For Dagen Hvis Den Ikke Eksisterer =============================================================
        File monthFolder = MappeForMaaned();                                                                            //Mappe for månederne
        File dagensFil = DagensFil(monthFolder);                                                                        //Filer for dags dato


        Scanner sc = new Scanner(System.in);                                                                            //Scanner der tager input til start menu

        while (aabent == true) {                                                                                        //Loop der gør at man altid returnerer til startmenu og udskriver pizzamenuen

            ///Udskriver Menuen ========================================================================================
            List<PizzaMenuObj> pizzaMenuObj = new ArrayList<>();                                                        //Udskriver pizza menu
            pizzaMenuObj = FileUtil.readPizzaFromFile();

            System.out.println("");
            System.out.println("===   Menu   ======================================================");
            for (PizzaMenuObj p : pizzaMenuObj) {
                System.out.println(p);
            }
            System.out.println("");

            ///Printer Sorterede Ordrer ================================================================================
            String path = "Logs/" + Main.getMaanedNu() + "/log_" + Main.getDato() + ".txt";
            List<Ordrer> ordrerList = FileUtilOrders.readOrdreFromFile(path);
            ordrerList.sort(Comparator.comparing(Ordrer::getDifference).reversed());                                    //Sorterer

            System.out.println("===   Aktive Ordrer   =============================================");
            for (Ordrer o : ordrerList) {
                System.out.println(o.getDifference() + " | " + o.getPizzaer() + " | " + o.getNavn());
            }
            System.out.println("");


            ///Starten På Output til StartMenu =========================================================================
            System.out.println(" ");

            System.out.println("INDTAST EN KOMMANDO - Brug 'Hjælp' for at se muligheder - Tryk enter for at opdatere.");

            String input = sc.nextLine();                                                                               //Inputtet lagres som en string variabel, for ellers vil equals ikke læse det


            ///Command Line Mulighederne ===============================================================================

            if (input.equalsIgnoreCase("Ny")) {                                                                         //Bruges så man kan skrive med stort og små bogstav

                System.out.println("Laver ny ordre");                                                                   //Laver en ny ordre

                System.out.println("Bestilling (indtast pizzanummer adskilt med mellemrum): ");                         //Input for bestillingen
                String pizzaInput = sc.nextLine();

                PizzaBestilling bestilling = findFlerePizzaer(pizzaInput, pizzaMenuObj);
                System.out.println("Du har valgt: " + bestilling.pizzaTekst);                                           //Viser hvilken pizza der blev valgt

                System.out.println("Tid:");                                                                             //Input for tiden på bestillingen
                int tid = Integer.parseInt(sc.nextLine());

                System.out.println("Navn:");                                                                            //Input for navn på bestillingen
                String navn = sc.nextLine();

                String linje = bestilling.pizzaTekst + "_" + tid + "_" + getTid() + "_" +
                                                    navn + "_" + "false" + "_" + bestilling.totalPris;                  //Finder og formaterer pizzaerne
                FileUtilOrders.appendLine(dagensFil, linje);                                                            //Skriver i dags dato filen

                System.out.println("Skriv Færdig, når ordren er færdig\n");

                trykEnKnap();                                                                                           //Tryk på en knap for at fortsætte (for at se output)

            } else if (input.equalsIgnoreCase("Færdig")) {

                System.out.println("Indtast navn på den ordre, der er færdig: ");
                String navn = sc.nextLine();
                updateOrdreFaerdig(navn);                                                                               //Kalder funktionen som opdaterer ordren via input

                trykEnKnap();

            } else if (input.equalsIgnoreCase("Sluk")) {

                System.out.println("Slukker");
                aabent = false;

            } else if (input.equalsIgnoreCase("Omsætning")) {

                String inputPath = IndtastetDato();

                List<Ordrer> datoList = FileUtilOrders.readOrdreFromFile(inputPath);

                FileUtilOrders.visSolgtePizzaerPrType(datoList);                                                        //Udskriver statistikken/omsætnignen for dagen
                System.out.println("Omsætning for i dag: " + BeregnOmsaetning(inputPath) + " kr.");

                trykEnKnap();

            } else if (input.equalsIgnoreCase("Hjælp")) {                                                               //Mulige kommandoer

                System.out.println("Ny (ordre)\nFærdig (ordre)\nSluk (program)\nOmsætning (for i dag)\nHjælp");

                trykEnKnap();

            } else if (input.equalsIgnoreCase("")) {                                                                    //For at skippe tomt input

            } else {
                System.out.println("Prøv igen. Brug Hjælp kommandoen.");                                                //Bruges hvis de fx laver en stavefejl.

                trykEnKnap();
            }
        }
    }
}