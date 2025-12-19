package com.project.todoappbackend.service;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.mapper.TaskManualMapper;
import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskManualMapper taskManualMapper;

    public TaskDto createNewTask(TaskDto taskDto) {
        // Dto -> Entity
        Task task = taskManualMapper.toEntity(taskDto);
        // Save task(Entity) in the database
        Task savedTask = taskRepository.save(task);
        log.info("Task Created");
        // Entity -> Dto
        return taskManualMapper.toDto(savedTask);
    }

    public Task updateTask(Task task) {
        log.info("Task updated");
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        log.info("Fetched All Tasks");
        return taskRepository.findAll();
    }

    public List<Task> findAllCompletedTasks() {
        log.info("Fetched All Completed Tasks");
        return taskRepository.findByCompletedTrue();
    }

    public List<Task> findAllUncompletedTasks() {
        log.info("Fetched All Uncompleted Tasks");
        return taskRepository.findByCompletedFalse();
    }

    public void deleteTask(Long id) {
        log.info("Task Deleted");
        taskRepository.deleteById(id);
    }


}
