package com.example.demo.repository;
import com.example.demo.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface QuestionRepository extends JpaRepository<Question,Integer>{
	List<Question> findBySubjectContainingIgnoreCase(String subject);
	List<Question> findByContentContainingIgnoreCase(String content);
}
