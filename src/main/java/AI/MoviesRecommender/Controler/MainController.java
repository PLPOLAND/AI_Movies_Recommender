package AI.MoviesRecommender.Controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Security.Security;



@Controller
public class MainController {
	@Autowired
	Film_DAO filmDatabase;
	@Autowired
	User_DAO userDatabase;


/**
 * Strona główna
 */
	@RequestMapping("/")
	public String main(HttpServletRequest request) {
		Security security = new Security(request, userDatabase);
		if (!security.isLoged())
			return "redirect:/login";

		return "mainpage";
	}
	/**
	 * Strona z tworzeniem nowych filmów
	 */
	@RequestMapping("/filmMaker")
	public String filmMaker(){
		return "filmcreator";
	}

	/**
	 * Strona logowania
	 */
	@RequestMapping("/login")
	public String login() {
		return "loginPage";
	}
}
