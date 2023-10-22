package br.com.rocketseat.todolist.task;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todolist/tasks/")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    @Parameter(
            name = "credentials",
            description = "Authentication header",
            in = ParameterIn.HEADER,
            required = true,
            content = @Content(schema = @Schema(type = "string"))
    )
    @PostMapping
    public ResponseEntity<TaskModel> create(@RequestBody TaskModel taskModel,
                                            HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return ResponseEntity.ok(taskService.create(taskModel, idUser));
    }

    @Parameter(
            name = "credentials",
            description = "Authentication header",
            in = ParameterIn.HEADER,
            required = true,
            content = @Content(schema = @Schema(type = "string"))
    )
    @GetMapping
    public List<TaskModel> readAll(HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return taskService.readAll(idUser);
    }

    @Parameter(
            name = "credentials",
            description = "Authentication header",
            in = ParameterIn.HEADER,
            required = true,
            content = @Content(schema = @Schema(type = "string"))
    )
    @PutMapping("/{id}")
    public ResponseEntity<TaskModel> read(@RequestBody TaskModel taskModel,
                                          @PathVariable("id") UUID id,
                                          HttpServletRequest request){
        UUID idUser = (UUID) request.getAttribute("idUser");
        return ResponseEntity.ok(taskService.update(taskModel, id, idUser));
    }

}
