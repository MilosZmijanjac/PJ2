package Kompozicija.Vagon;

import Konstanta.Konstanta;
import Teritorija.Element;

import java.io.File;

public class Vagon extends Element {
    private int duzina;
    private String oznaka;

    public Vagon(int duzina, String oznaka) {
        super(Konstanta.slikeFolder+ File.separator+"vagon.png");
        this.duzina = duzina;
        this.oznaka = oznaka;
    }
    public Vagon(String imagePath,int duzina, String oznaka) {
        super(imagePath);
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
