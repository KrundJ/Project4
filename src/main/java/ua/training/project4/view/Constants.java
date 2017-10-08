package ua.training.project4.view;

public final class Constants {
	//JSP param names
	public static final String LOCALE = "locale";
	
	public static final String NEW_LOCALE = "new_lang";
	
	public static final String RACE_ID = "raceID";
	
	public static final String HORSE_NAMES = "horseNames";
	
	public static final String HORSE_NAME = "horseName";
	
	public static final String DATE = "date";
	
	public static final String DISTANCE = "distance";
	
	public static final String RACE_RESULTS = "results";
	
	public static final String RACES = "races";
	
	public static final String RACE = "race";
	
	public static final String HORSES = "horses";
	
	public static final String COMMAND_URL = "commandURL";
	
	public static final String COEFFICIENTS = "coefficients";
	
	public static final String BET = "bet";
	
	public static final String BET_ID = "betID";
	
	public static final String BET_AMOUNT = "amount";
	
	public static final String BET_TYPE = "betType";
	
	public static final String MESSAGE = "message";
	
	public static final String LOGIN = "login";
	
	public static final String PASSWORD = "password";
	
	public static final String LOGIN_MAIN_ERROR = "loginPasswordDontMatch";
	
	public static final String REGISTRATION_MAIN_ERROR = "userExisits";
	
	//VALIDATION
	public static final String VALIDATION_ERR_RACE_ID = "validation.error.raceID";
	
	public static final String VALIDATION_ERR_BET_ID = "validation.error.betID";
	
	public static final String VALIDATION_ERR_DISTANCE = "validation.error.distance";
	
	public static final String VALIDATION_ERR_DATE = "validation.error.date";
	
	public static final String VALIDATION_ERR_RACE_RESULTS = "validation.error.raceResults";
	
	public static final String VALIDATION_ERR_HORSE_NAMES = "validation.error.horseNames";
	
	public static final String VALIDATION_ERR_HORSE_NAME = "validation.error.horseName";
	
	public static final String VALIDATION_ERR_AMOUNT = "validation.error.amount";
	
	public static final String VALIDATION_ERR_BET_TYPE = "validation.error.betType";
	
	public static final String VALIDATION_ERR_COEFFICIENTS = "validation.error.coefficients";
	
	public static final String VALIDATION_ERR_LOGIN = "validation.error.login";
	
	public static final String VALIDATION_ERR_PASSWORD = "validation.error.password";
		
	//MESSAGES
	public static final String LOGOUT_MSG = "jsp.logout.message";
	
	public static final String LOGIN_ERROR_MSG = "service.error.auth.loginPasswordDontMatch";
	
	public static final String REGISTRATION_ERROR_MSG = "service.error.auth.userExisits";
	
	public static final String REGISTRATION_SUCCESS_MSG = "service.error.auth.registrationSuccess";
	
	public static final String RACE_NOT_FOUND = "service.error.admin.notFound";
	
	public static final String DELETE_ERROR = "service.error.admin.delete";
	
	public static final String START_ERROR = "service.error.admin.start";
	
	public static final String FINISH_ERROR = "service.error.admin.finish";
	
	public static final String NO_COEFFICIENTS = "service.error.admin.noCoefficients";
	
	public static final String INVALID_STATE = "service.error.admin.invalidState";
	
	public static final String HORSE_NOT_IN_RACE = "service.error.admin.horseNotInRace";
	
	public static final String COEFFICIENTS_NOT_FOUND = "service.error.bookm.coefficientsNotFound";
	
	public static final String BET_NOT_FOUND = "service.error.user.betNotFound";
	
	public static final String BET_NOT_WIN = "service.error.user.betNotWin";
	
	public static final String WINNINGS_RECEIVED = "service.error.user.winningsReceived";
	
	public static final String WINNINGS_MESSAGE = "service.error.user.winningsMessage";
	
	//DAO
	public static final String CREATE_BET_ERR = "dao.errors.createBet";
	
	public static final String UPDATE_BET_ERR = "dao.errors.updateBet";
	
	public static final String LIST_RACE_ERR = "dao.errors.listRace";
	
	public static final String CREATE_RACE_ERR = "dao.errors.createRace";
	
	public static final String UPDATE_RACE_ERR = "dao.errors.updateRace";
	
	public static final String DELETE_RACE_ERR = "dao.errors.deleteRace";
	
	public static final String CREATE_USER_ERR = "dao.errors.createUser";
	
	public static final String COEFFICIENTS_GET_ERR = "dao.errors.listCoefficients";
	
	public static final String COEFFICIENTS_SET_ERR = "dao.errors.setCoefficients";
	
	public static final String HORSES_GET_ERR = "dao.errors.listHorses";
	
	private Constants() {}
}

