import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        List<Pizza> pizza =  new ArrayList<Pizza>();
        pizza = FileUtil.readPizzaFromFile();

        for (Pizza p : pizza){
            System.out.print(p);
        }

    }
}