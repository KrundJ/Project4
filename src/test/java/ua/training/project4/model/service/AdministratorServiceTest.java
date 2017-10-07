package ua.training.project4.model.service;


import static org.mockito.Mockito.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.dao.RaceDAO;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AdministratorServiceTest {

	private AdministratorService adminService = AdministratorService.getInstance();
	
	@Rule
	public ExpectedException e = ExpectedException.none();
	
	//Attempting to finish finished race
	@Test
	public void shouldThrowException() {
		e.expect(ServiceException.class);
				
		int raceID = 10;
		Optional<Race> raceOptional = Optional.of(
				Race.builder().state(RaceState.FINISHED).build());
		DAOFactory factoryMock = DAOFactory.getInstance();
		RaceDAO raceDaoMock = mock(RaceDAO.class);
		when(factoryMock.getRaceDAO()).thenReturn(raceDaoMock);
		when(raceDaoMock.getRaceByID(raceID)).thenReturn(raceOptional);
		
		adminService.finishRace(raceID);
	}
	//OK
	@Test
	public void shouldFinishRace() {
		int raceID = 10;
		Optional<Race> raceOptional = Optional.of(
				Race.builder().state(RaceState.STARTED).build());
		DAOFactory factoryMock = DAOFactory.getInstance();
		RaceDAO raceDaoMock = mock(RaceDAO.class);
		when(factoryMock.getRaceDAO()).thenReturn(raceDaoMock);
		when(raceDaoMock.getRaceByID(raceID)).thenReturn(raceOptional);
		doNothing().when(raceDaoMock).update(any());
				
		adminService.finishRace(raceID);
		
		verify(raceDaoMock).update(any());
	}
	
	@Test
	public void shouldSetResults() {
		Map<Horse, Integer> raceResults = new HashMap<>();
		Set<Horse> horses = new HashSet<>(); 
		Map<Integer, String> newRaceResults = new HashMap<>();
		Map<Horse, Integer> expectedRaceResults = new HashMap<>();
		Horse horse;
		for(int i = 1; i <= Race.NUMBER_OF_HORSES_IN_RACE; i++) {
			horse = Horse.builder().name("horse" + i).build();
			raceResults.put(horse, null);
			horses.add(horse);
			newRaceResults.put(i, horse.getName());
			expectedRaceResults.put(horse, i);
		}
		Optional<Race> raceOptional = Optional.of(
				Race.builder()
				.ID(100)
				.raceResults(raceResults)
				.state(RaceState.STARTED)
				.build());
		DAOFactory factoryMock = DAOFactory.getInstance();
		RaceDAO raceDaoMock = mock(RaceDAO.class);
		HorseService horseMock = ServiceFactory.getInstance().getHorseService();
		when(factoryMock.getRaceDAO()).thenReturn(raceDaoMock);
		when(raceDaoMock.getRaceByID(raceOptional.get().getID())).thenReturn(raceOptional);
		when(horseMock.getHorsesByNames(any())).thenReturn(horses);
		doNothing().when(raceDaoMock).update(any());

		adminService.saveRaceResults(raceOptional.get().getID(), newRaceResults);
		
		Race expectedRace = raceOptional.get();
		expectedRace.setState(RaceState.FINISHED);
		expectedRace.setRaceResults(expectedRaceResults);
		
		verify(raceDaoMock).update(expectedRace);
		
	}
		
}
