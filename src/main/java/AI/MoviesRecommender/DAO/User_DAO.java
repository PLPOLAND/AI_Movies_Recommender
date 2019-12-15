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

import AI.MoviesRecommender.Model.User;

/**
 * Klasa do tworzenia i odczytywania danych o filmach z bazy danych(plików)
 * 
 */
@Repository
public class User_DAO {

    List<User> data = new ArrayList<User>();

    /**
     * Standardowy konstruktor. Wczytuję baze.
     */
    public User_DAO() {
        this.readDatabase();
    }

    // @Override //Jeśli ma zapisywać bazę danych podczas usuwania obiektu z
    // pamięci.
    // public void finalize(){

    // }

    /**
     * Czyta bazę danych z plików
     */
    public void readDatabase() {
        ObjectMapper obj = new ObjectMapper();
        int i = 1;
        while (true) {
            User user = null;
            try {
                user = obj.readValue(TypeReference.class.getResourceAsStream("/static/database/users/" + i + "_User.json"), User.class);
                data.add(user);
                i++;
            } catch (Exception e) {
                System.out.println("Wczytano "+ --i +" userow");
                break;
            }
        }
    }

    /**
     * Pobieranie bazy danych Jeśli baza danych jest pusta pobiera ją a następnie
     * zwraca
     * 
     * @return List<User> - baza danych
     */
    public List<User> getDatabase() {
        if (data.isEmpty()) {
            this.readDatabase();
        }
        return this.data;
    }

    /**
     * Zapisuje całą bazę danych do plików
     */
    public void save() {
        for (User user : data) {
            this.save(user);
        }
    }
    /**
     * Sprawdza czy baza danych zawiera już to ID
     * @param id - id do sprawdzenia
     * @return boolean - czy baza zawiera dane ID
     */
    public boolean contains(Long id){
        boolean czyZawiera = false;
        for (User user : data) {
            if (user.getID() == id) {
                czyZawiera = true;
            }
        }
        return czyZawiera;
    }
    /**
     * Zwraca następne wole ID dla usera
     * @return Long - następne id
     */
    public Long getNextID() {
        return new Long(data.size()+1);
    }

    /**
     * Zapisuje podany user do pliku
     * 
     * @param user - user do zapisania
     */
    public void save(User user) {
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            File projFile = new File("src/main/resources/static/database/users/" + user.getID() + "_User.json");
            projFile.createNewFile();// utworzenie pliku jeśli nie istnieje
            objectMapper.writeValue(projFile, user);// plik projektu (src)
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
            File appFile = new File(
                    TypeReference.class.getResource("/static/database/users/").getPath() + user.getID() + "_User.json");
            appFile.createNewFile();// utworzenie pliku jeśli nie istnieje
            objectMapper.writeValue(appFile, user);// plik aplikacji (target)
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}