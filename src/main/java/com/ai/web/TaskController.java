package com.ai.web;

import com.ai.constants.TaskStatus;
import com.ai.model.Task;
import com.ai.service.TaskService;
import com.ai.web.vo.ResultResponse;
import com.ai.web.vo.TaskRequest;
import com.ai.web.vo.TaskStatusRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    /**
     * 새로운 할 일 추가
     * @param req 추가하고자 하는 할 일
     * @return 추가된 할 일
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskRequest req) {
        var res = this.taskService.add(req.getTitle(), req.getDescription(), req.getDueDate());
        return ResponseEntity.ok(res);
    }

    /**
     * 특정 마감일에 해당하는 할 일 목록 반환
     *
     * @param dueDate 할 일의 마감일
     * @return 마감일에 해당하는 할 일 목록
     */
    @GetMapping
    public ResponseEntity<List<Task>> getTasksByDueDate(Optional<String> dueDate) {
        List<Task> res;

        if (dueDate.isPresent()) {
            res = this.taskService.getByDueDate(dueDate.get());
        } else {
            res = this.taskService.getAll();
        }

        return ResponseEntity.ok(res);
    }

    /**
     * 특정 ID에 해당하는 할 일을 조회
     *
     * @param id 할 일 ID
     * @return ID에 해당하는 할 일 객체
     */
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id) {
        var res = this.taskService.getOne(id);
        return ResponseEntity.ok(res);
    }

    /**
     * 특정 상태에 해당하는 할 일 목록을 반화
     *
     * @param status 할 일 상태
     * @return 상태에 해당하는 할 일 목록
     */
    @GetMapping("status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable TaskStatus status) {
        var res = this.taskService.getAllByStatus(status);
        return ResponseEntity.ok(res);
    }

    /**
     * 특정 ID에 해당하는 할 일을 수정
     *
     * @param id 할 일 ID
     * @param req 수정할 할 일 정보
     * @return 수정된 할 일 객체
     */
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id,
                                           @RequestBody TaskRequest req) {
        var res = this.taskService.update(id,
                req.getTitle(),
                req.getDescription(),
                req.getDueDate());
        return ResponseEntity.ok(res);
    }

    /**
     * 특정 ID에 해당하는 할 일의 상태를 수정
     *
     * @param id 할 일 ID
     * @param req 수정할 할 일 상태 정보
     * @return 수정된 할 일 객체
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id,
                                                 @RequestBody TaskStatusRequest req) {
        var res = this.taskService.updateStatus(id, req.getStatus());
        return ResponseEntity.ok(res);
    }

    /**
     * 특정 ID에 해당하는 할 일 삭제
     *
     * @param id 삭제할 할 일 ID
     * @return 삭제 결과를 담은 응답 객체
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ResultResponse> deleteTask(@PathVariable Long id) {
        var res = this.taskService.delete(id);
        return ResponseEntity.ok(new ResultResponse(res));
    }

    @GetMapping("/status")
    public ResponseEntity<TaskStatus[]> getAllStatus() {
        var status = TaskStatus.values();
        return ResponseEntity.ok(status);
    }
}
