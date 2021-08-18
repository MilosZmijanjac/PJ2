package Kompozicija.Vagon;

public class VagonZaPosebnuNamjenu extends Vagon{
    private static int count=0;
    public VagonZaPosebnuNamjenu() {
        super(count+10, "VPN"+count);
    }
}
