package AI.MoviesRecommender.DAO;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.stereotype.Repository;

import AI.MoviesRecommender.Model.Film;

/**
 * Klasa do tworzenia i odczytywania danych o filmach z bazy danych(plików)
 * 
 */
@Repository
public class Film_DAO {

    List<Film> data = new ArrayList<Film>();
    /**
     * Standardowy konstruktor. 
     * Wczytuję baze.
     */
    public Film_DAO(){
        this.readDatabase();
    }


    /**
     * Czyta bazę danych z plików
     */
    public void readDatabase() {
        ObjectMapper obj = new ObjectMapper();
        int i = 1;
        while (true) {
            Film film = null;
            try {
                film = obj.readValue(TypeReference.class.getResourceAsStream("/static/database/films/"+i+"_Film.json"),
                        Film.class);
                data.add(film);
                i++;
            } catch (Exception e) {
                System.out.println("Wczytano " + --i + " filmow");
                break;
            }
        }
    }
    /**
     * Pobieranie bazy danych
     * Jeśli baza danych jest pusta pobiera ją a następnie zwraca
     * @return List<Film> - baza danych
     */
    public List<Film> getDatabase(){
        if (data.isEmpty()) {
            this.readDatabase();
        }
        return this.data;
    }
    /**
     * Pobieranie Filmu o podanych ID
     * @param ID - ID filmu do pobrania
     * @return Film o podanym Id (Jeśli nie ma to null)
     */
    public Film getFilm(Long ID){
        for (Film film : data) {
            if(film.getID().equals(ID))
                return film;
            
        }
        return null;
    }
    /**
     * Sprawdza czy baza danych zawiera już to ID
     * 
     * @param id - id do sprawdzenia
     * @return boolean - czy baza zawiera dane ID
     */
    public boolean contains(Long id) {
        boolean czyZawiera = false;
        for (Film film : data) {
            if (film.getID() == id) {
                czyZawiera = true;
            }
        }
        return czyZawiera;
    }
    
    /**
     * Sprawdza czy baza danych zawiera już film z tym tytułem
     * 
     * @param tytul - tytul do sprawdzenia
     * @return boolean - czy baza zawiera dany tytul
     */
    public boolean contains(String tytul) {
        boolean czyZawiera = false;
        for (Film film : data) {
            if (film.getTytul().equals(tytul)) {
                czyZawiera = true;
            }
        }
        return czyZawiera;
    }
    /**
     * Zwraca następne wole ID dla filmu
     * 
     * @return Long - następne id
     */
    public Long getNextID() {
        return new Long(data.size() + 1);
    }

    /**
     * Zapisuje całą bazę danych do plików
     */
    public void save(){
        for (Film film : data) {
            this.save(film);
        }
    }
    /**
     * Zapisuje podany film do pliku
     * @param film - film do zapisania
     */
    public void save(Film film){
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            File projFile = new File("src/main/resources/static/database/films/" + film.getID() + "_Film.json");
            projFile.createNewFile();// utworzenie pliku jeśli nie istnieje
            objectMapper.writeValue(projFile, film);//plik projektu (src)
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            File appFile = new File(TypeReference.class.getResource("/static/database/films/").getPath() + film.getID() + "_Film.json");
            appFile.createNewFile();// utworzenie pliku jeśli nie istnieje
            objectMapper.writeValue(appFile, film);// plik aplikacji (target)
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.data.add(film);//dodaj film do aktualnie przechowywanej bazy danych
    }

    /**
     * Zwraca listę filmów o podancyh ID
     * 
     * @param ids - filmy do zwrócenia
     * @return List<Film> - filmy o podanych ID
     */
    public List<Film> getFilmsFromIDs(List<Long> ids) {
        List<Film> films = new ArrayList<>();

        for (Long id : ids) {
            Film f = this.getFilm(id);
            if (f!=null)
                films.add(f);
        }

        return films;
    }
}