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


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
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
    
}