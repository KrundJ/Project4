package ua.training.project4.model.service;

import static org.mockito.Mockito.mock;

import java.util.Objects;

import ua.training.project4.Config;

/*
 * This required, otherwise stack overflow error because
 *  services contains references on each other    
 */
public class ServiceFactory {
	
	private static ServiceFactory instance;
	
	private AdministratorService administratorMock = mock(AdministratorService.class);

	private HorseService horseMock = mock(HorseService.class);
		
	private ServiceFactory() {}
			
	public static ServiceFactory getInstance() {
		if (Objects.isNull(instance)) {
			instance = new ServiceFactory();
		}
		return instance;
	}
	
	public AdministratorService getAdministratorService() {
		if (Config.getInstance().isTesting())
			return administratorMock;
		return AdministratorService.getInstance();
	}
	
	public BookmakerService getBookmakerService() {
		return BookmakerService.getInstance();
	}
	
	public HorseService getHorseService() {
		if (Config.getInstance().isTesting())
			return horseMock;
		return HorseService.getInstance();
	}
	
	public UserService getUserService() {
		return UserService.getInstance();
	}
	
}
