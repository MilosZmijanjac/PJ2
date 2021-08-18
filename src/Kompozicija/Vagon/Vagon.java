package Kompozicija.Vagon;

import Teritorija.Element;

public class Vagon extends Element {
    private int duzina;
    private String oznaka;

    public Vagon(int duzina, String oznaka) {
        super("Wagon.png");
        this.duzina = duzina;
        this.oznaka = oznaka;
    }

    public int getDuzina() {
        return duzina;
    }

    public void setDuzina(int duzina) {
        this.duzina = duzina;
    }

    public String getOznaka() {
        return oznaka;
    }

    public void setOznaka(String oznaka) {
        this.oznaka = oznaka;
    }

    @Override
    public String toString() {
        return "Vagon{" +
                "duzina=" + duzina +
                ", oznaka='" + oznaka + '\'' +
                '}';
    }
}
