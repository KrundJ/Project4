package ua.training.project4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ua.training.project4.controller.AuthFilter;

import ua.training.project4.model.dao.DAOFactory;
import ua.training.project4.model.entities.Bet;
import ua.training.project4.model.entities.Bet.BetType;
import ua.training.project4.model.entities.Coefficients;
import ua.training.project4.model.entities.Horse;
import ua.training.project4.model.entities.Race;
import ua.training.project4.model.entities.Race.RaceState;
import ua.training.project4.model.entities.User;
import ua.training.project4.model.entities.User.Role;
import ua.training.project4.model.service.AdministratorService;
import ua.training.project4.model.service.HorseService;

class ValidationResult {
	
	public StringBuilder result;
	
//	public static StringBuilder result = new StringBuilder();
	
	public ValidationResult() {
		result = new StringBuilder();
	}

	public static ValidationResult checkA(ValidationResult r) {
		r.result.append("checked A ");
		return r;
	}
	
	public static ValidationResult checkB(ValidationResult r) {
		r.result.append("checked B ");
		return r;
	}
}



public class TEST {

	public static void main(String[] args) {
		
//		new Thread("thread1") {
//			@Override		
//			public void run() {
//				ValidationResult r = new ValidationResult();
//				for(int i = 0; i < 10; i++) {
//					ValidationResult.checkA(r).checkB(r);
//				}
//								
//				System.out.println("From " + this.getName()+ " " + r.result.toString());
//			}
//		}.start();
//		
//		
//		new Thread("thread2") {
//			@Override		
//			public void run() {
//				ValidationResult r = new ValidationResult();
//				
//				for(int i = 0; i < 10; i++) {
//					ValidationResult.checkA(r).checkB(r);
//				}
//				
//				System.out.println("From " + this.getName()+ " " + r.result.toString());
//			}
//		}.start();
		
		
//		Optional<Integer> i = Optional.ofNullable(null);
//		System.out.println(i.orElseThrow(RuntimeException::new));

		//System.out.println(DAOFactory.getInstance().getBetDAO().getBetByID(2000));
		
		
		//Locale l = Locale.forLanguageTag("uk-UA");
//		Locale l = Locale.forLanguageTag("dfafsaf");
//		
//		for(Locale l2 : Locale.getAvailableLocales()) {
//			if (l.equals(l2)) System.out.println("MATCH");
//		}
//		
	
//		System.out.println("/app/administrator/".matches("^/app(/[a-z/\\d]+)/?$"));
		
//		Map<String, Integer> horseNameAndValue = new HashMap<>();
//		{
//			horseNameAndValue.put("aaa", 1);
//			horseNameAndValue.put("bbb", 2);
//			horseNameAndValue.put("ccc", 3);
//		}
//
//		Map<Horse, Integer> raceResults = new HashMap<>();
//		{
//			Horse h = Horse.builder().name("ccc").build();
//			raceResults.put(h, 5);
//			Horse h2 = Horse.builder().name("aaa").build();
//			raceResults.put(h2, 8);
//		}
//		
//		
//		boolean b = raceResults.entrySet().stream()
//		.allMatch(entry -> Objects.nonNull(entry.getValue()));
//		
//		System.out.println(b);
		
//		Race race = DAOFactory.getInstance().getRaceDAO().getRaceByID(1000);
//		
//		Bet bet = Bet.builder().horseName("").betType(BetType.SHOW_BET).build();
//
//			boolean b = race.getRaceResults().entrySet().stream()
//				.filter(e -> e.getKey().getName().equals(bet.getHorseName()))
//				.filter(e -> Arrays.stream(bet.getBetType().getWinPlaces())
//						.anyMatch(p -> p == e.getValue().intValue()))
//				.findAny().isPresent();
//			
//		System.out.println(b);
				
			
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
		
		
//		List<Coefficients> c = DAOFactory.getInstance().getCoefficientsDAO().getCoefficientsForCurrentRaces();
//		System.out.println(c.size());
		
//		List<Race> r = DAOFactory.getInstance().getRaceDAO().getCurrentRaces();
//		System.out.println(r.size());
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

