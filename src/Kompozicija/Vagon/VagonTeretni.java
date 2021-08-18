package Kompozicija.Vagon;

public class VagonTeretni extends Vagon{
    private int nosivost;
    private static int count=0;

    public VagonTeretni() {
        super(count+10, "VT"+count);
        this.nosivost = count+2;
    }

    public int getNosivost() {
        return nosivost;
    }

    public void setNosivost(int nosivost) {
        this.nosivost = nosivost;
    }

    @Override
    public String toString() {
        return "VagonTeretni{" +
                "nosivost=" + nosivost +
                "} " + super.toString();
    }
}
