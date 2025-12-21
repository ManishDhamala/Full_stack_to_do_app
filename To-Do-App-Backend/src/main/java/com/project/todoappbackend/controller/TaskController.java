package com.project.todoappbackend.controller;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.mapper.TaskManualMapper;
import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin(origins = "http://localhost:3000") [Vite Build tool]npx
@Controller
@RequestMapping("/api/v1/tasks")
@CrossOrigin
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<Task>> getCompletedTask() {
        return ResponseEntity.ok(taskService.findAllCompletedTasks());
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<List<Task>> getUncompletedTask() {
        return ResponseEntity.ok(taskService.findAllUncompletedTasks());
    }

    @PostMapping("/")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createNewTask(task);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {  // Binding the value of {id} from the URL to the id parameter in the method
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping({"/{id}"})
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(id, taskDto);
        return ResponseEntity.ok(updatedTask);
    }


}
