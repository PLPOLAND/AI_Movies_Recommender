package AI.MoviesRecommender.Controler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import AI.MoviesRecommender.Banner.Banner;
import AI.MoviesRecommender.Banner.Menu;
import AI.MoviesRecommender.DAO.Engine_DAO;
import AI.MoviesRecommender.DAO.Film_DAO;
import AI.MoviesRecommender.DAO.User_DAO;
import AI.MoviesRecommender.Model.EngineFilm;
import AI.MoviesRecommender.Model.Film;
import AI.MoviesRecommender.Model.User;
import AI.MoviesRecommender.Security.Security;
import Generator.Generator;



@Controller
public class MainController {
	@Autowired
	Film_DAO filmDatabase;
	@Autowired
	User_DAO userDatabase;
	@Autowired
	Engine_DAO engineDatabase;

/**
 * Strona główna
 */
	@RequestMapping("/")
	public String main(Model model, HttpServletRequest request) {
		Security security = new Security(request, userDatabase);
		if (!security.isLoged())
			return "redirect:/login";
		if (userDatabase.getUserByID(security.getUserID()).getPolubione() == null) {
			return "redirect:/firstpage";
		}
		if (userDatabase.getUserByID(security.getUserID()).getPolubione().size() < 4) {
			return "redirect:/firstpage";
		}
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

		
		Film f = filmDatabase.getFilm(film_ID);
		model.addAttribute("f", f);
		EngineFilm film = null;
		
		float procent = 0;
		if(engineDatabase.getUserById(security.getUserID()) != null){
			if(engineDatabase.getUserById(security.getUserID()).getSugFilms() != null){
				for (EngineFilm fi : engineDatabase.getUserById(security.getUserID()).getSugFilms()) {
					if (fi.getID().equals(film_ID)) {
						film=fi;
					}
				}
				if(film!=null){
					if(film.haveRecomendation()){
						procent = film.getRecomendation();
					}
				}
				if(userDatabase.getUserByID(security.getUserID()).getPolubione().contains(film_ID)){
					procent = 100f;
				}
			}
		}
		
		model.addAttribute("procent", procent);
		model.addAttribute("uID", security.getUserID());

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
	 * Strona odpowiadająca za pierwsze wybieranie filmów (nie)polubionych 
	 */
	@RequestMapping("/firstpage")
	public String firstpage(Model model, HttpServletRequest request) {
		Security security = new Security(request, userDatabase);
		if (!security.isLoged())
			return "redirect:/login";
		Banner banner = new Banner(new Menu(1), security.getFullUserData());
		model.addAttribute(banner);
		return "firstpage";
	}
	/**
	 * 
	 * 
	 */
	@RequestMapping("/proponowane")
	public String recommended(Model model, HttpServletRequest request) {
		Security security = new Security(request, userDatabase);
		if (!security.isLoged())
			return "redirect:/login";//przekierowanie do logowania
		User u = userDatabase.getUserByID(security.getUserID());
		if (u.getPolubione() == null) {
			return "redirect:/firstpage";
		}
		if (u.getPolubione().size() < 4) {
			return "redirect:/firstpage";
		}
		Banner banner = new Banner(new Menu(), security.getFullUserData());
		model.addAttribute(banner);

		return "recommendPage";
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
		Generator gen = new Generator(filmDatabase, userDatabase);
		// gen.generateUsers(1000); // odkomentować w celu wygenerowania nowych userów
		return "tmp";
	}
}
