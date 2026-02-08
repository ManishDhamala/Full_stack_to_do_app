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
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
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

        // Verify mocks were called properly
        verify(taskMapper, times(1)).toEntity(inputDto);
        verify(taskRepository, times(1)).save(taskEntity);
        verify(taskMapper, times(1)).toDTO(savedTaskEntity);


    }

    @Test
    void updateTask_AndReturnDto() {

        Long taskId = 1L;

        // Arrange

        // Input Dto for update
        TaskDto inputDto = new TaskDto();
        inputDto.setId(taskId);
        inputDto.setTask("Update Task");
        inputDto.setCompleted(true);

        // Old task
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTask("Old Task");
        existingTask.setCompleted(false);


        // Check after update
        Task updatedTask = new Task();
        updatedTask.setId(1L);
        updatedTask.setTask("Update Task");
        updatedTask.setCompleted(false);

        // Expected return dto
        TaskDto expectedDto = new TaskDto();
        expectedDto.setId(1L);
        expectedDto.setTask("Update Task");
        expectedDto.setCompleted(true);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        when(taskRepository.save(existingTask)).thenReturn(updatedTask);

        when(taskMapper.toDTO(updatedTask)).thenReturn(expectedDto);


        // Act
        TaskDto result = taskService.updateTask(inputDto);

        //Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTask()).isEqualTo("Update Task");
        assertThat(result.getCompleted()).isTrue();


        // Verify mocks were called properly
        verify(taskRepository, times(1)).findById(taskId);
        verify(taskRepository, times(1)).save(existingTask);
        verify(taskMapper, times(1)).toDTO(updatedTask);

    }

    @Test
    void getAllTasks_AndReturnDto() {

        // Arrange
        Task task1 = new Task(1L, "Task 1", true);
        Task task2 = new Task(2L, "Task 2", false);

        List<Task> tasks = Arrays.asList(task2, task1);

        TaskDto expectedTaskDto1 = new TaskDto(1L, "Task 1", true);
        TaskDto expectedTaskDto2 = new TaskDto(2L, "Task 2", false);


        Sort expectedSort = Sort.by(Sort.Direction.DESC, "id");

        // Mock repository to return desc sorted tasks
        when(taskRepository.findAll(expectedSort)).thenReturn(tasks);

        when(taskMapper.toDTO(task1)).thenReturn(expectedTaskDto1);
        when(taskMapper.toDTO(task2)).thenReturn(expectedTaskDto2);

        //Act
        List<TaskDto> results = taskService.getAllTasks();

        // Assert
        assertThat(results).isNotNull();

        assertThat(results.get(0).getId()).isEqualTo(2L);
        assertThat(results.get(0).getTask()).isEqualTo("Task 2");
        assertThat(results.get(0).getCompleted()).isFalse();

        assertThat(results.get(1).getId()).isEqualTo(1L);
        assertThat(results.get(1).getTask()).isEqualTo("Task 1");
        assertThat(results.get(1).getCompleted()).isTrue();


        verify(taskMapper, times(1)).toDTO(task1);
        verify(taskMapper, times(1)).toDTO(task2);

    }

    @Test
    void deleteTask_ShouldDeleteSuccessfully() {

        // ARRANGE
        Long taskId = 1L;

        // Mock that task exists
        when(taskRepository.existsById(taskId)).thenReturn(true);

        // Mock deleteById to do nothing (void method)
        doNothing().when(taskRepository).deleteById(taskId);

        // ACT & ASSERT (no exception should be thrown)
        assertThatCode(() -> taskService.deleteTask(taskId))
                .doesNotThrowAnyException();

        // VERIFY
        verify(taskRepository, times(1)).existsById(taskId);
        verify(taskRepository, times(1)).deleteById(taskId);

    }


}