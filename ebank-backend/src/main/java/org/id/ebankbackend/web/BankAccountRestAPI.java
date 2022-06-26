package org.id.ebankbackend.web;


import org.id.ebankbackend.dtos.AccountHistoryDTO;
import org.id.ebankbackend.dtos.AccountOperationDTO;
import org.id.ebankbackend.dtos.BankAccountDTO;
import org.id.ebankbackend.exceptions.BankAccountNotFoundException;
import org.id.ebankbackend.service.BankAccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount( accountId);


    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccount(){
        return bankAccountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory( @PathVariable String accountId){

    return bankAccountService.accountHistory(accountId);

    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId, @RequestParam(name="page",defaultValue ="0") int page,
                                               @RequestParam(name="size",defaultValue ="5") int size) throws BankAccountNotFoundException {

        return bankAccountService.getAccountHistory(accountId,page,size);

    }

}
