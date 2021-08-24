package Vozilo;

import Konstanta.Konstanta;
import Teritorija.Lokacija;
import Teritorija.Smjer;

import java.io.File;
import java.util.Random;

public class Automobil extends Vozilo {
    private int brojVrata;

    public Automobil(Lokacija lokacija ,Smjer smjer,double brzina ) {
        super(Konstanta.slikeFolder+ File.separator+"car.png",lokacija,smjer,brzina);
        this.brojVrata = new Random().nextInt(3)+2;
    }

}
