package Teritorija;

public class Dionica {
    private int brojVozila=0;
    private Smjer smjerDionice;
    private int brzinaDionice;

    public synchronized Smjer getSmjerDionice(){
        return smjerDionice;
    }
    public synchronized void setSmjerDionice(Smjer smjer){
        smjerDionice=smjer;
    }
    public synchronized int getBrzinaDionice(){return brzinaDionice;}
    public synchronized void setBrzinaDionice(int brzina){brzinaDionice=brzina;}
    public synchronized int getBrojVozila(){return brojVozila;}

    public synchronized boolean isSlobodna(){
        return brojVozila==0;
    }
    public synchronized void zauzmi(){
        brojVozila++;
    }
    public synchronized void oslobodi(){
        brojVozila--;
    }
    public synchronized boolean porediSmjer(Smjer smjer){
        return smjerDionice==smjer;
    }

}
