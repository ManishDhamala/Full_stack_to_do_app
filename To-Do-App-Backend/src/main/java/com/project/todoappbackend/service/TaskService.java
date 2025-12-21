package com.project.todoappbackend.service;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.mapper.TaskManualMapper;
import com.project.todoappbackend.mapper.TaskMapper;
import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskManualMapper taskManualMapper;
//  private final TaskMapper taskMapper;

    public Task createNewTask(Task task) {
        Task savedTask = taskRepository.save(task);
        log.info("Task Created");
        return savedTask;
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {

        Task task = taskRepository.findById(id).orElseThrow(() -> new RuntimeException("No task found"));

        task.setTask(taskDto.getTask());
        task.setCompleted(taskDto.isCompleted());

        Task updatedTask = taskRepository.save(task);
        log.info("Task Updated");
        return taskManualMapper.toDto(updatedTask);
    }

    public List<Task> getAllTask() {
        List<Task> tasks = taskRepository.findAll(
                Sort.by(Sort.Direction.DESC, "id")  // Sorting task on desc order based on id
        );
        log.info("Fetched All Tasks");
        return tasks;
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

        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task not found");
        }

        taskRepository.deleteById(id);
        log.info("Task Deleted");
    }


}
