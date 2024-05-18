package com.youngpopeugene.mainservice.controller;

import com.youngpopeugene.mainservice.model.api.Application;
import com.youngpopeugene.mainservice.model.api.ManagerBank;
import com.youngpopeugene.mainservice.model.api.OkResponse;
import com.youngpopeugene.mainservice.model.api.ReviewDTO;
import com.youngpopeugene.mainservice.service.ApplicationService;
import com.youngpopeugene.mainservice.service.JwtService;
import com.youngpopeugene.mainservice.service.ManagerBankService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {

    private final JwtService jwtService;
    private final ApplicationService applicationService;
    private final ManagerBankService managerBankService;

    @PostMapping(value = "/setBank", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ManagerBank setBank(@RequestHeader(value = "Authorization") String token, @RequestBody ManagerBank managerBank){
         return managerBankService.setBank(jwtService.extractEmail(token), managerBank);
    }

    @GetMapping(value = "/getApplications", produces = APPLICATION_JSON_VALUE)
    public List<Application> getApplications(@RequestHeader(value = "Authorization") String token){
        return applicationService.getApplicationsForReview(jwtService.extractEmail(token));
    }

    @PostMapping(value = "/reviewApplication", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public OkResponse reviewApplication(@RequestHeader(value = "Authorization") String token, @RequestBody ReviewDTO reviewDTO){
        applicationService.reviewApplication(jwtService.extractEmail(token), reviewDTO);
        return new OkResponse("Status is updated");
    }
}