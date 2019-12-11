package AI.MoviesRecommender.Controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import Generator.Generator;



@Controller
public class MainController {
	@Autowired
	Film_DAO filmDatabase;
	@Autowired
	User_DAO userDatabase;



	@RequestMapping("/")
	public String main() {
		Generator generator = new Generator(filmDatabase,userDatabase);
		//generator.generateUsers(5); // generowanie 5 userów
		return "mainpage";
	}
}
