package com.youngpopeugene.mainservice.service;

import com.youngpopeugene.mainservice.model.api.BankOffer;
import com.youngpopeugene.mainservice.model.api.LoanRequirementsDTO;
import com.youngpopeugene.mainservice.repository.BankOfferRepository;
import com.youngpopeugene.mainservice.exception.NotFoundException;
import com.youngpopeugene.mainservice.exception.ValidateException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BankOfferService {

    private final BankOfferRepository bankOfferRepository;

    public List<BankOffer> getBankOffers(LoanRequirementsDTO loanRequirementsDTO) {
        if (!validateLoanRequirements(loanRequirementsDTO)) throw new ValidateException();
        double amount = loanRequirementsDTO.getAmount();
        int duration = loanRequirementsDTO.getDuration();
        List<BankOffer> list = bankOfferRepository.findSuitableBankOffers(amount, duration);
        if (list.isEmpty())
            throw new NotFoundException("There are no bank offers for your credit requirements");
        return list;
    }

    public BankOffer getBankOfferById(int id) {
        return bankOfferRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Bank offer not found")
        );
    }

    public boolean validateLoanRequirements(LoanRequirementsDTO loanRequirementsDTO){
        Set<ConstraintViolation<LoanRequirementsDTO>> violations =
                Validation.buildDefaultValidatorFactory().getValidator().validate(loanRequirementsDTO);
        return violations.isEmpty();
    }
}