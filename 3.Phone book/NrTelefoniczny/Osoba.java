package NrTelefoniczny;
import java.util.Scanner;
public class Osoba extends Wpis {
    private String imie;
    private String nazwisko;
    private Adres adres;
    private  NrTelefoniczny nrTel;
    public Osoba(){
        imie=null;
        nazwisko=null;
        adres=null;
        nrTel=null;
    }
    public Osoba(String in_imie,String in_nazwisko,Adres in_adres,NrTelefoniczny in_nrTel){
        imie=in_imie;
        nazwisko=in_nazwisko;
        adres=in_adres;
        nrTel=in_nrTel;
    }
    public String opis(){
        return imie+" "+nazwisko+"\n\t"+adres.getUlica()+"\n\t"+adres.getMiasto()+"\n\t"+nrTel;
    }
    public String toString(){
        return opis();
    }
    public NrTelefoniczny getNrTel(){
        return nrTel;
    }
    
    public Adres getAdres() {
    return adres;
}

    public void enter(){
        System.out.print("Podaj imie i nazwisko: ");
        String name=scanner.nextLine();
        String splited_name[]=name.split(" ");
        if(splited_name.length<2)
            throw new IllegalArgumentException("Cos nie poszlo!");
        imie=splited_name[0];
        nazwisko=splited_name[1];
        adres= new Adres();
        adres.enter();
        nrTel=new NrTelefoniczny();
        nrTel.enter();

    }
}

