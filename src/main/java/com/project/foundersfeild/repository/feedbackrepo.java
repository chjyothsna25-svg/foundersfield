package com.project.foundersfeild.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.foundersfeild.model.Feedback;
import com.project.foundersfeild.model.ideas;

public interface feedbackrepo extends JpaRepository<Feedback,Long>{
	List<Feedback> findByIdea(ideas idea);
	List<Feedback> findByIdeaOrderByCreatedAtDesc(ideas idea);
}
