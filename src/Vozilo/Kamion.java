package Vozilo;

import Teritorija.Lokacija;
import Teritorija.Smjer;

import java.util.Random;

public class Kamion extends Vozilo {
    private int nosivost;

    public Kamion(Lokacija lokacija , Smjer smjer, double brzina ) {
        super("truck.png",lokacija,smjer,brzina);
        this.nosivost = new Random().nextInt(1000)+500;
    }

}
