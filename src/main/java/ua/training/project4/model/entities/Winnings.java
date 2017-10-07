package ua.training.project4.model.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Winnings {
	
	private String message;
	private Integer amount;

}
