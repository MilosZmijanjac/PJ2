package Kompozicija.Vagon;

public class VagonPutnickiRestoran extends VagonPutnicki {
    private String opis;
    private static int count=0;

    public VagonPutnickiRestoran() {
        super(count+10, "VPR"+count);
        this.opis = "opis"+count;
        count++;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    @Override
    public String toString() {
        return "VagonPutnickiRestoran{" +
                "opis='" + opis + '\'' +
                "} " + super.toString();
    }
}
