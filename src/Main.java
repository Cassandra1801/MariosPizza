import java.util.ArrayList;
import java.util.List;



public class Main {
    public static boolean aabent = true;

    public static void main(String[] args) {

        while (aabent == true )  {

            List<Pizza> pizza =  new ArrayList<Pizza>();
            pizza = FileUtil.readPizzaFromFile();

            for (Pizza p : pizza) {
            System.out.println();
            }
        }
    }
}