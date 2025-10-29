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
    private double totalPris;
    private double pris;
    private double prisIkkeKlar = 0.0;

    public void setPizzaKlar(boolean pizzaKlar) {
        this.pizzaKlar = pizzaKlar;
    }

    // Constructer
    public Ordrer(String pizzaer, int afhentning, LocalTime ordreLavet, String navn, boolean pizzaKlar, double totalPris, double prisIkkeKlar) {
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

    public double getPris() {
        return pris;
    }

    public double getTotalPris() {
        if (pizzaKlar) {
            return pris * pizzaer;
        } else {
            return prisIkkeKlar;
        }
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