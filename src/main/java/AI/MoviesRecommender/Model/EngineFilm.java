package AI.MoviesRecommender.Model;

/**
 * EngineFilm
 */
public class EngineFilm extends Film implements Comparable<EngineFilm>{

    float recomendation;
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