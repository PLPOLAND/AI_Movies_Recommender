package AI.MoviesRecommender.Model;

/**
 * EngineFilm
 */
public class EngineFilm extends Film implements Comparable<EngineFilm>{

    float recomendation;

    public EngineFilm(Film f){
        super(f);
    }

    public float getRecomendation() {
        return this.recomendation;
    }

    public void setRecomendation(float recomendation) {
        this.recomendation = recomendation;
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