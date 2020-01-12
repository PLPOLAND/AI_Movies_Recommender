package AI.MoviesRecommender.Model;

/**
 * Klasa przetrzymująca dane o filmie.
 * 
 * @author Marek Pałdyna
 */

public class Film {
    Long id; //id filmu
    String tytul;//Tytuł filmu
    String zdjecie; //Scieżka zdjęcia filmu
    String gatunek; //gatunek filmu
    int rokProdukcji; //rok produkcji filmu

    public Film(){}
    
    public Film(Long id, String tytul, String zdjecie, String gatunek, int rokProdukcji) {
        this.id = id;
        this.tytul = tytul;
        this.zdjecie = zdjecie;
        this.gatunek = gatunek;
        this.rokProdukcji = rokProdukcji;
    }

    public Film(Film f) {
        this.id = f.id;
        this.tytul = f.tytul;
        this.zdjecie = f.zdjecie;
        this.gatunek = f.gatunek;
        this.rokProdukcji = f.rokProdukcji;
	}

	public Long getID() {
        return this.id;
    }

    public void setID(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return this.tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getZdjecie() {
        return this.zdjecie;
    }

    public void setZdjecie(String zdjecie) {
        this.zdjecie = zdjecie;
    }
    
    public String getGatunek() {
        return this.gatunek;
    }

    public void setGatunek(String gatunek) {
        this.gatunek = gatunek;
    }

    public int getRokProdukcji() {
        return this.rokProdukcji;
    }

    public void setRokProdukcji(int rokProdukcji) {
        this.rokProdukcji = rokProdukcji;
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getID() + "'" +
            ", tytul='" + getTytul() + "'" +
            ", zdjecie='" + getZdjecie() + "'" +
            ", gatunek='" + getGatunek() + "'" +
            ", rokProdukcji='" + getRokProdukcji() + "'" +
            "}";
    }
}