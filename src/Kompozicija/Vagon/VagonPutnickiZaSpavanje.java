package Kompozicija.Vagon;

public class VagonPutnickiZaSpavanje extends VagonPutnicki{
    private static int count=0;
    public VagonPutnickiZaSpavanje() {
        super(count+10, "VPZS"+count);
        count++;
    }
}
