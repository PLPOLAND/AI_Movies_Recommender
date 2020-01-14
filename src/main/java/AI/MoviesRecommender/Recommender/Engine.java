package AI.MoviesRecommender.Recommender;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.EngineFilm;
import AI.MoviesRecommender.Model.EngineUser;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.Similarity;

/**
 * Klasa przetwarzająca dane i obsugująca logikę potrzebną do rekomendacji
 * filmów.
 * 
 * @author Rafał Świąder
 * @author Marek Pałdyna
 * 
 * @see AI.MoviesRecommender.DAO.Engine_DAO
 */
@Service
public class Engine {

    // Listy użytkowników i filmów
    List<User> users;
    List<Film> films;

    final float similarity_treshold = 0.75f;
    final int similar_users_limit = 100;
    final float minFilmRecom = 50f; // Minimalna wartość trafności filmu

    @Autowired
    public Engine(User_DAO users, Film_DAO films) {
        this.users = users.getDatabase();
        this.films = films.getDatabase();
    }

    public User getUser(Long ID) {
        for (User i : this.users) {
            if (i.getID() == ID)
                return i;
        }
        return null;
    }

    public Film getFilm(Long ID) {
        for (Film i : this.films) {
            if (i.getID() == ID)
                return i;
        }
        return null;
    }

    /**
     * Zwraca podobieństwo userów
     * 
     * @author Rafał Świąder
     * @param first_ID  - id pierwszego usera
     * @param second_ID - id drugiego usera
     * @return float - podobieństwo
     * @deprecated
     * @see Engine#getUsersSim(Long firstId, Long secId)
     */
    public float getUsersSimilarity(Long first_ID, Long second_ID) {
        Long same_rating = 0L;
        Long opposite_rating = 0L;

        for (Long i : getUser(first_ID).getPolubione()) {
            if (getUser(second_ID).getPolubione().contains(i))
                same_rating++;
            else if (getUser(second_ID).getNielubione().contains(i))
                opposite_rating--;
        }

        for (Long i : getUser(first_ID).getNielubione()) {
            if (getUser(second_ID).getPolubione().contains(i))
                same_rating--;
            else if (getUser(second_ID).getNielubione().contains(i))
                opposite_rating++;
        }

        if ((same_rating + opposite_rating) == 0L)
            return 0.f;

        float similarity_precantage = (float) same_rating / (float) (same_rating + opposite_rating);
        return similarity_precantage;
    }

    /**
     * Zwraca podobieństwo userów
     * 
     * @author Marek Pałdyna
     * @param firstId - Id pierwszego usera
     * @param secId   - Id drugiego usera
     * @return float - podobieństwo userów
     */
    public float getUsersSim(Long firstId, Long secId) {
        float similarity = 0;
        float sameRate = 0;
        float diffRate = 0;
        if (this.getUser(firstId).getPolubione() != null && this.getUser(secId).getPolubione() != null
                && this.getUser(secId).getNielubione() != null) {
            for (Long id : this.getUser(firstId).getPolubione()) {
                if (this.getUser(secId).getPolubione().contains(id)) {
                    sameRate++;
                } else if (this.getUser(secId).getNielubione().contains(id)) {
                    diffRate--;
                }
            }
        }
        if (this.getUser(firstId).getNielubione() != null && this.getUser(secId).getPolubione() != null
                && this.getUser(secId).getNielubione() != null) {
            for (Long id : this.getUser(firstId).getNielubione()) {
                if (this.getUser(secId).getNielubione().contains(id)) {
                    sameRate++;
                } else if (this.getUser(secId).getPolubione().contains(id)) {
                    diffRate--;
                }
            }
        }
        if (sameRate + diffRate == 0) {
            return 0f;
        } else {
            similarity = (float) sameRate / (sameRate + Math.abs(diffRate));
        }
        return similarity;

    }

    /**
     * Zwraca listę usrów o podobnych ocenach
     * 
     * @author Rafał Świąder
     * @param ID - id usera dla którego zwracani są podobni userzy
     * @return List<User> - lista userów podobnych.
     * @deprecated
     * @see Engine#getSimUsers(Long Id)
     */
    public List<User> getSimilarUsers(Long ID)// Lista użytkowników o podobnych ocenach
    {
        if (getUser(ID).getPolubione() == null) {
            return null;
        }
        List<User> similar = new ArrayList<User>();
        for (User i : this.users) {
            if (ID != i.getID()) {
                float u_similarity = getUsersSimilarity(ID, i.getID());
                if (u_similarity >= similarity_treshold) {
                    if (similar.size() >= 100) {
                        float min_similarity = 1.f;
                        User worst_user = users.get(0);
                        for (User j : similar) {
                            float new_minsimilarity = getUsersSimilarity(j.getID(), ID);
                            if (new_minsimilarity < min_similarity) {
                                min_similarity = new_minsimilarity;
                                worst_user = j;
                            }
                        }
                        if (u_similarity > min_similarity) {
                            similar.remove(worst_user);
                            similar.add(i);
                        }
                    } else
                        similar.add(i);
                }
            }
        }

        return similar;
    }

