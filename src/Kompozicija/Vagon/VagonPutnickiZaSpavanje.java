package Kompozicija.Vagon;

import Konstanta.Konstanta;

import java.io.File;

public class VagonPutnickiZaSpavanje extends VagonPutnicki{
    private static int count=0;
    public VagonPutnickiZaSpavanje() {
        super(Konstanta.slikeFolder+ File.separator+"VPZS.png",count+10, "VPZS"+count);
        count++;
    }
}
