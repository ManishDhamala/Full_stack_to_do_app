package com.project.todoappbackend.service;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.mapper.TaskMapper;
import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDto createNewTask(TaskDto taskDto) {

        Task task = taskMapper.toEntity(taskDto);
        Task savedTask = taskRepository.save(task);
       // log.info("Task Created");

        return taskMapper.toDTO(savedTask);
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.getId()).orElseThrow(() -> new RuntimeException("No task found"));
        task.setTask(taskDto.getTask());
        task.setCompleted(taskDto.getCompleted());
        Task updatedTask = taskRepository.save(task);

     //   log.info("Task Updated");
        return taskMapper.toDTO(updatedTask);
    }

    public List<TaskDto> getAllTasks() {
        List<TaskDto> tasks = taskRepository.findAll(
                        Sort.by(Sort.Direction.DESC, "id") // Sorting task on desc order based on id
                ).stream()
                .map(task -> taskMapper.toDTO(task))
                .toList();
      //  log.info("Fetched All tasks");
        return tasks;
    }

    @Cacheable("CompletedTasks")
    public List<TaskDto> findAllCompletedTasks() {
        List<TaskDto> completedTasks = taskRepository.findByCompletedTrue()
                .stream()
                .map(task -> taskMapper.toDTO(task))
                .toList();
     //   log.info("Fetched All Completed Tasks");
        return completedTasks;
    }

    public List<TaskDto> findAllUncompletedTasks() {
        List<TaskDto> uncompletedTasks = taskRepository.findByCompletedFalse()
                .stream()
                .map(task -> taskMapper.toDTO(task))
                .toList();
      //  log.info("Fetched All UnCompleted Tasks");
        return uncompletedTasks;

    }

    public void deleteTask(Long id) {

        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
      //  log.info("Task Deleted");
    }


}
