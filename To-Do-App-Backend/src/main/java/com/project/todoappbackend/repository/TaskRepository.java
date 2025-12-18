package com.project.todoappbackend.repository;

import com.project.todoappbackend.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByCompletedTrue();
    List<Task> findByCompletedFalse();
    List<Task> findAll();
    Task getById(Long id);


}
