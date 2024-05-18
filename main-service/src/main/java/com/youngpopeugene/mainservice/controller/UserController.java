package com.youngpopeugene.mainservice.controller;

import com.youngpopeugene.mainservice.model.api.*;
import com.youngpopeugene.mainservice.model.api.LoanRequirementsDTO;
import com.youngpopeugene.mainservice.service.ApplicationService;
import com.youngpopeugene.mainservice.service.BankOfferService;
import com.youngpopeugene.mainservice.service.JwtService;
import com.youngpopeugene.mainservice.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final JwtService jwtService;
    private final BankOfferService bankOfferService;
    private final UserWalletService userWalletService;
    private final ApplicationService applicationService;

    @PostMapping(value = "/getBankOffers", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public List<BankOffer> getBankOffers(@RequestBody LoanRequirementsDTO loanRequirementsDTO){
        return bankOfferService.getBankOffers(loanRequirementsDTO);
    }

    @PostMapping(value = "/registerApplication", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public OkResponse registerApplication(@RequestHeader(value = "Authorization") String token, @RequestBody Application application){
        applicationService.registerApplication(jwtService.extractEmail(token), application);
        return new OkResponse("Check status on /getApplications");
    }

    @GetMapping(value = "/getApplications", produces = APPLICATION_JSON_VALUE)
    public List<Application> getApplications(@RequestHeader(value = "Authorization") String token){
        return applicationService.getApplications(jwtService.extractEmail(token));
    }

    @GetMapping(value = "/cashoutMoney", produces = APPLICATION_JSON_VALUE)
    public UserWallet cashoutMoney(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "money") double money){
        userWalletService.cashoutMoney(jwtService.extractEmail(token), money);
        return userWalletService.balance(jwtService.extractEmail(token));
    }

    @GetMapping(value = "/depositMoney", produces = APPLICATION_JSON_VALUE)
    public UserWallet depositMoney(@RequestHeader(value = "Authorization") String token, @RequestParam(name = "money") double money){
        userWalletService.depositMoney(jwtService.extractEmail(token), money);
        return userWalletService.balance(jwtService.extractEmail(token));
    }

    @GetMapping(value = "/balance", produces = APPLICATION_JSON_VALUE)
    public UserWallet balance(@RequestHeader(value = "Authorization") String token){
        return userWalletService.balance(jwtService.extractEmail(token));
    }
}