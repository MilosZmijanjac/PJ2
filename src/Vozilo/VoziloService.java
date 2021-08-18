package Vozilo;

import Teritorija.Lokacija;
import Teritorija.Mapa;
import Teritorija.Put;
import Teritorija.Smjer;
import javafx.application.Platform;
import javafx.scene.Group;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VoziloService extends TimerTask {
    private Group group;
    private Put lijeviPut;
    private Put desniPut;
    private Put srednjiPut;
    private int brojKreiranihVozilaSrednjiPut;
    private int brojKreiranihVozilaDesniPut;
    private int brojKreiranihVozilaLijeviPut;
    private Random random;
    private String putanjaDoConfigFajla;

    static {
        try
        {
            Logger.getLogger(VoziloService.class.getName()).addHandler(new FileHandler("logs/VoziloService.log"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public VoziloService(Group group,String putanjadoFajla) {
        this.group=group;
        lijeviPut= new Put();
        desniPut= new Put();
        srednjiPut=new Put();
        brojKreiranihVozilaSrednjiPut=0;
        brojKreiranihVozilaDesniPut=0;
        brojKreiranihVozilaLijeviPut=0;
        this.putanjaDoConfigFajla=putanjadoFajla;
        random=new Random();
    }
    private void ucitajParametre(){
        try (InputStream inputStream = new FileInputStream(putanjaDoConfigFajla)) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(inputStream);

            lijeviPut.setBrojVozila(
                    Math.max(Integer.parseInt(prop.getProperty("lijeviPut.brojVozila")), lijeviPut.getBrojVozila())
            );
            lijeviPut.setMaxBrzina(Integer.parseInt(prop.getProperty("lijeviPut.maxBrzina")));

            desniPut.setBrojVozila(
                    Math.max(Integer.parseInt(prop.getProperty("desniPut.brojVozila")), desniPut.getBrojVozila())
            );
            desniPut.setMaxBrzina(Integer.parseInt(prop.getProperty("desniPut.maxBrzina")));

            srednjiPut.setBrojVozila(
                    Math.max(Integer.parseInt(prop.getProperty("srednjiPut.brojVozila")), srednjiPut.getBrojVozila())
            );
            srednjiPut.setMaxBrzina(Integer.parseInt(prop.getProperty("srednjiPut.maxBrzina")));

        } catch (IOException ex) {
            Logger.getLogger(VoziloService.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
        }
    }
    private void kreirajVozila(){
        if(lijeviPut.getBrojVozila()>brojKreiranihVozilaLijeviPut){
            if(random.nextBoolean()){//radi nasumicnog smjera
                brojKreiranihVozilaLijeviPut=kreirajJednoVozilo(lijeviPut, new Lokacija(0, 21), Smjer.NAPRIJED,brojKreiranihVozilaLijeviPut);
            }else{
                brojKreiranihVozilaLijeviPut= kreirajJednoVozilo(lijeviPut, new Lokacija(8, 29), Smjer.GORE,brojKreiranihVozilaLijeviPut);
            }
        }
        if(desniPut.getBrojVozila()>brojKreiranihVozilaDesniPut){
            if(random.nextBoolean()){//radi nasumicnog smjera
                brojKreiranihVozilaDesniPut=kreirajJednoVozilo(desniPut, new Lokacija(29, 20), Smjer.NAZAD,brojKreiranihVozilaDesniPut);
            }else{
                brojKreiranihVozilaDesniPut= kreirajJednoVozilo(desniPut, new Lokacija(22, 29), Smjer.GORE,brojKreiranihVozilaDesniPut);
            }
        }
        if(srednjiPut.getBrojVozila()>brojKreiranihVozilaSrednjiPut){
            if(random.nextBoolean()){//radi nasumicnog smjera
                brojKreiranihVozilaSrednjiPut=kreirajJednoVozilo(srednjiPut, new Lokacija(13, 0), Smjer.DOLJE,brojKreiranihVozilaSrednjiPut);
            }else{
                brojKreiranihVozilaSrednjiPut= kreirajJednoVozilo(srednjiPut, new Lokacija(14, 29), Smjer.GORE,brojKreiranihVozilaSrednjiPut);
            }
        }
    }
    private int kreirajJednoVozilo(Put put,Lokacija lokacija,Smjer smjer,int brojKreiranihVozila) {
        if(Mapa.mapa[lokacija.getPozicijaY()][lokacija.getPozicijaX()].getElement()==null)
        {
            double brzina= (Math.random()*(put.getMaxBrzina()-500+1)) +500;
            Vozilo vozilo = (random.nextBoolean() ? new Automobil(lokacija, smjer, brzina) : new Kamion(lokacija, smjer, brzina));
            Platform.runLater(() -> group.getChildren().add(vozilo));
            new Thread(vozilo).start();
            brojKreiranihVozila++;
        }
        return brojKreiranihVozila;
    }

    @Override
    public void run() {
    ucitajParametre();
    kreirajVozila();
    }
}
