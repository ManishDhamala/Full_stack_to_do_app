package com.project.todoappbackend.service;

import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Task createNewTask(Task task){
        return taskRepository.save(task);
    }

    public List<Task> getAllTask(){
        return taskRepository.findAll();
    }

    public List<Task> findAllCompletedTasks(){
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllUncompletedTasks(){
        return taskRepository.findByCompletedFalse();
    }

    public void deleteTask(Long id){
        taskRepository.deleteById(id);
    }


}
