package Kompozicija;

import IstorijaKretanja.IstorijaKretanja;
import Konstanta.Konstanta;
import Teritorija.*;
import javafx.application.Platform;
import javafx.scene.Group;

import java.io.File;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Kompozicija  implements Runnable {

    private ArrayList<Element> elementiKompozicije;
    private String odredisnaStanica;
    private String pocetnaStanica;
    private Lokacija pocetnaLokacija;
    private Deque<Lokacija> lokacije=new LinkedList<>();
    private Smjer smjer=Smjer.GORE;
    private Pravac trenutniPravac;
    private int X,Y;
    private Group parent;
    boolean stop=true;
    private static int count=0;
    public static boolean stopThread;
    private int usporenje;
    private int brzina;
    private int brojPredjenihPolja;
    private int step;
    private Iterator<Lokacija> iterator;
    public IstorijaKretanja istorijaKretanja;
    public static FileHandler handler;
    static{
        try {
            handler=new FileHandler(Konstanta.logFolder+ File.separator+"Kompozicija.log");
            Logger.getLogger(Kompozicija.class.getName()).addHandler(handler);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Kompozicija(Group group,  ArrayList<Element> elementi,String pocetnaStanica,String odrediste,int brzina){
        this.stopThread=true;
        this.stop=true;
        this.usporenje=1;
        this.brojPredjenihPolja=0;
        this.brzina=(500-brzina)>0?(500-brzina):500;
        this.elementiKompozicije =elementi;
        this.pocetnaStanica=pocetnaStanica;
        this.odredisnaStanica=odrediste;
        this.istorijaKretanja=new IstorijaKretanja("kompozicija"+count++);
        istorijaKretanja.dodajStanicu(pocetnaStanica);
        parent=group;

        Platform.runLater(() -> {
            for(Element e: elementiKompozicije) {
                e.setVisible(false);
                parent.getChildren().add(e);
            }
        });
    }

    public void setUsporenje(int usporenje){
        this.usporenje=usporenje;
  }
    public int getBrzina(){return brzina;}
    public Lokacija getPocetnaLokacija(){
        return pocetnaLokacija;
    }
    public void setPocetnaLokacija(Lokacija lokacija){
        pocetnaLokacija=lokacija;
   }
    public Smjer getSmjer(){return smjer;}
    public void setSmjer(Smjer smjer){
        this.smjer=smjer;
   }
    public String getOdredisnaStanica() {
        return odredisnaStanica;
    }
    public void setOdredisnaStanica(String odredisnaStanica) {
        this.odredisnaStanica = odredisnaStanica;
    }

    @Override
    public void run() {
    Y= pocetnaLokacija.getPozicijaY();
    X= pocetnaLokacija.getPozicijaX();

            while (stopThread) {

                if(lokacije.size()>= elementiKompozicije.size()) {
                    if (!Mapa.mapa[lokacije.peek().getPozicijaY()][lokacije.peek().getPozicijaX()].isStanica()) {
                        Mapa.postaviElement(lokacije.peek(), null);
                    }
                    lokacije.remove();
                }
                if(stop) {
                    lokacije.add(new Lokacija(X, Y));
                    istorijaKretanja.dodajLokaciju(lokacije.peek());
                }

                if(lokacije.size()<elementiKompozicije.size()&&stop) {
                    iterator = lokacije.descendingIterator();
                    step=elementiKompozicije.size()-1;
                }
                else {
                    iterator = lokacije.iterator();
                    step=0;
                }
                while(iterator.hasNext())
                {
                    Lokacija l=iterator.next();
                    int finalS = step;
                    Platform.runLater(() -> {
                        elementiKompozicije.get(finalS).setTranslateX(l.getPozicijaX()*20);
                        elementiKompozicije.get(finalS).setTranslateY(l.getPozicijaY()*20);
                        elementiKompozicije.get(finalS).setVisible(true);
                        if(Mapa.mapa[l.getPozicijaY()][l.getPozicijaX()].isStanica()) {
                            elementiKompozicije.get(finalS).setVisible(false);
                        }
                    });
                    if(lokacije.size()<elementiKompozicije.size()&&stop)
                        step--;
                    else
                        step++;
                }


                if (Mapa.mapa[Y][X].isStanica()) {
                    stop=false;
                    lokacije.poll();
                    if(lokacije.isEmpty()) {
                        stop=true;
                        try {
                            istorijaKretanja.dodajStanicu(((Stanica) Mapa.mapa[Y][X].getElement()).getOznaka());
                            ((Stanica) Mapa.mapa[Y][X].getElement()).staviKompozicijuURedCekanja(this);
                            ((Stanica) Mapa.mapa[Y][X].getElement()).oslobodiDionicu(Mapa.mapa[Y][X].getLokacija());
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Kompozicija.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                        }
                        break;
                    }
                }else {
                    Mapa.postaviElement(lokacije.getFirst(), elementiKompozicije.get(elementiKompozicije.size() - 1));
                }

                if(stop){
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
                    }

                    if (Mapa.mapa[Y][X].getPravac() == Pravac.VERTIKALAN) {
                        if (smjer == Smjer.DOLJE) {
                            Y++;
                        } else {
                            Y--;
                        }
                        trenutniPravac = Pravac.VERTIKALAN;
                    } else {
                        if (smjer == Smjer.NAPRIJED) {
                            X++;
                        } else {
                            X--;
                        }
                        trenutniPravac = Pravac.HORIZONTALAN;
                    }
                }
                brojPredjenihPolja++;
                try {
                    Thread.sleep(brzina*usporenje);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Kompozicija.class.getName()).log(Level.WARNING, ex.fillInStackTrace().toString());
                }
            }
    }
}
