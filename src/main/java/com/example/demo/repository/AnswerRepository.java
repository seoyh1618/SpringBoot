package com.example.demo.repository;
import java.util.List;
import com.example.demo.model.Answer;
import com.example.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AnswerRepository extends JpaRepository<Answer,Integer> {
	List<Answer> findByQuestion(Question questionId);
	List<Answer> findByContentContainingIgnoreCase(String content);
	Answer findByQuestionAndId(Question questionId,Integer id);
}
