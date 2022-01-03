package ProiectPI;

public class Produs {
    String titlu;
    int pret;
    public Produs(String titlu,int pret){
        this.titlu=titlu;
        this.pret=pret;
    }

    public String getTitlu() {
        return titlu;
    }

    public int getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "titlu='" + titlu + '\'' +
                ", pret=" + pret +
                '}';
    }
}
