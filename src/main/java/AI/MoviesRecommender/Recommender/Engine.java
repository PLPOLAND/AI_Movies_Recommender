package AI.MoviesRecommender.Recommender;

import java.util.ArrayList;
import java.util.List;

import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.Model.Film;

/**
 * Klasa przetrzymująca dane o filmie.
 * 
 * @author Rafał Świąder
 */

public class Engine {

    // Listy użytkowników i filmów
    List<User> users;
    List<Film> films;

     final float similarity_treshold = 0.75f;
     final int similar_users_limit = 100;

     public Engine(List<User> users, List<Film> films)
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
            if(i.getID() == ID)
                return i;
         }
         return null;
     }

     /* Algorytmy doboru */
     public float getUsersSimilarity(Long first_ID, Long second_ID)
     {
        Long same_rating = 0L;
        Long opposite_rating = 0L;

        for(Long i:getUser(first_ID).getPolubione())
        {
            if(getUser(second_ID).getPolubione().contains(i))
                same_rating++;
            else if(getUser(second_ID).getNielubione().contains(i))
                opposite_rating--;
        }

        for(Long i:getUser(first_ID).getNielubione())
        {
            if(getUser(second_ID).getPolubione().contains(i))
                same_rating--;
            else if(getUser(second_ID).getNielubione().contains(i))
                opposite_rating++;
        }

        if((same_rating + opposite_rating) == 0L)
            return 0.f;

        float similarity_precantage = (float)same_rating / (float)(same_rating+opposite_rating);
        return similarity_precantage;
     }

     public List<User> getSimilarUsers(Long ID)//Lista użytkowników o podobnych ocenach
     { 
        List<User> similar = new ArrayList<User>();
        for(User i:this.users)
        {
            if(ID != i.getID())
            {
                float u_similarity = getUsersSimilarity(ID, i.getID());
                if(u_similarity >= similarity_treshold)
                {
                    if(similar.size() >= 100)
                    {
                        float min_similarity = 1.f;
                        User worst_user = users.get(0);
                        for(User j:similar)
                        {
                            float new_minsimilarity = getUsersSimilarity(j.getID(), ID);
                            if(new_minsimilarity < min_similarity)
                            {
                                min_similarity = new_minsimilarity;
                                worst_user = j;
                            }
                        }
                        if(u_similarity > min_similarity)
                        {
                            similar.remove(worst_user);
                            similar.add(i);
                        }  
                    }
                    else
                        similar.add(i);
                }
            }
        }

        return similar;
     }

     public List<Long> getRecommendedFilms(User main_user, List<User> similar_users)
     {
        //System.out.println(similar_users);

         List<Long> all_liked = new ArrayList<Long>();
         for(User i:similar_users)
         {
             for(Long j:i.getPolubione())
             {
                if(!all_liked.contains(j))
                    all_liked.add(j);
             }
         }

         //System.out.println(all_liked);

         List<Long> films = new ArrayList<Long>();
         List<Float> percentages = new ArrayList<Float>();
         for(Long i:all_liked)
         {
             Float perc = getPercantageAccuracy(i, similar_users);
             if(perc >= 50.f)
             {
                films.add(i);
                percentages.add(perc);
             }
         }

         return films;
     }

     public float getPercantageAccuracy(Long film_ID, List<User> similar_users)
     {
        int liked = 0, disliked = 0;

        for(User i:similar_users)
        {
            if(i.getPolubione().contains(film_ID))
                liked++;
            else if(i.getNielubione().contains(film_ID))
                disliked++;
        }

        float percentage = (float)liked / (float)(liked + disliked);
        return percentage * 100;
     }
 }