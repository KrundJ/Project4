package ua.training.project4.controller.commands.administrator;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import ua.training.project4.controller.Servlet;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceDistance;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;
import ua.training.project4.model.service.ServiceFactory;

import static ua.training.project4.view.Constants.*;
import static org.hamcrest.collection.IsMapContaining.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

public class OrganizeRaceTest {

	private Servlet servlet;
	{
		servlet = new Servlet();
		servlet.init();
	}
	
	private Map<String, String> errors;
	
	//Invalid horse names
	@Test
	public void shouldReturnBackToEditor() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher disp = mock(RequestDispatcher.class);
		
		String[] horseNames = {"sameName", "sameName", "sameName", "sameName", "sameName"};
		String distance = RaceDistance.RACE_ON_2400m.name();
		String date = "Jan 23, 1996";
		
				
	    when(req.getMethod()).thenReturn("POST");
	    when(req.getRequestURI()).thenReturn("/app/administrator/new");
	    when(req.getRequestDispatcher(anyString())).thenReturn(disp);
		doAnswer(new Answer<Void>() {
		    public Void answer(InvocationOnMock invocation) {
		      Object[] args = invocation.getArguments();
		      for(Object o : args) {
		    	 if (o.getClass().equals(HashMap.class)) 
		    		OrganizeRaceTest.this.errors = (Map<String, String>) o; 
		      }
		      return null;
		    }
		}).when(req).setAttribute(same("errors"), any());
	    doNothing().when(disp).forward(req, resp);
		when(req.getParameterValues(HORSE_NAMES)).thenReturn(horseNames);
		when(req.getParameter(DISTANCE)).thenReturn(distance);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(LOCALE)).thenReturn(Locale.US);
		when(req.getParameter(DATE)).thenReturn(date);

		servlet.doPost(req, resp);
		
		verify(disp).forward(req, resp);
		assertThat(errors, hasKey(HORSE_NAMES));
	}

	@Test
	public void shouldCallService() throws Exception {
		HttpServletRequest req = mock(HttpServletRequest.class);
		HttpServletResponse resp = mock(HttpServletResponse.class);
		HttpSession session = mock(HttpSession.class);
		RequestDispatcher disp = mock(RequestDispatcher.class);
		AdministratorService adminMock = ServiceFactory.getInstance().getAdministratorService();
		HorseService horseMock = ServiceFactory.getInstance().getHorseService();
		
		String[] horseNames = {"name1", "name2", "name3", "name4", "name5"};
		String distance = RaceDistance.RACE_ON_2400m.name();
		String date = "Jan 23, 1996";
		
	    when(req.getMethod()).thenReturn("POST");
	    when(req.getRequestURI()).thenReturn("/app/administrator/new");
	    when(req.getRequestDispatcher(anyString())).thenReturn(disp);
		doNothing().when(adminMock).organizeRace(any(Race.class));
		when(horseMock.getHorsesByNames(horseNames)).thenReturn(new HashSet<>());
	    doNothing().when(disp).forward(req, resp);
		when(req.getParameterValues(HORSE_NAMES)).thenReturn(horseNames);
		when(req.getParameter(DISTANCE)).thenReturn(distance);
		when(req.getSession()).thenReturn(session);
		when(session.getAttribute(LOCALE)).thenReturn(Locale.US);
		when(req.getParameter(DATE)).thenReturn(date);

		servlet.doPost(req, resp);
				
		verify(adminMock).organizeRace(any(Race.class));
	}
}
