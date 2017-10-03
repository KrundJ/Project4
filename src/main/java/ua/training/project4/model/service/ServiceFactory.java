package ua.training.project4.model.service;

import java.util.Objects;

/*
 * This required, otherwise stack overflow error because
 *  services contains references on each other    
 */
public class ServiceFactory {
	
	private static ServiceFactory instance;
	
	private ServiceFactory() {}
			
	public static ServiceFactory getInstance() {
		if (Objects.isNull(instance)) {
			instance = new ServiceFactory();
		}
		return instance;
	}
	
	public AdministratorService getAdministratorService() {
		return AdministratorService.getInstance();
	}
	
	public BookmakerService getBookmakerService() {
		return BookmakerService.getInstance();
	}
	
	public HorseService getHorseService() {
		return HorseService.getInstance();
	}
	
	public UserService getUserService() {
		return UserService.getInstance();
	}
	
}
