package ua.training.project4.controller.commands.administrator;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.training.project4.controller.commands.Command;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.service.HorseService;
import static ua.training.project4.view.Constants.*;

public class ShowNewRaceEditor extends Command {
	
	HorseService horseService = HorseService.getInstance();

	public ShowNewRaceEditor(String successPage) {
		super(successPage);
	}

	public static void setRequestAttributes(HttpServletRequest req, Set<Horse> horses) {
		req.setAttribute(HORSES, horses);
	}
	
	@Override
	protected void peformAction(HttpServletRequest req, 
				HttpServletResponse resp, Map<String, Object> validValues) {
		Set<Horse> horses = horseService.getAllHorses();
		setRequestAttributes(req, horses);
	}
}
