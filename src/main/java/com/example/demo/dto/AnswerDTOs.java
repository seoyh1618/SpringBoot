package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;

@Data
public class AnswerDTOs {
	@Data
	public static class AnswerInsertRequest {
		
		@NotNull(message="QuestionId cannot be Null")
		private Integer questionId;
		
		@NotBlank(message="Content cannot be Blank")
		@NotEmpty(message="Content cannot be Empty")
		private String content;
	}
	
	@Data
	public static class AnswerUpdateRequest{
		@NotNull(message="QuestionId cannot be Null")
		private Integer questionId;
		
		@NotNull(message="AnswerId cannot be Null")
		private Integer id;
		
		@NotEmpty(message="Content cannot be Empty")
		@NotBlank(message="Content cannot be Blank")
		private String content;
	}
	
}
