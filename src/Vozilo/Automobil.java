package Vozilo;

import Teritorija.Lokacija;
import Teritorija.Smjer;

import java.util.Random;

public class Automobil extends Vozilo {
    private int brojVrata;

    public Automobil(Lokacija lokacija ,Smjer smjer,double brzina ) {
        super("car.png",lokacija,smjer,brzina);
        this.brojVrata = new Random().nextInt(3)+2;
    }

}
