package Kompozicija.Vagon;

import Konstanta.Konstanta;

import java.io.File;

public class VagonPutnickiSaSjedistima extends VagonPutnicki {
    private int brojMjesta;
    private static int count=0;

    public VagonPutnickiSaSjedistima() {
        super(Konstanta.slikeFolder+ File.separator+"VPSS.png",count+10, "VPSS"+count);
        this.brojMjesta = count+20;
        count++;
    }

    public int getBrojMjesta() {
        return brojMjesta;
    }

    public void setBrojMjesta(int brojMjesta) {
        this.brojMjesta = brojMjesta;
    }

    @Override
    public String toString() {
        return "VagonPutnickiSaSjedistima{" +
                "brojMjesta=" + brojMjesta +
                "} " + super.toString();
    }
}
