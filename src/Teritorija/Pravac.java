package Teritorija;

public enum Pravac {
    VERTIKALAN,HORIZONTALAN;

    public static Pravac promjeniPravac(Pravac p){
        if(p== VERTIKALAN)
            return HORIZONTALAN;
        else
            return VERTIKALAN;

    }

}

