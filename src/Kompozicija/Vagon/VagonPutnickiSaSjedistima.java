package Kompozicija.Vagon;

public class VagonPutnickiSaSjedistima extends VagonPutnicki {
    private int brojMjesta;
    private static int count=0;

    public VagonPutnickiSaSjedistima() {
        super(count+10, "VPSS"+count);
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
