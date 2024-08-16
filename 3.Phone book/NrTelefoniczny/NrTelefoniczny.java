package NrTelefoniczny;
import java.util.Scanner;
public class NrTelefoniczny implements Comparable<NrTelefoniczny> {
    private int nrKierunkowy;
    private int nrTelefonu;

    public NrTelefoniczny(int nrKier, int nrTel) {
        if (nrKier < 0 || nrTel < 0)
            throw new IllegalArgumentException("Incorrect phone number!");
        if (nrKier > 99 || nrTel < 100000000 || nrTel > 999999999)
            throw new IllegalArgumentException("Incorrect phone number!");

        nrKierunkowy = nrKier;
        nrTelefonu = nrTel;
    }

    public NrTelefoniczny() {
        nrKierunkowy = 0;
        nrTelefonu = 0;
    }

    @Override
    public String toString() {
        String result = "tel: ";
        if (nrKierunkowy != 0)
            result += "+" + nrKierunkowy + " ";
        result += (nrTelefonu / 1000000) + "-" + (nrTelefonu / 1000 % 1000) + "-" + nrTelefonu % 1000;
        return result;
    }

    public String opis() {
        return toString();
    }

    public boolean equals(NrTelefoniczny nr2) {
        if (nrKierunkowy == nr2.nrKierunkowy && nrTelefonu == nr2.nrTelefonu)
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(NrTelefoniczny nr2) {
        if (nrKierunkowy == nr2.nrKierunkowy) {
            return nrTelefonu - nr2.nrTelefonu;
        } else
            return nrKierunkowy - nr2.nrKierunkowy;
    }

    public void enter() {
     Scanner scanner= new Scanner(System.in);
        System.out.print("Podaj kod/ wcisnij enter jak nie znasz: ");
        try {
            String entry = scanner.nextLine();
            if(entry.length()!=0)
                nrKierunkowy = Integer.parseInt(entry);
            System.out.print("Podaj numer telefonu: ");
            entry = scanner.nextLine();
            nrTelefonu = Integer.parseInt(entry);

        } catch (Exception e) {
            throw new IllegalArgumentException("Nieprawidlowy numer!");
        }
    }

}
