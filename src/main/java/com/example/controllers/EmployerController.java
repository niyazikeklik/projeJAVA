package com.example.controllers;

import com.example.Util;
import com.example.entities.dtos.EmployerForRegisterDto;
import com.example.services.EmployerService;
import com.example.utilities.results.Result;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/employer")
@CrossOrigin
public class EmployerController {

    private EmployerService employerService;

    @Autowired
    public EmployerController(EmployerService employerService) {
        this.employerService = employerService;
    }

    @GetMapping("/getall")
    public String getAll(){
        return Util.ConvertToJsonString(this.employerService.getAll());
    }

    @PostMapping("/add")
    public String add(@RequestBody String json, HttpServletRequest request, HttpServletResponse response){
        EmployerForRegisterDto nesne = new EmployerForRegisterDto();
        JSONObject requestBody = new JSONObject(json);
        nesne.setEmail(requestBody.getString("email"));
        nesne.setPassword(Util.GetSha256Hash(requestBody.getString("password")));
        nesne.setCompanyName(requestBody.getString("companyName"));
        nesne.setPhoneNumber(requestBody.getString("phoneNumber"));
        nesne.setRePassword(Util.GetSha256Hash(requestBody.getString("rePassword")));
        nesne.setWebSite(requestBody.getString("webSite"));
        Result result=this.employerService.add(nesne);
        if(result.isSuccess()){
            return Util.ConvertToJsonString(ResponseEntity.ok(result));
        }
        return Util.ConvertToJsonString(ResponseEntity.badRequest().body(result));
    }

    @GetMapping("/getById")
    public String getById(@RequestParam int id){
        return Util.ConvertToJsonString(this.employerService.getById(id));
    }
}