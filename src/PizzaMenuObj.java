public class PizzaMenuObj {

    //Default constructor
    public PizzaMenuObj() {}

    //Variabler
    private int pizzaID;
    private String navn;
    private String toppings;
    private double pris;

    //PizzaMenu constructor
    public PizzaMenuObj (int pizzaID, String navn, String toppings, double pris){
        this.pizzaID = pizzaID;
        this.navn = navn;
        this.toppings = toppings;
        this.pris = pris;
    }

    //GetPizzaID
    public int getPizzaID() {
        return pizzaID;
    }

    //GetNavn
    public String getNavn() {
        return navn;
    }

    //GetToppings
    public String getToppings() {
        return toppings;
    }

    //GetPris
    public double getPris() {
        return pris;
    }

    //ToString af menuen
    @Override
    public String toString() {
        return pizzaID + ". " + navn + ": " + toppings + " | " + pris + " kr.";
    }
}

