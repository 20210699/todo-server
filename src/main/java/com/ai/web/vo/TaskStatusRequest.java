package com.ai.web.vo;

import com.ai.constants.TaskStatus;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TaskStatusRequest {
    private TaskStatus status;
}
