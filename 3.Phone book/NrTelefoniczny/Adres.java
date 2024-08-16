package NrTelefoniczny;

public class Adres extends Wpis {
    String miasto;
    String ulica;
    int nr_budynku;
    String kod_pocztowy;

    public Adres() {
        miasto = null;
        ulica = null;
        nr_budynku = 0;
        kod_pocztowy = null;
    }

    public Adres(String in_ulica, int in_nr_budynku, String in_miasto, String in_kod_pocztowy) {
        if (in_nr_budynku <= 0)
            throw new IllegalArgumentException("Incorrect house number!");
        miasto = in_miasto;
        ulica = in_ulica;
        nr_budynku = in_nr_budynku;
        String temp[] = in_kod_pocztowy.split("-");
        if (temp.length != 2)
            throw new IllegalArgumentException("Incorrect postcode!");

        int temp_kod_pocztowy = 0;
        try {
            temp_kod_pocztowy = Integer.parseInt(temp[0]) * 1000;
            if (temp_kod_pocztowy == 0)
                temp_kod_pocztowy = 10000;
            temp_kod_pocztowy += Integer.parseInt(temp[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect postcode!");
        }
        if (temp_kod_pocztowy < 10000 || temp_kod_pocztowy > 99999)
            throw new IllegalArgumentException("Incorrect postcode!");
        kod_pocztowy = in_kod_pocztowy;
    }

    @Override
    public String toString() {
        return "ul." + ulica + " " + nr_budynku + "\n" + miasto + " " + kod_pocztowy;
    }

    public String opis() {
        return toString();
    }

    public String getUlica() {
        return "ul." + ulica + " " + nr_budynku;
    }
    
    public String getUlica_bez() {
		return ulica;
		}

    public String getMiasto() {
        return miasto + " " + kod_pocztowy;
    }
    
  

    public void enter() {
        System.out.print("Podaj ulice i numer domu: ");
        String entry = scanner.nextLine();
        String splitted_entry[] = entry.split(" ");
        if (splitted_entry.length < 2)
            throw new IllegalArgumentException("Incorrect adress!");

        ulica = splitted_entry[0];
        for (int i = 1; i < splitted_entry.length - 1; i++) {
            ulica += " " + splitted_entry[i];
        }
        try {
          
            nr_budynku = Integer.parseInt(splitted_entry[splitted_entry.length - 1]);
            if (nr_budynku <= 0)
                throw new IllegalArgumentException("Incorrect house number!");
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect adress!");
        }

        System.out.print("Podaj miasto i kod pocztowy: ");
        entry = scanner.nextLine();
        splitted_entry = entry.split(" ");
        if (splitted_entry.length < 2)
            throw new IllegalArgumentException("Incorrect adress!");
        miasto = splitted_entry[0];
        for (int i = 1; i < splitted_entry.length - 1; i++) {
            miasto += " " + splitted_entry[i];
        }
        String temp[] = splitted_entry[splitted_entry.length - 1].split("-");
        if (temp.length < 2)
            throw new IllegalArgumentException("Incorrect postcode!");
        int temp_kod_pocztowy = 0;
        try {
            temp_kod_pocztowy = Integer.parseInt(temp[0]) * 1000;
            if (temp_kod_pocztowy == 0)
                temp_kod_pocztowy = 10000;
            temp_kod_pocztowy += Integer.parseInt(temp[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Incorrect postcode!");
        }
        if (temp_kod_pocztowy < 10000 || temp_kod_pocztowy > 99999)
            throw new IllegalArgumentException("Incorrect postcode!");
        kod_pocztowy = splitted_entry[splitted_entry.length - 1];
    }
}
