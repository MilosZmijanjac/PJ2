package Teritorija;

import java.util.ArrayList;

public class Put {
    private int brojVozila;
    private int maxBrzina;
    public static ArrayList<Lokacija>zauzeteLokacije;

    public Put() {
        brojVozila=0;
        maxBrzina=0;
        zauzeteLokacije=new ArrayList<>();
    }
    public static synchronized void zauzmiLokaciju(Lokacija lokacija){
        zauzeteLokacije.add(lokacija);
    }
    public static synchronized void oslobodiLokaciju(Lokacija lokacija){
        for (int i = 0; i < zauzeteLokacije.size(); i++) {
            if(zauzeteLokacije.get(i).odgovaraKoordinatama(lokacija.getPozicijaX(), lokacija.getPozicijaY()))
                zauzeteLokacije.remove(i);
        }
    }
    public static synchronized boolean isSlobodnaLokacija(Lokacija lokacija){
        for (Lokacija value : zauzeteLokacije) {
            if (value.odgovaraKoordinatama(lokacija.getPozicijaX(), lokacija.getPozicijaY()))
                return false;
        }
        return true;
    }


    public int getBrojVozila() {
        return brojVozila;
    }

    public void setBrojVozila(int brojVozila) {
        this.brojVozila = brojVozila;
    }

    public int getMaxBrzina() {
        return maxBrzina;
    }

    public void setMaxBrzina(int maxBrzina) {
        this.maxBrzina = maxBrzina;
    }
}
