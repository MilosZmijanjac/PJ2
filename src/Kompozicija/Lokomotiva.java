package Kompozicija;

import Teritorija.Element;

public class Lokomotiva extends Element {
    private String oznaka;
    private int snaga;
    private Pogon pogon;
    private TipLokomotive tip;
    private static int count=0;

    public Lokomotiva( Pogon pogon, TipLokomotive tip) {
        super("Train.png");
        this.oznaka = "oznaka:"+count;
        this.snaga = count+100;
        this.pogon = pogon;
        this.tip = tip;
        count++;
    }


}
