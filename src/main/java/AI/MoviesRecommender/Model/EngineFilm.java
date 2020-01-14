package AI.MoviesRecommender.Model;

/**
 * EngineFilm Klasa do obsługi rekomendacji dla filmu
 * 
 * @see Film
 * @see AI.MoviesRecommender.DAO.Engine_DAO
 */
public class EngineFilm extends Film implements Comparable<EngineFilm>{

    float recomendation; //procentowa wartość trafności 
    boolean haveRecomendation;

    public EngineFilm(Film f){
        super(f);
        haveRecomendation = false;
    }

    public float getRecomendation() {
        return this.recomendation;
    }

    public void setRecomendation(float recomendation) {
        haveRecomendation = true;
        this.recomendation = recomendation;
    }
    
    /**
     * Czy posiada ustawioną wartość trafności
     * @return boolean -czy posiada ustawioną wartość trafności 
     */
    public boolean haveRecomendation(){  
        return haveRecomendation;
    }

    @Override
    public int compareTo(EngineFilm f) {
        if (this.recomendation > f.recomendation) {
            return -1;
        } else if (this.recomendation < f.recomendation) {
            return 1;
        }
        return 0;
    }

    
}