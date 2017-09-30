package ua.training.project4;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import ua.training.project4.controller.AuthFilter;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;
import ua.training.project4.model.service.AdministratorService;


public class TEST {

	public static void main(String[] args) {
		//Locale l = Locale.forLanguageTag("uk-UA");
//		Locale l = Locale.forLanguageTag("dfafsaf");
//		
//		for(Locale l2 : Locale.getAvailableLocales()) {
//			if (l.equals(l2)) System.out.println("MATCH");
//		}
//		
	
		System.out.println("/app/administrator/".matches("^/app(/[a-z/\\d]+)/?$"));
		
//		Map<String, Integer> horseNameAndValue = new HashMap<>();
//		{
//			horseNameAndValue.put("aaa", 1);
//			horseNameAndValue.put("bbb", 2);
//			horseNameAndValue.put("ccc", 3);
//		}
//
//		Map<Horse, Integer> raceResults = new HashMap<>();
//		{
//			Horse h = new Horse();
//			h.setName("ccc");
//			raceResults.put(h, 5);
//			Horse h2 = new Horse();
//			h2.setName("aaa");
//			raceResults.put(h2, 5);
//		}
//		
//		raceResults.keySet().stream()
//		.map(Horse::getName).collect(Collectors.toSet()).forEach(System.out::println);
		
//		horseNameAndValue.keySet().stream()
//		.filter(name -> (! raceResults.keySet().stream()
//			.map(Horse::getName).collect(Collectors.toSet()).contains(name)))
//		.findAny().ifPresent(name -> { throw new RuntimeException("not found"); });
		
//		Map<Horse, Integer> values = raceResults.keySet().stream()
//				.collect(Collectors.toMap(horse -> horse, horse -> horseNameAndValue.get(horse.getName())));
//		
//		for (Entry<Horse, Integer> entry : values.entrySet()) {
//			System.out.println(entry.getKey() + " " + entry.getValue());
//		}
		
		
		Coefficients c = DAOFactory.getInstance().getCoefficientsDAO().getByRaceID(1001);
		System.out.println(c);
//		
//		Set<Horse> h = DAOFactory.getInstance().getHorseDAO().getAllHorses();
//		
//		for (Horse hh : h) {
//			System.out.println(hh);
//		}
		
//		System.out.println(l == null);
//		System.out.println(l.getCountry());
//		System.out.println(l.getDisplayCountry());
//		
//		
//		Locale l2 = null; 
//		for(Locale loc : Locale.getAvailableLocales()) {
//			if (loc.getDisplayCountry().equals("Украина")) {
//				System.out.println(loc.toLanguageTag());
//				System.out.println(loc.getCountry());
//				System.out.println("FOUND");
//				l2 = loc;
//				break;
//			}
//				
//			//System.out.println(loc.getDisplayCountry());
//		}
//		
//		System.out.println(l == l2);
	}
}

