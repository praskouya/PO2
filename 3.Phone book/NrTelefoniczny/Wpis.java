package NrTelefoniczny;
import java.util.Scanner;
abstract public class Wpis{
    protected static final Scanner scanner= new Scanner(System.in);
    abstract String opis();
    abstract void enter();
}