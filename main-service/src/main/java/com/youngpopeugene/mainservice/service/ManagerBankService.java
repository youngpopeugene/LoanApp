package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.model.api.ManagerBank;
import com.youngpopeugene.mainservice.repository.ManagerBankRepository;
import com.youngpopeugene.mainservice.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagerBankService {

    private final ManagerBankRepository managerBankRepository;

    public ManagerBank setBank(String email, ManagerBank managerBank) {
        managerBank.setManagerEmail(email);
        saveManagerBank(managerBank);
        return managerBank;
    }

    public ManagerBank getManagerBankByEmail(String email){
        return managerBankRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException("You have to work at least in some bank")
        );
    }

    public void saveManagerBank(ManagerBank managerBank){
        managerBankRepository.save(managerBank);
    }

}