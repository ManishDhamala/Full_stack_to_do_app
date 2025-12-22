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
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<TaskDto>> getCompletedTask() {
        return ResponseEntity.ok(taskService.findAllCompletedTasks());
    }

    @GetMapping("/uncompleted")
    public ResponseEntity<List<TaskDto>> getUncompletedTask() {
        return ResponseEntity.ok(taskService.findAllUncompletedTasks());
    }

    @PostMapping("/")
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
        TaskDto savedTask = taskService.createNewTask(taskDto);
        return ResponseEntity.ok(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteTask(@PathVariable Long id) {  // Binding the value of {id} from the URL to the id parameter in the method
        taskService.deleteTask(id);
        return ResponseEntity.ok(true);
    }

    @PutMapping({"/"})
    public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDto) {
        TaskDto updatedTask = taskService.updateTask(taskDto);
        return ResponseEntity.ok(updatedTask);
    }


}
