package Kompozicija.Vagon;

import Konstanta.Konstanta;

import java.io.File;

public class VagonPutnickiSaLezajima extends VagonPutnicki{
    private int brojMjesta;
    private static int count=0;

    public VagonPutnickiSaLezajima(int duzina, String oznaka, int brojMjesta) {
        super(Konstanta.slikeFolder+ File.separator+"VPL.png",duzina, oznaka);
        this.brojMjesta = brojMjesta;
    }
    public VagonPutnickiSaLezajima() {
        super(Konstanta.slikeFolder+ File.separator+"VPL.png",count+10, "VPL"+count);
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
        return "VagonPutnickiSaLezajima{" +
                "brojMjesta=" + brojMjesta +
                "} " + super.toString();
    }
}