    /**
     * Zwraca listę userów podobnych
     * 
     * @param ID - id usera dla którego są znajdowani userzy podobni
     * @return List<User> - lista userów podobnych
     */
    public List<Similarity> getSimUsers(Long ID) {
        List<Similarity> similar = new ArrayList<>();
        if (getUser(ID).getPolubione() == null) {// jeśli nie polubiono jeszcze niczego
            return null;
        }
        if (getUser(ID).getPolubione().size() < 4)// jeśli nie polubiono jeszcze conajmniej 4 filmów
            return null;

        for (User user : this.users) {
            if (ID != user.getID()) {
                Similarity s = new Similarity(user.getID(), getUsersSim(ID, user.getID()));
                similar.add(s);
            }
        }
        return similar;
    }

    /**
     * Zwraca listę id filmów recomendowanych
     * 
     * @deprecated
     * @author Rafał Świąder
     * @param main_user     - user dla którego szukamy filmów podobnych
     * @param similar_users - userzy podobni do main_user, których bierzemy pod
     *                      uwagę
     * @return List<Long> - id filmów podobnych
     */
    public List<Long> getRecommendedFilms(User main_user, List<User> similar_users) {
        // System.out.println(similar_users);

        List<Long> all_liked = new ArrayList<Long>();
        for (User i : similar_users) {
            for (Long j : i.getPolubione()) {
                if (!all_liked.contains(j))
                    all_liked.add(j);
            }
        }

        // System.out.println(all_liked);

        List<Long> films = new ArrayList<Long>();
        List<Float> percentages = new ArrayList<Float>();
        for (Long i : all_liked) {
            Float perc = getPercantageAccuracy(i, similar_users);
            if (perc >= 50.f) {
                films.add(i);
                percentages.add(perc);
            }
        }

        return films;
    }

    /**
     * Zwraca listę filmów recomendowanych
     * 
     * @author Marek Pałdyna
     * @param main_user - user dla którego szukamy rekomendacji
     * @param similar_u - podobni userzy
     * @return List<EngineFilm> - lista rekomendowanych filmów z ich procentową
     *         wartością
     */
    public List<EngineFilm> getRecommendedFilmsList(EngineUser main_user) {
        if (main_user.getSimilarity() != null) {
            if (main_user.getSimilarity().size() >= 4) {
                
                List<Similarity> similarities = new ArrayList<>();
                for (Similarity similarity : main_user.getSimilarity()) {
                    if (similarity.getSimilarity() >= similarity_treshold && similarities.size() <= similar_users_limit) {
                        similarities.add(similarity);
                    } else if (similarities.size() > similar_users_limit)
                        break;
                }

                List<User> simUsers = new ArrayList<>();

                for (Similarity similarity : similarities) {
                    simUsers.add(this.getUser(similarity.getID()));
                }

                List<Film> filmy = films;

                List<EngineFilm> recofilms = new ArrayList<>();
                if(main_user.getNielubione()==null)
                    main_user.setNielubione(new ArrayList<>());
                for (Film film : filmy) {
                    int liked = 0, disliked = 0;
                    if (!main_user.getPolubione().contains(film.getID()) && !main_user.getNielubione().contains(film.getID())) {
                        for (User i : simUsers) {
                            if (i.getPolubione().contains(film.getID()))
                                liked++;
                            else if (i.getNielubione().contains(film.getID()))
                                disliked++;
                        }
                        float percentage=0;
                        if (liked + disliked != 0) {
                            percentage = ((float) liked / (float) (liked + disliked)) * 100;
                        }
                        EngineFilm f = new EngineFilm(film);
                        f.setRecomendation(percentage);
                        recofilms.add(f);
                    }
                }

                return recofilms;
            }
        }
        return null;
    }

    /**
     * Pobierz procentową zgodność filmu z userem
     * 
     * @author Rafał Świąder
     * @deprecated
     * @param film_ID
     * @param similar_users
     * @return
     */
    public float getPercantageAccuracy(Long film_ID, List<User> similar_users) {
        int liked = 0, disliked = 0;

        for (User i : similar_users) {
            if (i.getPolubione().contains(film_ID))
                liked++;
            else if (i.getNielubione().contains(film_ID))
                disliked++;
        }

        float percentage = (float) liked / (float) (liked + disliked);
        return percentage * 100;
    }
}