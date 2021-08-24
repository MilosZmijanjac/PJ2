package Vozilo;

import Konstanta.Konstanta;
import Teritorija.*;
import javafx.application.Platform;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vozilo extends Element implements Runnable {
    static int count=0;
    private String marka;
    private String model;
    private String godiste;
    private Pravac trenutniPravac;
    private double brzina;
    private int X,Y;
    private Lokacija pocetnaLokacija;
    private Smjer smjer;
    public static boolean stopThread;
    public static FileHandler handler;
    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"Vozilo.log");
            Logger.getLogger(Vozilo.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Vozilo(String putanjaSlike,Lokacija pocetnaLokacija,Smjer smjer,double brzina){
        super(putanjaSlike);
        this.pocetnaLokacija=pocetnaLokacija;
        this.smjer=smjer;
        this.brzina=brzina;
        this.godiste=String.valueOf(1990+count);
        this.marka="marka"+count;
        model="model"+ count++;
        stopThread=true;
    }
    @Override
    public void run() {
        Y=pocetnaLokacija.getPozicijaY();
        X= pocetnaLokacija.getPozicijaX();
        int prethodnoX,prethodnoY;
        prethodnoX = X;
        prethodnoY = Y;

            while (stopThread) {
                //Ako je prelaz i rampa je zatvorena
                if ((Mapa.mapa[Y][X].isPrelaz() && !Mapa.isSlobodnaDionica(Mapa.mapa[Y][X].getLokacija()))) {
                    continue;
                }
                //Ako je vozilo na polju ispred
                if (!Put.isSlobodnaLokacija(new Lokacija(X,Y))) {
                        continue;
                } else {
                    Put.oslobodiLokaciju(new Lokacija(prethodnoX,prethodnoY));
                    Put.zauzmiLokaciju(new Lokacija(X,Y));
                }
                //Translira sliku
                 translirajXoY(X,Y);

                if(Mapa.mapa[Y][X].isKraj()) {
                    try {
                        Thread.sleep((long) brzina);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                    }
                    Platform.runLater(() -> setVisible(false));
                    Put.oslobodiLokaciju(new Lokacija(X,Y));
                    break;
                }

                prethodnoX = X;
                prethodnoY = Y;

                sljedeciKorak();

                try {
                    Thread.sleep((long) (brzina));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Vozilo.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }
        }

    private void sljedeciKorak(){
        if (Mapa.mapa[Y][X].isSkretnica()) {
            trenutniPravac = Mapa.mapa[Y][X].promjeniPravac(trenutniPravac);

            if (trenutniPravac == Pravac.VERTIKALAN) {
                if (Mapa.mapa[Y + 1][X] == null) {
                    smjer = Smjer.GORE;
                } else {
                    smjer = Smjer.DOLJE;
                }
            } else {
                if (Mapa.mapa[Y][X + 1] == null) {
                    smjer = Smjer.NAZAD;
                } else {
                    smjer = Smjer.NAPRIJED;
                }
            }
        }else trenutniPravac=Mapa.mapa[Y][X].getPravac();

        if (Mapa.mapa[Y][X].isPrelaz())
            trenutniPravac=Pravac.promjeniPravac(trenutniPravac);

        if (trenutniPravac== Pravac.VERTIKALAN) {
            if (smjer == Smjer.DOLJE)
                Y++;
            else
                Y--;
        } else {
            if (smjer == Smjer.NAPRIJED)
                X++;
            else
                X--;
        }
    }
}

