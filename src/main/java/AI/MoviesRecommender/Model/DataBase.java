package AI.MoviesRecommender.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa przetrzymująca dane o filmie.
 * 
 * @author Rafał Świąder
 */

 public class DataBase
 {
     //Listy użytkowników i filmów
     List<User> users; 
     List<Film> films;

     final float similarity_treshold = 0.75f;
     final int similar_users_limit = 100;

     DataBase(List<User> users, List<Film> films)
     {
        this.users = users;
        this.films = films;
     }

     public User getUser(Long ID)
     {
         for(User i:this.users)
         {
            if(i.getID() == ID)
                return i;
         }
         return null;
     }

     public Film getFilm(Long ID)
     {
         for(Film i:this.films)
         {
            if(i.getId() == ID)
                return i;
         }
         return null;
     }

     /* Algorytmy doboru */
     public float getUsersSimilarity(Long first_ID, Long second_ID)
     {
         //...
        return 0.0f;
     }

     public List<User> getSimilarUsers(Long ID)//Lista użytkowników o podobnych ocenach
     { 
        List<User> similar = new ArrayList<User>();
        for(User i:this.users)
        {
            if(ID != i.getID())
            {
                if(getUsersSimilarity(ID, i.getID()) >= similarity_treshold)
                    similar.add(i);
            }
        }

        while(similar.size() > similar_users_limit)
        {
            float min_similarity = 1.f;
            int index = 0;
            for(User i:similar)
            {
                float user_similarity = getUsersSimilarity(ID, i.getID());
                if(user_similarity < min_similarity)
                {
                    min_similarity = user_similarity;
                    index = users.indexOf(i);
                }
            }
            users.remove(index);
        }

        return similar;
     }
 }