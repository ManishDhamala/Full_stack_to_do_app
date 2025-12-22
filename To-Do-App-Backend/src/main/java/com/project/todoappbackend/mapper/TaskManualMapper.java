package com.project.todoappbackend.mapper;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.model.Task;
import org.springframework.stereotype.Component;

@Component
public class TaskManualMapper {

    public TaskDto toDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setTask(task.getTask());
        dto.setCompleted(task.getCompleted());
        return dto;
    }

    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTask(taskDto.getTask());
        task.setCompleted(taskDto.getCompleted());
        return task;
    }

}
