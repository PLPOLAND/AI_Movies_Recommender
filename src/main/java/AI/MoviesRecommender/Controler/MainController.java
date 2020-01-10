package AI.MoviesRecommender.Controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import AI.MoviesRecommender.Banner.Banner;
import AI.MoviesRecommender.Banner.Menu;
import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Recommender.Engine;
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
	public String main(Model model, HttpServletRequest request) {
		Security security = new Security(request, userDatabase);
		if (!security.isLoged())
			return "redirect:/login";
		Banner banner = new Banner(new Menu(), security.getFullUserData());
		model.addAttribute(banner);
		return "mainpage";
	}
	/**
	 * Strona z tworzeniem nowych filmów
	 */
	@RequestMapping("/filmMaker")
	public String filmMaker(Model model, HttpServletRequest request){
		Security security = new Security(request, userDatabase);

		Banner banner = new Banner(new Menu(), security.getFullUserData());
		model.addAttribute(banner);

		return "filmcreator";
	}

	/**
	 * Strona z filmem
	 */
	@RequestMapping("/film")
	public String film(Model model, HttpServletRequest request, @RequestParam("ID") Long film_ID){
		Security security = new Security(request, userDatabase);

		if(!security.isLoged())
			return "redirect:/login";

		Banner banner = new Banner(new Menu(), security.getFullUserData());
		model.addAttribute(banner);

		Engine eng = new Engine(userDatabase.getDatabase(), filmDatabase.getDatabase());
		Film f = eng.getFilm(film_ID);
		model.addAttribute(f);
		System.out.println(f.toString());
		System.out.println(film_ID);

		return "film";
	}
	/**
	 * Strona z rejestracją
	 */
	@RequestMapping("/register")
	public String register(){
		return "register";
	}
	/**
	 * Strona logowania
	 */
	@RequestMapping("/login")
	public String login() {
		return "loginPage";
	}
	/**
	 * Strona wylogowywania
	 */
	@RequestMapping("/logout")
	public String logout(Model model, HttpServletRequest request){
		Security security = new Security(request, userDatabase);
		security.logout();
		return "redirect:/login";
	}

	@RequestMapping("/tmp")
	public String tmp() {
		return "tmp";
	}
}
