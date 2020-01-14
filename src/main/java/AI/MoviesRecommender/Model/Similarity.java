package AI.MoviesRecommender.Model;

/**
 * Similarity Klasa przetrzymująca dane o podobieństwie do innego usera
 * 
 * @see AI.MoviesRecommender.DAO.Engine_DAO
 */
public class Similarity implements Comparable<Similarity>{
    Long ID; //user do którego przyrównywnujemy
    float similarity; //podobieństwo
    
    public Long getID() {
        return this.ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public float getSimilarity() {
        return this.similarity;
    }

    public void setSimilarity(float similarity) {
        this.similarity = similarity;
    }

    public Similarity(Long ID, float similarity) {
        this.ID = ID;
        this.similarity = similarity;
    }

    public boolean equals(Similarity o) {

        if (o.ID == this.ID && o.similarity == this.similarity) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Similarity o) {
        if (this.similarity < o.similarity) {
            return -1;
        } else if(this.similarity > o.similarity) {
            return 1;
        }
        return 0;
    }

    
}