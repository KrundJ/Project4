package ua.training.project4.controller.commands.administrator;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.training.project4.view.Constants.RACE_ID;
import static ua.training.project4.view.Constants.VALIDATION_ERR_RACE_ID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ua.training.project4.controller.Servlet;
import ua.training.project4.model.service.ServiceFactory;

public class StartRaceTest {
	
	@Rule
	public ExpectedException e = ExpectedException.none();
	
	private Servlet servlet;
	{
		servlet = new Servlet();
		servlet.init();
	}
	
	//no raceID in request
	@Test
	public void shouldThrowException() throws Exception {
		 e.expect(RuntimeException.class);
		 e.expectMessage(VALIDATION_ERR_RACE_ID);
		     
		 HttpServletRequest req = mock(HttpServletRequest.class);       
	     HttpServletResponse resp = mock(HttpServletResponse.class);
	     when(req.getMethod()).thenReturn("POST");
	     when(req.getRequestURI()).thenReturn("/app/administrator/start");
	     when(req.getParameter(RACE_ID)).thenReturn(null);
	     	     
	     servlet.doPost(req, resp);
	}
	
	// non integer raceID in request
	@Test
	public void shouldAlsoThrowException() throws Exception {
		e.expect(RuntimeException.class);
		e.expectMessage(VALIDATION_ERR_RACE_ID);
		 
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
	    when(req.getMethod()).thenReturn("POST");
	    when(req.getRequestURI()).thenReturn("/app/administrator/start");
		when(req.getParameter(RACE_ID)).thenReturn("djhkkhd");

		servlet.doPost(req, resp);
	}
	
	//Call service
	@Test
	public void shouldCallService() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
							
		when(req.getMethod()).thenReturn("POST");
	    when(req.getRequestURI()).thenReturn("/app/administrator/start");
		when(req.getParameter(RACE_ID)).thenReturn("2200");

		servlet.doPost(req, resp);
		
		verify(ServiceFactory.getInstance().getAdministratorService()).startRace(2200);
	}
	
}