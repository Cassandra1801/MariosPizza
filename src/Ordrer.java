import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Ordrer {

    // Default constructor
    public Ordrer() {
    }

    //Variabler
    private String pizzaer;
    private int afhentning;
    private LocalTime ordreLavet;
    private String navn;
    private boolean pizzaKlar;


    // Constructer
    public Ordrer(String pizzaer, int afhentning, LocalTime ordreLavet, String navn, boolean pizzaKlar) {

        this.pizzaer = pizzaer;
        this.afhentning = afhentning;
        this.ordreLavet = ordreLavet;
        this.navn = navn;
        this.pizzaKlar = pizzaKlar;
    }


    //getPizza
    public String getPizzaer() {
        return pizzaer;
    }

    public int getAfhentning() {
        return afhentning;
    }

    public LocalTime getOrdreLavet() {
        return ordreLavet;
    }

    public String getNavn() {
        return navn;
    }

    public boolean getPizzaKlar() {
        return pizzaKlar;
    }

    public LocalTime getOrdreFaerdig() {
        return ordreLavet.plusMinutes(afhentning);
    }

    //Differencen mellem hvornår den skal være færdig og den reelle tid
    public Duration getDifference() {
        return Duration.between(LocalTime.now(), getOrdreFaerdig());
    }

    @Override
    public String toString() {
        return pizzaer + " (" + afhentning + " min) | bestilt: " + ordreLavet + " | navn: " + navn + " | status: " + (pizzaKlar ? "HENTET" : "AFVENTER");
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