package Teritorija;

import java.io.Serializable;
import java.util.Objects;

public class Lokacija implements Serializable {
    private int pozicijaX;
    private int pozicijaY;

    public Lokacija(int X, int Y) {
        this.pozicijaX = X;
        this.pozicijaY = Y;
    }

    public int getPozicijaX() {
        return pozicijaX;
    }

    public void setPozicijaX(int pozicijaX) {
        this.pozicijaX = pozicijaX;
    }

    public int getPozicijaY() {
        return pozicijaY;
    }

    public void setPozicijaY(int pozicijaY) {
        this.pozicijaY = pozicijaY;
    }

    public boolean odgovaraKoordinatama(int x,int y){
        return pozicijaX==x&&pozicijaY==y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lokacija lokacija = (Lokacija) o;
        return pozicijaX == lokacija.pozicijaX &&
                pozicijaY == lokacija.pozicijaY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pozicijaX, pozicijaY);
    }

    @Override
    public String toString() {
        return "("+pozicijaX+","+pozicijaY+")";
    }
}
