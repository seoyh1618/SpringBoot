package com.example.demo.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;

@Data
public class QuestionDTOs {

	@Data
	public static class QuestionInsertDTO{
		@NotNull(message = "Subject cannot be Null")
		@NotBlank(message = "Subeject cannot be Blank")
		@Size(min=1,max=100,message ="Subject length must be between 1 and 100")
		private String subject;
		
		@NotNull(message = "Content cannot be Null")
		@NotBlank(message = "Content cannot be Blank")
		private String content;
	}
	
	@Data
	public static class QuestionUpdateDTO{
		
		@NotNull(message = "Id cannot be Null")
		private Integer id;
		
		@NotNull(message = "Subject cannot be Null")
		@NotEmpty(message = "Subject cannot be Empty")
		private String updateSubject;
		
		@NotNull(message = "Content cannot be Null")
		@NotEmpty(message = "content cannot be Empty")
		private String updateContent;
		
		public boolean isIdEmpty() {
			return id == null;
		}
		
		public boolean isUpdateSubject() {
			return updateSubject != null && !updateSubject.isEmpty();
		}
		
		public boolean isUpdateContent() {
			return updateContent != null && !updateContent.isEmpty();
		}
	}
	
	
}
