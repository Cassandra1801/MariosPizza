import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

public class Ordrer {

    //Default constructor
    public Ordrer() {
    }

    //Variabler
    private String pizzaer;
    private int afhentning;
    private LocalTime ordreLavet;
    private String navn;
    private boolean pizzaKlar;
    private double totalPris;


    //Constructer
    public Ordrer(String pizzaer, int afhentning, LocalTime ordreLavet, String navn, boolean pizzaKlar, double totalPris, double prisIkkeKlar) {
        this.pizzaer = pizzaer;
        this.afhentning = afhentning;
        this.ordreLavet = ordreLavet;
        this.navn = navn;
        this.pizzaKlar = pizzaKlar;
    }

    //Gettere
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

    //Færdig tidspunkt
    public LocalTime getOrdreFaerdig() {
        return ordreLavet.plusMinutes(afhentning);
    }

    //Differencen mellem hvornår den skal være færdig og den reelle tid i minutter
    public String getDifference() {
        Duration diff = Duration.between(LocalTime.now(), getOrdreFaerdig());
        long minutes = diff.toMinutes();
        return String.format("%d min", minutes);
    }

    @Override
    public String toString() {
        return pizzaer + " (" + afhentning + " min) | bestilt: " + ordreLavet + " | navn: " + navn + " | status: " + (pizzaKlar ? "HENTET" : "AFVENTER");
    }
}