package NrTelefoniczny;

public class Firma extends Wpis {
    private String nazwa;
    private Adres adres;
    private NrTelefoniczny nrTel;
    public Firma(){
        nazwa=null;
        adres=null;
        nrTel=null;
    }
    public Firma(String in_nazwa,Adres in_adres,NrTelefoniczny in_nrTel){
        nazwa=in_nazwa;
        adres=in_adres;
        nrTel=in_nrTel;
    }

    public String opis() {
        return nazwa+"\n\t"+ adres.getUlica()+"\n\t"+adres.getMiasto()+"\n\t" + nrTel;
    }
    public NrTelefoniczny getNrTel(){
        return nrTel;
    }
    
    public Adres getAdres() {
    return adres;
}


    @Override
    public String toString() {
        return opis();
    }

    public void enter() {
        System.out.println("Podaj nazwe firmy: ");
        nazwa = scanner.nextLine();
        adres = new Adres();
        adres.enter();
        nrTel = new NrTelefoniczny();
        nrTel.enter();
    }
}
