package Generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.User;

/**
 * Generator rekordów w bazie
 */
@Repository
public class Generator {
    
    @Autowired
    /** Baza Userów */
    User_DAO userDatabase;

    @Autowired
    /** Baza filmów */
    Film_DAO film_DAO;

    Random random = new Random(new Date().getTime());
    public Generator(Film_DAO filmDatabase, User_DAO userDatabase2) {
        userDatabase = userDatabase2;
        film_DAO = filmDatabase;
	}

	/**
     * Generuje określoną liczbę userów
     * @param i - liczba userów do wygenerowania
     */
    public void generateUsers(int i){
        i += userDatabase.getNextID().intValue();
        for (int j = userDatabase.getNextID().intValue(); j < i; j++) {
            User u = new User();
            u.setID(new Long(j));
            u.setPolubione(generateLiked(film_DAO.getDatabase().size() / 4));
            u.setNielubione(generateUnLiked(film_DAO.getDatabase().size() / 8, u.getPolubione()));
            this.saveUser(u);
        }
    }

    /**
     * Generuje określoną liczbę polubionych filmów
     * @param i - liczba filmów do wygenerowania
     * @return List<Integer> - filmy polubione
     */
    public List<Long> generateLiked(int i) {
        List<Long> list = new ArrayList<>();
        int maxBound = film_DAO.getDatabase().size()+1;
        for (int j = 0; j < i; j++) {
            Long losed = this.nextLong(random, maxBound);
            while (list.contains(losed) || losed.equals(0L) ) {
                losed = this.nextLong(random, maxBound);
            }
            list.add(losed);
        }
        return list;
    }

    /**
     * Generuje Filmy nie polubione uwzględniając filmy polubione
     * @param i - ilość filmów do wygenerowania
     * @param liked - filmy polubione
     * @return
     */
    public List<Long> generateUnLiked(int i, List<Long> liked) {
        List<Long> list = new ArrayList<>();
        int maxBound = film_DAO.getDatabase().size();
        for (int j = 0; j < i; j++) {
            Long losed = this.nextLong(random, maxBound);
            while (liked.contains(losed)|| list.contains(losed) || losed.equals(0L)) {
                losed = this.nextLong(random, maxBound);
            }
            list.add(losed);
        }
        return list;
    }

    /**
     * Zapisuje usera
     * @param u - user do zapisania
     */
    public void saveUser(User u) {
       userDatabase.save(u);
    }
    
    /**
     * Losuje randomowy long
     * 
     * @param rng   - generator
     * @param bound - garanica
     * @return - long
     */
    long nextLong(Random rng, long bound) {
        long bits, val;
        do {
            bits = (rng.nextLong() << 1) >>> 1;
            val = bits % bound;
        } while (bits - val + (bound - 1) < 0L);
        return val;
    }
}