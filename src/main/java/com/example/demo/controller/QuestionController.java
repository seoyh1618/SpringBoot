package com.example.demo.controller;
import com.example.demo.service.QuestionService;
import com.example.demo.dto.QuestionDTOs;
import com.example.demo.model.Question;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import java.util.stream.Collectors;
import java.util.List;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;

@RestController
@RequestMapping("/apis/question")
public class QuestionController {
	
	private QuestionService questionSerivce;
	
	@Autowired
	public QuestionController(QuestionService questionSerivce){
		this.questionSerivce=questionSerivce;
	}
	
	/*
	 *  POST METHOD
	 */
	
	// Insert Question Data in Database
	@PostMapping("/insert_one")
	public ResponseEntity<Object> questionInsertOne(@RequestBody @Valid QuestionDTOs.QuestionInsertDTO questionDto, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}
		try {
			Question savedQuestion = questionSerivce.InsertOneService(questionDto);
			return  new ResponseEntity<>(savedQuestion,HttpStatus.CREATED);
		}catch(DataAccessException e ) {
			throw new RuntimeException("Database Access Exception",e);
		}catch(Exception e) {
			throw new RuntimeException("Can Not Saved Question Data",e);
		}
		
	}
	
	// Insert Question Datas in Database
	@PostMapping("/insert_all")
	public ResponseEntity<Object> questionInsertAll(@RequestBody @Valid List<QuestionDTOs.QuestionInsertDTO> questionDto,BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			List<String> errors = bindingResult.getAllErrors()
					.stream()
					.map(ObjectError::getDefaultMessage)
					.collect(Collectors.toList());
			
			return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
		}
		try {
			List<Question> savedQuestions = questionSerivce.InsertAllService(questionDto);
			return new ResponseEntity<>(savedQuestions,HttpStatus.CREATED);
		}catch(DataAccessException e){
			throw new RuntimeException("DataBase Access Exception ",e);
		}catch(Exception e) {
			throw new RuntimeException("Can not Saved Question Datas ",e);
		}
	}
	
	/*
	 * GET METHOD
	 */
	// GET ALL Question Data
	@GetMapping("/getAll")
	public ResponseEntity<Object> getQuestions(){
		try {
			List<Question> searchedQuestions = questionSerivce.getAll();	
			return new ResponseEntity(searchedQuestions,HttpStatus.OK);
		}catch(DataAccessException e) {
			throw new RuntimeException("Database Access exptions",e);
		}catch(Exception e) {
			throw new RuntimeException("Can not Search QuestionData in Database",e);
		}
	}
	
	// GET ONE Question Data ( ID, User, Subject, Content )
	@GetMapping("/search")
	public ResponseEntity<Question> searchQuestion(
			@RequestParam(name="id",required = false) Integer id,
			@RequestParam(name="subject",required = false) String subject,
			@RequestParam(name="content",required = false) String content
			){
		if(id != null) {
			try {
				System.out.println("Id Branch : "+id);
				Question searchedQuestion = questionSerivce.getById(id);
				return new ResponseEntity<>(searchedQuestion,HttpStatus.OK);	
			}catch(DataAccessException e) {
				throw new RuntimeException("DataBase Access Exception",e);
			}catch(Exception e) {
				throw new RuntimeException("Can not Search QuestionData in Database",e);
			}
		}else if(subject != null) {
			try {
				System.out.println("Subject Branch : "+subject);
				List<Question> searchedQuestions = questionSerivce.getBySubject(subject);
				return new ResponseEntity(searchedQuestions,HttpStatus.OK);		
			}catch(DataAccessException e) {
				throw new RuntimeException("DataBase Access Exception",e);
			}catch(Exception e) {
				throw new RuntimeException("Can not Search QuestionData in Database",e);
			}
			
		}else if(content != null) {
			try {
				System.out.println("Content Branch : "+content);
				List<Question> searchedQuestions = questionSerivce.getByContent(content);
				return new ResponseEntity(searchedQuestions,HttpStatus.OK);		
			}catch(DataAccessException e) {
				throw new RuntimeException("DataBase Access Exception",e);
			}catch(Exception e) {
				throw new RuntimeException("Can not Search QuestionData in Database",e);
			}
		}else {
			return new ResponseEntity("Prameters (subject, id, content) Not Found",HttpStatus.BAD_REQUEST);
		}
		
	}

	// PUT Question Data (Subject, Content)
	@PutMapping("/update")
	public ResponseEntity<Object> updateQuestion(@RequestBody @Valid QuestionDTOs.QuestionUpdateDTO updateRequest,BindingResult bindingResult){
		if(updateRequest.isIdEmpty()) {
			return new ResponseEntity<>("ID Should be provided for updated",HttpStatus.BAD_REQUEST);
		}
		
		System.out.println("Is ID Value : "+updateRequest.getId());
		System.out.println("----");
		System.out.println("Is Update Subject Value : "+updateRequest.isUpdateSubject());
		System.out.println("Is Update Subject var Value : "+updateRequest.getUpdateSubject());
		System.out.println("----");
		
		System.out.println("Is Update Content Value : "+updateRequest.isUpdateContent());
		System.out.println("Is Update Subject var Value : "+updateRequest.getUpdateContent());
		
		if (!updateRequest.isUpdateSubject() && !updateRequest.isUpdateContent()) {
			return new ResponseEntity<>("Subject or Content Should be provided for updated",HttpStatus.BAD_REQUEST);
		}
		try {
			Question updatedQuestion = questionSerivce.updateQuestion(updateRequest);
			return new ResponseEntity<>(updatedQuestion,HttpStatus.OK);
		}catch(DataAccessException e) {
			throw new RuntimeException("DataBase Access Exception",e);
		}catch(Exception e) {
			throw new RuntimeException("Can not Update QuestionData in Database",e);
		}
		
		
		
	}

	// Delete Question Data By Id 
	@DeleteMapping("/remove")
	public ResponseEntity<Object> deleteQuestion(@RequestParam(name = "id") Integer id){
		if(id==null) {
			return new ResponseEntity<>("ID should be provided for remove",HttpStatus.BAD_REQUEST);
		}
		try {
			Question removedQuestion = questionSerivce.remove(id);
			return new ResponseEntity<>(removedQuestion,HttpStatus.OK);
		
		}catch(DataAccessException e) {
			throw new RuntimeException("DataBase Access Exception",e);
		}catch(Exception e) {
			throw new RuntimeException("Can not Remove QuestionData in Database",e);
		}
	}

}
