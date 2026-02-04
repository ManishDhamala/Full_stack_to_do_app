package com.project.todoappbackend.service;

import com.project.todoappbackend.dto.TaskDto;
import com.project.todoappbackend.mapper.TaskMapper;
import com.project.todoappbackend.model.Task;
import com.project.todoappbackend.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createNewTask_ShouldSaveTaskAndReturnDto() {

        // Arrange
        TaskDto inputDto = new TaskDto();
        inputDto.setTask("Test task");
        inputDto.setCompleted(false);

        Task taskEntity = new Task();
        taskEntity.setTask("Test task");
        taskEntity.setCompleted(false);

        Task savedTaskEntity = new Task();
        savedTaskEntity.setId(1010L);
        savedTaskEntity.setTask("Test task");
        savedTaskEntity.setCompleted(false);

        TaskDto expectedDto = new TaskDto();
        expectedDto.setId(1010L);
        expectedDto.setTask("Test task");
        expectedDto.setCompleted(false);

        when(taskMapper.toEntity(inputDto)).thenReturn(taskEntity);

        when(taskRepository.save(taskEntity)).thenReturn(savedTaskEntity);

        when(taskMapper.toDTO(savedTaskEntity)).thenReturn(expectedDto);

        // Act
        TaskDto result = taskService.createNewTask(inputDto);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1010L);
        assertThat(result.getTask()).isEqualTo("Test task");
        assertThat(result.getCompleted()).isFalse();

        verify(taskMapper, times(1)).toEntity(inputDto);
        verify(taskRepository, times(1)).save(taskEntity);
        verify(taskMapper, times(1)).toDTO(savedTaskEntity);


    }

    @Test
    void updateTask() {
    }

    @Test
    void getAllTasks() {
    }

    @Test
    void deleteTask() {
    }
}