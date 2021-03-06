package com.example.controllers;

import com.example.Util;
import com.example.entities.dtos.UserLoginDto;
import com.example.services.UserService;
import com.example.utilities.results.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getall")
    public String getAll(){
        return Util.ConvertToJsonString(userService.getAll());
    }

    @PostMapping("/login")
    public String login(@RequestBody String json, HttpServletRequest request, HttpServletResponse response){
        JSONObject requestBody = new JSONObject(json);
        UserLoginDto nesne = new UserLoginDto();
        nesne.setEmail(requestBody.getString("email"));
        nesne.setPassword(Util.GetSha256Hash(requestBody.getString("password")));
        Result result=this.userService.login(nesne);
        if(result.isSuccess()){
            return Util.ConvertToJsonString(ResponseEntity.ok(result));
        }
        return Util.ConvertToJsonString(ResponseEntity.badRequest().body(result));
    }
}