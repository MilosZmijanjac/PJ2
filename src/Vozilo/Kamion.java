package Vozilo;

import Konstanta.Konstanta;
import Teritorija.Lokacija;
import Teritorija.Smjer;

import java.io.File;
import java.util.Random;

public class Kamion extends Vozilo {
    private int nosivost;

    public Kamion(Lokacija lokacija , Smjer smjer, double brzina ) {
        super(Konstanta.slikeFolder+ File.separator+"truck.png",lokacija,smjer,brzina);
        this.nosivost = new Random().nextInt(1000)+500;
    }

}
