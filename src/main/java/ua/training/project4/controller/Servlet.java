package ua.training.project4.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.administrator.DeleteRace;
import ua.training.project4.controller.commands.administrator.FinishRace;
import ua.training.project4.controller.commands.administrator.OrganizeRace;
import ua.training.project4.controller.commands.administrator.SetRaceResults;
import ua.training.project4.controller.commands.administrator.ShowAdministratorControls;
import ua.training.project4.controller.commands.administrator.ShowEditRaceMenu;
import ua.training.project4.controller.commands.administrator.ShowNewRaceEditor;
import ua.training.project4.controller.commands.administrator.ShowRaceResultsEditor;
import ua.training.project4.controller.commands.administrator.StartRace;
import ua.training.project4.controller.commands.administrator.SubmitRaceChanges;
import ua.training.project4.controller.commands.bookmaker.SetOrEditCoefficients;
import ua.training.project4.controller.commands.bookmaker.ShowBookmakerControlls;
import ua.training.project4.controller.commands.bookmaker.ShowCoefficientsEditor;
import ua.training.project4.controller.commands.user.MakeBet;
import ua.training.project4.controller.commands.user.ShowBetEditor;
import ua.training.project4.controller.commands.user.ShowCurrentRaces;
import ua.training.project4.controller.commands.user.ShowWinningsForm;
import ua.training.project4.controller.commands.user.WinningsMessage;
import ua.training.project4.controller.commands.Command;

public class Servlet extends HttpServlet {
		
	private Map<String, Command> commands = new HashMap<>();
	
	private static final Pattern actionPattern = Pattern.compile("^/app(/[a-z/\\d]+)/?$");
	
	@Override
	public void init(){
	   	//ADMINISTRATOR
	   	commands.put("GET:/administrator", 
	   			new ShowAdministratorControls("/WEB-INF/jsp/administrator_main.jsp"));
	   	
	   	commands.put("GET:/administrator/new", 
	   			new ShowNewRaceEditor("/WEB-INF/jsp/race_editor.jsp"));
	   	commands.put("POST:/administrator/new", 
	   			new OrganizeRace("/app/administrator", "/WEB-INF/jsp/race_editor.jsp"));
	   	
		commands.put("GET:/administrator/edit", 
	   			new ShowEditRaceMenu("/WEB-INF/jsp/race_editor.jsp"));
	   	commands.put("POST:/administrator/edit", 
	   			new SubmitRaceChanges("/app/administrator", "/WEB-INF/jsp/race_editor.jsp"));
	   	
	   	commands.put("POST:/administrator/start", new StartRace("/app/administrator"));
	   	commands.put("POST:/administrator/delete", new DeleteRace("/app/administrator"));
	   	commands.put("POST:/administrator/finish", new FinishRace("/app/administrator"));
	   	
	   	commands.put("GET:/administrator/results", 
	   			new ShowRaceResultsEditor("/WEB-INF/jsp/race_results_editor.jsp"));
	   	commands.put("POST:/administrator/results", 
	   			new SetRaceResults("/app/administrator", "/WEB-INF/jsp/race_results_editor.jsp"));
	   	//BOOKMAKER
	   	commands.put("GET:/bookmaker", 
	   			new ShowBookmakerControlls("/WEB-INF/jsp/bookmaker_main.jsp"));
	 	commands.put("GET:/bookmaker/edit", 
	 			new ShowCoefficientsEditor("/WEB-INF/jsp/coefficients_editor.jsp"));
	 	commands.put("POST:/bookmaker/edit", 
	   			new SetOrEditCoefficients("/app/bookmaker", "/WEB-INF/jsp/coefficients_editor.jsp"));
	 	//USER
	 	commands.put("GET:/races", 
	 			new ShowCurrentRaces("/WEB-INF/jsp/main.jsp"));
	 	
	 	commands.put("GET:/bet", 
	 			new ShowBetEditor("/WEB-INF/jsp/bet_editor.jsp"));
		commands.put("POST:/bet", 
	   			new MakeBet("/WEB-INF/jsp/bet_successful.jsp", "/WEB-INF/jsp/bet_editor.jsp"));
		
		commands.put("GET:/winnings", 
				new ShowWinningsForm("/WEB-INF/jsp/winnings_form.jsp"));
		commands.put("POST:/winnings", 
				new WinningsMessage("/WEB-INF/jsp/winnings_message.jsp")); 
	}
	
	private void findCommandAndExecute(HttpServletRequest req, HttpServletResponse resp) 
			throws IOException, ServletException  {
		String method = req.getMethod().toUpperCase();
		String path = req.getRequestURI();
		Matcher m = actionPattern.matcher(path);
		m.matches();
		Command command = commands.get(method + ":" + m.group(1));
		if (command == null) {
			throw new RuntimeException("Command not found");
		}
		command.execute(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		System.out.println("received POST");
		findCommandAndExecute(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
					throws ServletException, IOException {
		
		System.out.println("received GET");
		findCommandAndExecute(req, resp);
	}
}
