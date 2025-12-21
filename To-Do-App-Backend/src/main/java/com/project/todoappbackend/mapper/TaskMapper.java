package com.project.todoappbackend.mapper;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.model.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskDto toDTO(Task task);

    Task toEntity(TaskDto taskDto);

}
