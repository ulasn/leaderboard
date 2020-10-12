package com.goodjobgames.leaderboard.Controller;


import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.DTO.UserListDTO;
import com.goodjobgames.leaderboard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path= "/create", method = RequestMethod.POST)
    public void createNewUser(@RequestBody NewUserRequestDTO newUserRequestDTO){
        userService.createNewUser(newUserRequestDTO);
    }


    @RequestMapping(path = "/profile/{user_guid}", method = RequestMethod.GET)
    public void getProfileInfo(@PathVariable Long guid){
        userService.getProfileInfo(guid);
    }


    @RequestMapping(path = "/get/all", method = RequestMethod.GET)
    public UserListDTO getAllUsers(){
        return userService.getAllUsers();
    }
}
