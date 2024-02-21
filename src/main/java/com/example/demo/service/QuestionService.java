package com.example.demo.service;
import com.example.demo.service.QuestionService;
import com.example.demo.dto.QuestionDTOs;
import com.example.demo.model.Question;
import com.example.demo.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

	private QuestionRepository questionRepository;
	
	@Autowired
	public QuestionService(QuestionRepository questionRepository) {
		this.questionRepository=questionRepository;
	}
	
	public Question InsertOneService(QuestionDTOs.QuestionInsertDTO questionDTO) {
		try {
			Question question = new Question();
			question.setSubject(questionDTO.getSubject());
			question.setContent(questionDTO.getContent());
			
			return questionRepository.save(question);
		}catch(DataAccessException e) {
			System.out.println("Database Access Exception"+e.getMessage());
			throw e;
		}
		
	}
	
	public List<Question> InsertAllService(List<QuestionDTOs.QuestionInsertDTO> questionDTO){
		return questionDTO.stream()
				.map(this::InsertOneService)
				.collect(Collectors.toList());
	}
	
	public List<Question> getAll(){
		try{
			return questionRepository.findAll();	
		}catch(DataAccessException e) {
			System.out.println("Database Access Exception"+e.getMessage());
			throw e;
		}
	}
	public Question getById(Integer id){
		try{
			return questionRepository.findById(id).orElseThrow(()->new RuntimeException(" Not Found ID in Database"));
		}catch(DataAccessException e) {
			System.out.println("Database Access Exception"+e.getMessage());
			throw e;
		}
	}
	
	public List<Question> getBySubject(String subject){
		try{
			return Optional.ofNullable(questionRepository.findBySubjectContainingIgnoreCase(subject)).orElse(Collections.emptyList());
		}catch(DataAccessException e) {
			System.out.println("Database Access Exception"+e.getMessage());
			throw e;
		}
	}
	
	public List<Question> getByContent(String content){
		try{
			return Optional.ofNullable(questionRepository.findByContentContainingIgnoreCase(content)).orElse(Collections.emptyList());
		}catch(DataAccessException e) {
			System.out.println("Database Access Exception"+e.getMessage());
			throw e;
		}
	}
	public Question updateQuestion(QuestionDTOs.QuestionUpdateDTO updateRequest) {
		
		try {
			Question question = questionRepository.findById(updateRequest.getId()).orElseThrow(()-> new RuntimeException("Cannot Founded by id in database"));
			
			if(updateRequest.isUpdateContent()) {
				question.setContent(updateRequest.getUpdateContent());
			}
			if(updateRequest.isUpdateSubject()) {
				question.setSubject(updateRequest.getUpdateSubject());
			}
			
			return questionRepository.save(question);
		}catch(DataAccessException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
	}
	
	public Question remove(Integer id) {
		try {
			Question deletedQuestion = questionRepository.findById(id).orElseThrow(()->new RuntimeException("An object with the given ID does not exist in the current database."));
			questionRepository.deleteById(id);
		
			boolean existAfterDeletion = questionRepository.existsById(id);
			if(!existAfterDeletion) {
				return deletedQuestion;
			}else {
				throw new  RuntimeException("Failed to delete question in Database");
			}

		}catch(DataAccessException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}

	}
	
	
}
