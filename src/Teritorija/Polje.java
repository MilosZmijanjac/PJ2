package Teritorija;

public class Polje {
    private Lokacija lokacija;
    private Pravac pravac;
    private Element element;
    private boolean isSkretnica=false;
    private boolean isStanica=false;
    private boolean isPrelaz=false;
    private boolean isKraj=false;


    public Polje(Lokacija lokacija, Pravac pravac, Element element) {
        this.lokacija = lokacija;
        this.pravac = pravac;
        this.element = element;
    }

    public Lokacija getLokacija() {
        return lokacija;
    }

    public void setLokacija(Lokacija lokacija) {
        this.lokacija = lokacija;
    }

    public Pravac getPravac() {
        return pravac;
    }

    public void setPravac(Pravac pravac) {
        this.pravac = pravac;
    }

    public Element getElement() {
        return element;
    }

    public synchronized void setElement(Element element) {
        this.element = element;
    }

    public boolean isStanica() {
        return isStanica;
    }

    public boolean isKraj(){return isKraj;}

    public void setKraj(boolean kraj) {
        isKraj = kraj;
    }

    public boolean isSkretnica() {
        return isSkretnica;
    }

    public void setStanica(boolean stanica) {
        isStanica = stanica;
    }

    public boolean isPrelaz() {
        return isPrelaz;
    }

    public void setPrelaz(boolean prelaz) {
        isPrelaz = prelaz;
    }
    public void setSkretnica(boolean skretnica){
        isSkretnica=skretnica;
    }

    public Pravac promjeniPravac(Pravac pravac){
        if(pravac==Pravac.VERTIKALAN)
            this.pravac=Pravac.HORIZONTALAN;
        else
            this.pravac=Pravac.VERTIKALAN;
        return this.pravac;
    }
    @Override
    public String toString() {
        return "Polje{" +
                "lokacija=" + lokacija +
                ", pravac=" + pravac +
                ", element=" + element +
                ", isSkretnica=" + isSkretnica +
                ", isStanica=" + isStanica +
                ", isPrelaz=" + isPrelaz +
                '}';
    }
}
