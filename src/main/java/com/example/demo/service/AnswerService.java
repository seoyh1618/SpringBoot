package com.example.demo.service;
import com.example.demo.dto.AnswerDTOs;
import com.example.demo.model.Answer;
import com.example.demo.model.Question;

import com.example.demo.repository.AnswerRepository;
import com.example.demo.repository.QuestionRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@Service
public class AnswerService {
	
	private AnswerRepository answerRepository;
	private QuestionRepository questionRepository;

	@Autowired
	public AnswerService(AnswerRepository answerRepository,QuestionRepository questionRepository) {
		this.answerRepository = answerRepository;
		this.questionRepository=questionRepository;
	} 
	
	public Answer insertOneService(AnswerDTOs.AnswerInsertRequest answerRequest) {
		try {
			Answer newAnswer = new Answer();
			newAnswer.setContent(answerRequest.getContent());
			
			Question searchedQuestion = questionRepository.findById(answerRequest.getQuestionId())
				.orElseThrow(()->new RuntimeException("Cannot Access Question in Database"));
			
			newAnswer.setQuestion(searchedQuestion);
			
			return answerRepository.save(newAnswer);
		}catch(DataAccessException e){
			System.out.println("Data Access Exception"+e.getMessage());
			throw e;
		}catch(Exception e) {
			System.out.println("Cannot Saved AnswerData in Database"+e.getMessage());
			throw e;
		}
	}
	
	public List<Answer> insertManyService(List<AnswerDTOs.AnswerInsertRequest> answerRequest){
		try {
			return	answerRequest
					.stream()
					.map(this::insertOneService)
					.collect(Collectors.toList());
		}catch(DataAccessException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}

	}

	
	public List<Answer> getByContent(String content){	
		return answerRepository.findByContentContainingIgnoreCase(content);
	}
	public List<Answer> getByQuestionId(Integer questionId){
		
		Question searchedQuestion = questionRepository.findById(questionId)
			.orElseThrow(()->new RuntimeException("Cannot Access Question in Database"));
		
		return answerRepository.findByQuestion(searchedQuestion);
	}
	
	public Answer update(AnswerDTOs.AnswerUpdateRequest answerUpdateRequest) {
		
		Question searchedQuestion = questionRepository.findById(answerUpdateRequest.getQuestionId())
				.orElseThrow(()->new RuntimeException("Cannot Found question ID for answer update"));
		try {	
			Answer searchedAnswer = answerRepository.findByQuestionAndId(searchedQuestion,answerUpdateRequest.getId());
			searchedAnswer.setContent(answerUpdateRequest.getContent());

			return answerRepository.save(searchedAnswer);
		}catch(DataAccessException e) {
			throw e;
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	public Answer delete(Integer id) {

		Answer searchedAnswer= answerRepository.findById(id)
				.orElseThrow(()-> new RuntimeException("Cannot Found by id in Database"));
		
		answerRepository.deleteById(id);
		
		boolean isDeleted = !answerRepository.existsById(id);
		
		if(isDeleted) {
			return searchedAnswer;
		}else {
			throw new RuntimeException("Cannot Remove Answer by id in Database");
		}
		
	}

}
