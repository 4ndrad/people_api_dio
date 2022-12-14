package one.digitalinnovation.personapi.dto.response;

import lombok.Builder;

@Builder
public class MessageResponseDTO {
	
	private String message;
	
	public MessageResponseDTO() {
		
	}

	public MessageResponseDTO(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
