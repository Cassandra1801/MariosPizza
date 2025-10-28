import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Ordrer {
    // Default constructor
    public Ordrer() {
    }

    //Variabler
    private int pizzaer;
    private int afhentning;
    private LocalTime ordreLavet;
    private int ordreID;
    private boolean pizzaKlar;


    // Constructer
    public Ordrer(int pizzaer, int afhentning, LocalTime ordreLavet, int ordreID,
                  boolean pizzaKlar) {

        this.pizzaer = pizzaer;
        this.afhentning = afhentning;
        this.ordreLavet = ordreLavet;
        this.ordreID = ordreID;
        this.pizzaKlar = pizzaKlar;
    }


    //getPizza
    public int getPizzaer() {
        return pizzaer;
    }

    public int getAfhentning() {
        return afhentning;
    }

    public LocalTime getOrdreLavet() {
        return ordreLavet;
    }

    public int getOrdreID() {
        return ordreID;
    }

    public boolean getPizzaKlar() {
        return pizzaKlar;
    }

    public LocalTime getOrdreFaerdig() {
        LocalTime ordreFaerdig = getOrdreLavet().plusMinutes(getAfhentning());
        return ordreFaerdig;
    }

    //Differencen mellem hvornår den skal være færdig og den reelle tid
    public Duration Difference() {
        Duration forskel = Duration.between(getOrdreFaerdig(), getOrdreLavet());
        return forskel;
    }
}

    /*

    Ordrer objekt

    Det data som er i filen
    - pizzaer
    - tid til afhentning
    - tidspunktet ordren er lavet
    - tidspunktet ordren skal være færdig
    - ordreID (navn)
    - boolean for om ordren er færdig eller ikke (true = færdig)

    data som skal findes via givet data
    - differencen mellem tidspunktet den skal være færdig og det reelle klokkeslæt

     */