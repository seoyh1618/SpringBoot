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
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@NoArgsConstructor

public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length=100)
	private String subject;
	
	@Column(columnDefinition="TEXT")
	private String content;
	
	@CreationTimestamp
	private LocalDateTime createTime;
	@JsonIgnore
	@OneToMany(mappedBy="question",cascade=CascadeType.REMOVE)
	private List<Answer> answers = new ArrayList<>();
	
	//@ManyToOne
	//private String userId;
	
	@Builder
	public Question(String subject, String content) {
		this.subject = subject;
		this.content = content;
		this.answers = new ArrayList<>();
	}
	
	
}
