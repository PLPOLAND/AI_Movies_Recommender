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

    // @Override //Jeśli ma zapisywać bazę danych podczas usuwania obiektu z pamięci.
    // public void finalize(){
        
    // }

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
    }
}