package com.ai.persist;

import com.ai.constants.TaskStatus;
import com.ai.model.Task;
import com.ai.persist.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    List<TaskEntity> findAllByDueDate(Date dueDate);
    List<TaskEntity> findAllByStatus(TaskStatus status);
}
