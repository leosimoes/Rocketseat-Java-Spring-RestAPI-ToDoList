package br.com.rocketseat.todolist.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {

    Optional<TaskModel> findById(UUID id);

    List<TaskModel> findByIdUser(UUID id);

}
