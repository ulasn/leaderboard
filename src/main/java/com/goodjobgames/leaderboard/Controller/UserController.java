package com.goodjobgames.leaderboard.Controller;


import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestListDTO;
import com.goodjobgames.leaderboard.DTO.Response.UserResponseDTO;
import com.goodjobgames.leaderboard.DTO.Response.UserResponseListDTO;
import com.goodjobgames.leaderboard.DTO.UserListDTO;
import com.goodjobgames.leaderboard.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(path= "/create", method = RequestMethod.POST)
    @ResponseBody
    public UserResponseDTO createNewUser(@RequestBody NewUserRequestDTO newUserRequestDTO){
        return userService.createNewUser(newUserRequestDTO);
    }

    @RequestMapping(path = "/create/multiple", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponseListDTO createMultipleUsers(@RequestBody NewUserRequestListDTO newUserRequestListDTO){
        return userService.createMultipleUsers(newUserRequestListDTO);
    }

    @RequestMapping(path = "/create/multiple/points", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserResponseListDTO createMultipleUsersWithPoints(@RequestBody NewUserRequestListDTO newUserRequestListDTO){
        return userService.createMultipleUsersWithPoints(newUserRequestListDTO);
    }


    @RequestMapping(path = "/profile/{user_guid}", method = RequestMethod.GET)
    @ResponseBody
    public UserResponseDTO getProfileInfo(@PathVariable String user_guid){
        return userService.getProfileInfo(user_guid);
    }


    @RequestMapping(path = "/get/all", method = RequestMethod.GET)
    public UserListDTO getAllUsers(){
        return userService.getAllUsers();
    }
}
