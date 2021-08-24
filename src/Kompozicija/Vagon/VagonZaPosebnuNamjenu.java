package Kompozicija.Vagon;

import Konstanta.Konstanta;

import java.io.File;

public class VagonZaPosebnuNamjenu extends Vagon{
    private static int count=0;
    public VagonZaPosebnuNamjenu() {
        super(Konstanta.slikeFolder+ File.separator+"VPN.png",count+10, "VPN"+count);
    }
}
