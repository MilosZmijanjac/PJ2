package Teritorija;

import IstorijaKretanja.IstorijaKretanja;
import Kompozicija.Kompozicija;
import Konstanta.Konstanta;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stanica extends Element implements Runnable{
    private String oznaka;
    final LinkedBlockingQueue<Kompozicija>[] redoviCekanja;
    private Kompozicija trenutnaKompozicija;
    public static boolean stopThread;
    Object lock=new Object();
    public static FileHandler handler;
    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"Stanica.log");
            Logger.getLogger(Stanica.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Stanica(String oznaka,int brojUlaza) {
        super(Konstanta.slikeFolder+File.separator+"StanicaA.png");
        this.oznaka = oznaka;
        redoviCekanja= new LinkedBlockingQueue[brojUlaza];
        for(int i =0;i<brojUlaza;i++)
            redoviCekanja[i]= new LinkedBlockingQueue<Kompozicija>();
        stopThread=true;
    }

    public String getOznaka() {
        return oznaka;
    }
    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }
    private int getBrzinuDionice(Lokacija lokacija) {
        if(lokacija.odgovaraKoordinatama(6,6)||lokacija.odgovaraKoordinatama(27,2))
            return Mapa.AB.getBrzinaDionice();
        else if(lokacija.odgovaraKoordinatama(6,7)||lokacija.odgovaraKoordinatama(19,12))
            return Mapa.BC.getBrzinaDionice();
        else if(lokacija.odgovaraKoordinatama(19,13)||lokacija.odgovaraKoordinatama(26,1))
            return Mapa.CD.getBrzinaDionice();
        else if(lokacija.odgovaraKoordinatama(20,13)||lokacija.odgovaraKoordinatama(26,25))
            return Mapa.CE.getBrzinaDionice();
        else return 500;
    }
    public void staviKompozicijuURedCekanja(Kompozicija kompozicija) throws InterruptedException {
        synchronized (redoviCekanja){
            switch (oznaka) {
                case "A":
                case "D":
                case "E":
                    redoviCekanja[0].put(kompozicija);
                    break;
                case "B":
                    if ("A".equals(kompozicija.getOdredisnaStanica())) {
                        redoviCekanja[0].put(kompozicija);
                    } else {
                        redoviCekanja[1].put(kompozicija);
                    }
                    break;
                case "C":
                    if ("B".equals(kompozicija.getOdredisnaStanica()) || "A".equals(kompozicija.getOdredisnaStanica())) {
                        redoviCekanja[0].put(kompozicija);
                    } else if ("D".equals(kompozicija.getOdredisnaStanica())) {
                        redoviCekanja[1].put(kompozicija);
                    } else {
                        redoviCekanja[2].put(kompozicija);
                    }
                    break;
            }
        }
    }
    private void pripremiZaKretanje(Kompozicija kompozicija){
        switch (oznaka){
            case "A":
               kompozicija.setPocetnaLokacija(new Lokacija(2,26));
               kompozicija.setSmjer(Smjer.GORE);
               break;
            case "B":
                if("A".equals(kompozicija.getOdredisnaStanica())) {
                    kompozicija.setPocetnaLokacija(new Lokacija(5,6));
                    kompozicija.setSmjer(Smjer.NAZAD);
                }
                else{
                    kompozicija.setPocetnaLokacija(new Lokacija(8,6));
                    kompozicija.setSmjer(Smjer.NAPRIJED);
                }
                break;
            case "C":
                if("B".equals(kompozicija.getOdredisnaStanica())||"A".equals(kompozicija.getOdredisnaStanica())){
                    kompozicija.setPocetnaLokacija(new Lokacija(19,11));
                    kompozicija.setSmjer(Smjer.GORE);
                }
                else if("D".equals(kompozicija.getOdredisnaStanica())){
                    kompozicija.setPocetnaLokacija(new  Lokacija(21,12));
                    kompozicija.setSmjer(Smjer.NAPRIJED);
                }
                else{
                    kompozicija.setPocetnaLokacija(new  Lokacija(20,14));
                    kompozicija.setSmjer(Smjer.DOLJE);
                }
                break;
            case "D":
                kompozicija.setPocetnaLokacija(new Lokacija(25,1));
                kompozicija.setSmjer(Smjer.NAZAD);
                break;
            case "E":
                kompozicija.setPocetnaLokacija(new Lokacija(26,24));
                kompozicija.setSmjer(Smjer.GORE);
                break;
        }
    }
    public void  zauzmiDionicu(){
        if(trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(2,26)||
                trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(5,6)){
            Mapa.AB.zauzmi();
            Mapa.AB.setSmjerDionice(trenutnaKompozicija.getSmjer());
            Mapa.AB.setBrzinaDionice(trenutnaKompozicija.getBrzina());
        }
        else if(trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(8,6)||
                trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(19,11)){
            Mapa.BC.zauzmi();
            Mapa.BC.setSmjerDionice(trenutnaKompozicija.getSmjer());
            Mapa.BC.setBrzinaDionice(trenutnaKompozicija.getBrzina());
        }
        else if(trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(21,12)||
                trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(25,1)){
            Mapa.CD.zauzmi();
            Mapa.CD.setSmjerDionice(trenutnaKompozicija.getSmjer());
            Mapa.CD.setBrzinaDionice(trenutnaKompozicija.getBrzina());
        }
        else if(trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(20,14)||
                trenutnaKompozicija.getPocetnaLokacija().odgovaraKoordinatama(26,24)){
            Mapa.CE.zauzmi();
            Mapa.CE.setSmjerDionice(trenutnaKompozicija.getSmjer());
            Mapa.CE.setBrzinaDionice(trenutnaKompozicija.getBrzina());
        }
    }
    public void oslobodiDionicu(Lokacija lokacija){
      if(lokacija.odgovaraKoordinatama(6,6)||lokacija.odgovaraKoordinatama(2,27))
          Mapa.AB.oslobodi();
      else if(lokacija.odgovaraKoordinatama(7,6)||lokacija.odgovaraKoordinatama(19,12))
          Mapa.BC.oslobodi();
      else if(lokacija.odgovaraKoordinatama(20,12)||lokacija.odgovaraKoordinatama(26,1))
          Mapa.CD.oslobodi();
      else if(lokacija.odgovaraKoordinatama(20,13)||lokacija.odgovaraKoordinatama(26,25))
          Mapa.CE.oslobodi();
    }

    @Override
    public void run() {
        synchronized (lock) {
            while (stopThread) {
                for (LinkedBlockingQueue<Kompozicija> kompozicijas : redoviCekanja) {
                    if (kompozicijas.size() != 0) {
                        trenutnaKompozicija = (Kompozicija) kompozicijas.peek();
                        pripremiZaKretanje(trenutnaKompozicija);
                        if (oznaka.equals(trenutnaKompozicija.getOdredisnaStanica())) {
                            try {
                                new IstorijaKretanja().serializuj(trenutnaKompozicija.istorijaKretanja);
                                kompozicijas.take();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Stanica.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                            }
                        } else {
                            if (Mapa.isSlobodnaDionica(trenutnaKompozicija.getPocetnaLokacija())) {
                                zauzmiDionicu();
                                try {
                                    trenutnaKompozicija.setUsporenje(1);
                                    kompozicijas.take();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Stanica.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                                }

                                new Thread(trenutnaKompozicija).start();

                            } else if (((Mapa.smjerDionice(trenutnaKompozicija.getPocetnaLokacija()))== trenutnaKompozicija.getSmjer())&&Mapa.getElement(trenutnaKompozicija.getPocetnaLokacija())==null)
                                     {
                                if(getBrzinuDionice(trenutnaKompozicija.getPocetnaLokacija())<trenutnaKompozicija.getBrzina())
                                    trenutnaKompozicija.setUsporenje(trenutnaKompozicija.getBrzina()/
                                            getBrzinuDionice(trenutnaKompozicija.getPocetnaLokacija()));
                                zauzmiDionicu();
                                try {
                                    kompozicijas.take();
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(Stanica.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                                }
                                new Thread(trenutnaKompozicija).start();

                            }
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Stanica.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }
        }
    }
}
