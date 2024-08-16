import NrTelefoniczny.*;

import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;
public class lab7 {
    public static void main(String[] args) {
        TreeMap<NrTelefoniczny,Wpis> map = new TreeMap<NrTelefoniczny,Wpis> ();
        HashSet<String> uniqueAddresses = new HashSet<>(); 
        try{
            NrTelefoniczny tel1 = new NrTelefoniczny(48, 226952900);
            Adres adres1 = new Adres("Wiejska", 10, "Warszawa", "00-902");
            Osoba pasza = new Osoba("Andrzej", "Duda", adres1, tel1);

            NrTelefoniczny tel2 = new NrTelefoniczny(48, 501805928);
            Adres adres2 = new Adres("Politechniki", 9, "Lodz", "93-590");
            Osoba andrzej = new Osoba("Praskouya", "Horbach", adres2, tel2);

            NrTelefoniczny tel3 = new NrTelefoniczny(48, 111222333);
            Adres adres3 = new Adres("Bohdana Stefanowskiego", 18, "Lodz", "90-001");
            Firma firma1 = new Firma("Weeia dla geja", adres3, tel3);

            NrTelefoniczny tel4 = new NrTelefoniczny(48, 426313603);
            Adres adres4 = new Adres("Piotrkowska", 103, "Lodz", "90-423");
            Firma firma2 = new Firma("Urzad wojewodzki ds spraw obywatelskich i cudzoziemcow", adres4, tel4);
            
            NrTelefoniczny tel5 = new NrTelefoniczny(48, 938431365);
            Adres adres5 = new Adres("Piotrkowska", 7, "Lodz", "90-423");
            Firma firma3 = new Firma("Testowy", adres5, tel5);

            map.put(pasza.getNrTel(), pasza);
            map.put(andrzej.getNrTel(), andrzej);
            map.put(firma1.getNrTel(), firma1);
            map.put(firma2.getNrTel(), firma2);
            map.put(firma3.getNrTel(), firma3);
            
            System.out.println("Wybierz opcję:");
        System.out.println("1. Dodaj osobę");
        System.out.println("2. Dodaj firmę");
        Scanner scanner = new Scanner(System.in);
        int opcja = scanner.nextInt();
        scanner.nextLine(); 
        
        if (opcja == 1) {
            Osoba osoba = new Osoba();
            osoba.enter();
            map.put(osoba.getNrTel(), osoba);
        } else if (opcja == 2) {
            Firma firma = new Firma();
            firma.enter();
            map.put(firma.getNrTel(), firma);
        } else {
            System.out.println("Nieprawidłowa opcja.");
        }

  
            System.out.println("Zawartość ksiazki przed sprawdzeniem unikalności ulic:");
            wyswietlKsiazkeTelefoniczna(map);

            Set<Entry<NrTelefoniczny, Wpis>> entrySet = map.entrySet();
Iterator<Entry<NrTelefoniczny, Wpis>> iterator = entrySet.iterator();
while (iterator.hasNext()) {
    Entry<NrTelefoniczny, Wpis> entry = iterator.next();
    Wpis wpis = entry.getValue();
    Adres adres = null;
    if (wpis instanceof Firma) {
        adres = ((Firma) wpis).getAdres();
    } else if (wpis instanceof Osoba) {
        adres = ((Osoba) wpis).getAdres();
    }

    if (adres != null) {
        if (uniqueAddresses.contains(adres.getUlica_bez())) {
            iterator.remove(); 
            System.out.println("Taka ulica juz jest w ksiazce telefonicznej :(\n" + adres);
        } else {
            uniqueAddresses.add(adres.getUlica_bez()); 
        }
    }
}


      
            System.out.println("\nKsiazeczka bez powtorek:");
            wyswietlKsiazkeTelefoniczna(map);
        } catch(IllegalArgumentException e){
           System.out.println(e.getMessage());
        }
    }

    private static void wyswietlKsiazkeTelefoniczna(TreeMap<NrTelefoniczny, Wpis> map) {
        Set<Entry<NrTelefoniczny, Wpis>> entrySet = map.entrySet();
        Iterator<Entry<NrTelefoniczny, Wpis>> it = entrySet.iterator();
        while (it.hasNext()) {
            Entry<NrTelefoniczny, Wpis> me = it.next();
            System.out.println(me.getValue() + "\n");
        }
    }
}

