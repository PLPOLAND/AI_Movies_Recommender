package AI.MoviesRecommender.Controler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.User;



@Controller
public class MainController {
	@Autowired
	Film_DAO filmDatabase;
	@RequestMapping("/")
	public String main() {
		ObjectMapper obj = new ObjectMapper();
		User user = null;
		try {
			user = obj.readValue(TypeReference.class.getResourceAsStream("/static/database/Users.json"),User.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setID(3L);
		System.out.println(user.toString());

		Film film = new Film(2l,"Mars", "/img/mars.png", "Sci-Fi", 1980);
		System.out.println(filmDatabase.getDatabase().get(0).toString());
		System.out.println(filmDatabase.getDatabase().size());
		return "mainpage";
	}
}
