package br.com.rocketseat.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todolist/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserModel> create(@RequestBody UserModel userModel){
        return ResponseEntity.ok(userService.create(userModel));
    }

    @GetMapping
    public List<UserModel> readAll(){
        return userService.readAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserModel> read(@PathVariable("id") UUID id){
        return ResponseEntity.ok(userService.read(id));
    }

}
