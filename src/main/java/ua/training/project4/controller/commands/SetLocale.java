package ua.training.project4.controller.commands;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static ua.training.project4.view.Constants.*;

public class SetLocale extends Command {

	public SetLocale(String successPage) {
		super(successPage);
	}
	
	private Locale getLocaleOrUS(String newLang) {
		if (Objects.isNull(newLang)) return Locale.US;	
		Locale loc;
			switch (newLang) {
				case "en": {
					loc = Locale.US;
					break;
				}
				case "ru": {
					loc = Locale.forLanguageTag("ru-RU");
					break;
				}
				default: {
					loc = Locale.US;
				}
			}
		return loc;
	}
		
	@Override
	protected void peformAction(HttpServletRequest req, 
			HttpServletResponse resp, Map<String, Object> validValues) {
		
		Locale loc = getLocaleOrUS(req.getParameter(NEW_LOCALE));
		req.getSession().setAttribute(LOCALE, loc);
		resp.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
	}
}
