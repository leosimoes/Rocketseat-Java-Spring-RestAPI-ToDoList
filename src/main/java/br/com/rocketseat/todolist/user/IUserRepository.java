package br.com.rocketseat.todolist.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserModel, UUID> {

    Optional<UserModel> findById(UUID id);
    Optional<UserModel> findByUsername(String username);
}
