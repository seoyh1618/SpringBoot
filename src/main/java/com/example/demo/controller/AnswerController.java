package com.example.demo.controller;
import com.example.demo.service.AnswerService;
import com.example.demo.dto.AnswerDTOs;
import com.example.demo.model.Answer;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;

@RestController
@RequestMapping("/apis/answers")
public class AnswerController {
	
	private AnswerService answerService;
	
	@Autowired
	public AnswerController(AnswerService answerService) {
		this.answerService = answerService;
	}
	
	
	//Setter 1.Insert_one / 2. Insert_Many 
	
	@PostMapping("/insert_one")
	public ResponseEntity<Object> InsertOne(@RequestBody @Valid AnswerDTOs.AnswerInsertRequest answerReqeust,BindingResult bindingResult) {
		//Validation Parameters
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}

		try {
			Answer savedAnswer = answerService.insertOneService(answerReqeust);
		
			return  new ResponseEntity<>(savedAnswer,HttpStatus.CREATED);
		}catch(DataAccessException e ) {
			throw new RuntimeException("Database Access Exception",e);
		}catch(Exception e) {
			throw new RuntimeException("Can Not Saved Question Data",e);
		}

	}
	
	@PostMapping("/insert_many")
	public ResponseEntity<Object> InsertMany(@RequestBody @Valid List<AnswerDTOs.AnswerInsertRequest> answerRequest, BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}
		
		try {
			List<Answer> savedAnswers = answerService.insertManyService(answerRequest);
			return new ResponseEntity<>(savedAnswers,HttpStatus.CREATED);
		}catch(DataAccessException e) {
			System.out.println("Data Access Exception"+e.getMessage());
			throw new RuntimeException("Data Access Exception",e);
		}catch(Exception e) {
			System.out.println("Cannot saved Answers in Database"+e.getMessage());
			throw new RuntimeException("Cannot saved Answers in Database",e);
		}
	}

	//GET
	@GetMapping("/search")
	public ResponseEntity<Object> getAnswer(@RequestParam(name="content",required=false) String content,
			@RequestParam(name="questionId",required=false) Integer questionId){
		
		if(content != null) {
			List<Answer> searchedAnswers = answerService.getByContent(content);
			return new ResponseEntity<>(searchedAnswers,HttpStatus.OK);
			
		}
		else if(questionId != null) {
			List<Answer> searchedAnswers = answerService.getByQuestionId(questionId);
			return new ResponseEntity<>(searchedAnswers,HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Content or QuestionId should be Containing",HttpStatus.BAD_REQUEST);
		}
		
	}

	//PUT
	@PutMapping("/update")
	public ResponseEntity<Object> updateAnswer(@RequestBody @Valid AnswerDTOs.AnswerUpdateRequest answerUpdateRequest,BindingResult bindingResult ){
		
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());

			return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}
		try {
			Answer updatedAnswer = answerService.update(answerUpdateRequest);
			return new ResponseEntity<>(updatedAnswer,HttpStatus.OK);
			
		}catch(DataAccessException e){
			System.out.println("Data Access Exception"+e.getMessage());
			throw new RuntimeException("Data Access Exception",e);
		}catch(Exception e) {
			System.out.println("Cannot Update Answer in Database"+e.getMessage());
			throw new RuntimeException("Cannot Update Answer in Database",e);
		}
	}
	
	//DELETE
	@DeleteMapping("/delete")
	public ResponseEntity<Object> deleteAnswer(@RequestParam(name="id") Integer id){
		if(id==null) {
			return new ResponseEntity<>("Id cannot be null",HttpStatus.BAD_REQUEST);
		} 
		try {
			Answer deletedAnswer = answerService.delete(id);
			return new ResponseEntity<>(deletedAnswer,HttpStatus.OK);
		}catch(DataAccessException e){
			System.out.println("Data Access Exception"+e.getMessage());
			throw new RuntimeException("Data Access Exception",e);
		}catch(Exception e) {
			System.out.println("Cannot Update Answer in Database"+e.getMessage());
			throw new RuntimeException("Cannot Update Answer in Database",e);
		}

	} 
}
