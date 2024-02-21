package com.example.demo.model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
	@Column(columnDefinition="TEXT" )
	private String content;

	@CreationTimestamp
	private LocalDateTime createDate;
	
	@ManyToOne
	private Question question;
	
	//ManyToOne 
	//private User user;
	@Builder
	public Answer(String content) {
		this.content = content;
	}
	
}
