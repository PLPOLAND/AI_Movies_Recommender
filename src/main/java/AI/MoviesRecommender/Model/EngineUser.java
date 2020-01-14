package AI.MoviesRecommender.Model;

import java.util.List;

/**
 * EngineUser
 * User wzbogacony o podobieństwo do innych userów i proponowane filmy
 * @author Marek Pałdyna
 * @see User
 * @see AI.MoviesRecommender.DAO.Engine_DAO
 */
public class EngineUser extends User {
    List<Similarity> similarityToOthers;
    List<EngineFilm> sugFilms;

    public EngineUser(User user) {
        super(user);

    }

    public List<Similarity> getSimilarity() {
        return this.similarityToOthers;
    }

    public void setSimilarity(List<Similarity> similarityToOthers) {
        this.similarityToOthers = similarityToOthers;
    }

    public List<EngineFilm> getSugFilms() {
        return this.sugFilms;
    }

    public void setSugFilms(List<EngineFilm> sugFilms) {
        this.sugFilms = sugFilms;
    }
}