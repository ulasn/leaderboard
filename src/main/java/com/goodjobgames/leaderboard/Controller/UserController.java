package com.goodjobgames.leaderboard.Controller;


import com.goodjobgames.leaderboard.DTO.Request.NewUserRequestDTO;
import com.goodjobgames.leaderboard.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/create", method = RequestMethod.POST)
    public void createNewUser(NewUserRequestDTO newUserRequestDTO){
        userService.createNewUser(newUserRequestDTO);
    }


    @RequestMapping(path = "/profile/{user_guid}", method = RequestMethod.GET)
    public void getProfileInfo(@PathVariable Long guid){
        userService.getProfileInfo(guid);
    }

}
