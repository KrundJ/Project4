package ua.training.project4.model.service;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
	
	private String bundleKey;
	private String data;
	
	public ServiceException(String bundleKey, String data) {
		this.bundleKey = bundleKey;
		this.data = data;
	}
}
