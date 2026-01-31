package com.project.todoappbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {    // JdkSerializationRedisSerializer

    private static final long serialVersionUID = 1L;

    private Long id;
    private String task;
    private Boolean completed;

}
