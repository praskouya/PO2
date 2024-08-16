import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Vectors {

    public static class WektoryRoznejDlugosciException extends Exception {
        private int length1;
        private int length2;

        public WektoryRoznejDlugosciException(int length1, int length2) {
            this.length1 = length1;
            this.length2 = length2;
        }

        public String getMessage() {
            return "Długość pierwszego wektora to " + length1 + ", a drugiego to " + length2;
        }
    }

public static void vectorGenerator(ArrayList<Integer> vector) {
    vector.clear();
    Scanner scanner = new Scanner(System.in);
    String line = scanner.nextLine();
    String[] tokens = line.split("\\s+");
    for (String token : tokens) {
        try {
            vector.add(Integer.parseInt(token));
        } catch (NumberFormatException e) {
            System.out.println("Wektor to same liczby, glupku");
        }
    }
}


    public static boolean sumVectors(ArrayList<Integer> v1, ArrayList<Integer> v2) throws WektoryRoznejDlugosciException {
        if (v1.size() != v2.size()) throw new WektoryRoznejDlugosciException(v1.size(), v2.size());

        ArrayList<Integer> v3 = new ArrayList<>();
        for (int i = 0; i < v1.size(); i++) {
            v3.add(v1.get(i) + v2.get(i));
        }

        try (FileWriter writer = new FileWriter("vector.txt")) {
            for (int i = 0; i < v3.size(); i++) {
                writer.write(v3.get(i).toString() + " ");
            }
        } catch (IOException e) {
            throw new RuntimeException("Błąd podczas zapisu do pliku: " + e.getMessage());
        }

        System.out.println("Dobra robota :) Wynik dodawania wektorów zapisano do pliku vector.txt");
        return true;
    }

    public static void main(String[] args) {
        ArrayList<Integer> v1 = new ArrayList<>();
        ArrayList<Integer> v2 = new ArrayList<>();
        boolean equals = false;

        while (!equals) {
            System.out.println("Coz to za przystojniak! Prosze podac pierwszy wektor:");
            vectorGenerator(v1);
            System.out.println("No i teraz podaj drugi wektor:");
            vectorGenerator(v2);

            System.out.println("To jest pierwszy wektor: " + v1);
            System.out.println("A tak wyglada drugi: " + v2);

            try {
                equals = sumVectors(v1, v2);
            } catch (WektoryRoznejDlugosciException e) {
				System.out.println(e.getMessage());
                System.out.println("Długości wektorów musa sie zgadzac, podaj wektory jeszcze raz");
                
            }
        }
    }
}
