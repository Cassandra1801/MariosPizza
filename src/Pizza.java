public class Pizza {

    public Pizza(){}

    private int pizzaID;
    private String navn;
    String toppings;
    double pris;

    public Pizza (int pizzaID, String navn, String toppings, double pris){

        this.pizzaID = pizzaID;
        this.navn = navn;
        this.toppings = toppings;
        this.pris = pris;
    }
public int getPizzaID() {
        return pizzaID;
}
public String getNavn() {
        return navn;
}
public String getToppings() {
        return toppings;
}
public double getPris() {
        return pris;
}
    @Override
    public String toString() {
        return pizzaID + "." + navn + ":" + toppings + "|" + pris;
    }
}

