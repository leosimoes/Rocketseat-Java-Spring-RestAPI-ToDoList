package br.com.rocketseat.todolist.user;

import br.com.rocketseat.todolist.error.UserAlreadyExistsException;
import br.com.rocketseat.todolist.error.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private IUserRepository iUserRepository;

    @Autowired
    public UserService(IUserRepository iUserRepository){
        this.iUserRepository = iUserRepository;
    }

    public UserModel create(UserModel userModel){
        Optional<UserModel> userModelOpt = iUserRepository.findByUsername(userModel.getUsername());

        if(userModelOpt.isPresent()){
            throw new UserAlreadyExistsException("User already exists.");
        }

        return iUserRepository.save(userModel);
    }

    public UserModel read(UUID id){
        Optional<UserModel> userModelOpt = iUserRepository.findById(id);
        if(userModelOpt.isEmpty()){
            throw new UserNotFoundException("There is no record for the id " + id.toString() + ".");
        }

        return userModelOpt.get();
    }

    public List<UserModel> readAll(){
        return iUserRepository.findAll();
    }

}
