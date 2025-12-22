package com.project.todoappbackend.mapper;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR // Re-build mapper implementation after
)                                                    // changing field types
public interface TaskMapper {

    @Mapping(source = "id", target = "id") // Explicitly mapping id so that it doesn't get loss
    TaskDto toDTO(Task task);

    @Mapping(source = "id", target = "id")
    Task toEntity(TaskDto taskDto);

}
