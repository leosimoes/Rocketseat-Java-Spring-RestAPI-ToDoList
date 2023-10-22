package br.com.rocketseat.todolist.task;

import br.com.rocketseat.todolist.error.InvalidDateException;
import br.com.rocketseat.todolist.error.TaskNotAuthorizedForUserException;
import br.com.rocketseat.todolist.error.TaskNotFoundException;
import br.com.rocketseat.todolist.utils.ObjectUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private ITaskRepository iTaskRepository;

    private ObjectUtilities objectUtilities;

    @Autowired
    public TaskService(ITaskRepository iTaskRepository, ObjectUtilities objectUtilities){
        this.iTaskRepository = iTaskRepository;
        this.objectUtilities = objectUtilities;
    }

    public TaskModel create(TaskModel taskModel, UUID idUser) {
        taskModel.setIdUser(idUser);

        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            throw new InvalidDateException("The start/end date must be greater than the current date.");
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            throw new InvalidDateException("The start date must be less than the end date.");
        }

        return this.iTaskRepository.save(taskModel);
    }

    public List<TaskModel> readAll(UUID idUser) {
        return this.iTaskRepository.findByIdUser((UUID) idUser);
    }

    public TaskModel update(TaskModel taskModelNew, UUID id, UUID idUser) {
        Optional<TaskModel> taskModelOpt = this.iTaskRepository.findById(id);

        if(taskModelOpt.isEmpty()) {
            throw new TaskNotFoundException("No task with id " + id.toString() + " was found.");
        }

        TaskModel taskModelOld = taskModelOpt.get();

        if(!taskModelOld.getIdUser().equals(idUser)) {
            throw new TaskNotAuthorizedForUserException(
                    "Task with id " + taskModelOld.getId().toString()
                    + "not authorized for user with id " + idUser.toString()
                    + ".");
        }

        objectUtilities.copyNonNullProperties(taskModelNew, taskModelOld);

        return this.iTaskRepository.save(taskModelOld);
    }
}
