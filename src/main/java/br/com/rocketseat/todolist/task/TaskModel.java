package br.com.rocketseat.todolist.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    private String description;

    @Column(length = 50)
    private String title;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private String priority;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private UUID idUser;
}
