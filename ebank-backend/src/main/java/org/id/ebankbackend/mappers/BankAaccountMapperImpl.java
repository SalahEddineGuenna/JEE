package org.id.ebankbackend.mappers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.id.ebankbackend.dtos.AccountOperationDTO;
import org.id.ebankbackend.dtos.CurrentBankAccountDTO;
import org.id.ebankbackend.dtos.CustomerDTO;
import org.id.ebankbackend.dtos.SavingBankAccountDTO;
import org.id.ebankbackend.entities.AccountOperation;
import org.id.ebankbackend.entities.CurrentACCOUNT;
import org.id.ebankbackend.entities.Customer;
import org.id.ebankbackend.entities.SavingAccount;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.beans.Beans;


@Service
public class BankAaccountMapperImpl {

    public CustomerDTO fromCustomer(Customer customer){
    CustomerDTO customerDTO=new CustomerDTO();
        BeanUtils.copyProperties(customer,customerDTO);
    /*customerDTO.setId(customer.getId());
    customerDTO.setName(customer.getName());
    customerDTO.setEmail(customer.getEmail());*/

    return customerDTO;
    }

    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);

        return customer;
    }





        public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){

        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
            BeanUtils.copyProperties(savingAccount,savingBankAccountDTO);
            savingBankAccountDTO.setCustomerDTO(fromCustomer(savingAccount.getCustomer()));
            savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());
            return savingBankAccountDTO;

        }



    public SavingAccount fromSavingBankAccountDTO(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();
        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;

    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentACCOUNT currentACCOUNT){

        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentACCOUNT,currentBankAccountDTO);
        currentBankAccountDTO.setCustomerDTO(fromCustomer(currentACCOUNT.getCustomer()));
        currentBankAccountDTO.setType(currentACCOUNT.getClass().getSimpleName());
        return currentBankAccountDTO;
    }



    public CurrentACCOUNT fromCurrentBankAccountDTO(CurrentBankAccountDTO currentBankAccountDTO){

        CurrentACCOUNT currentACCOUNT=new CurrentACCOUNT();
        BeanUtils.copyProperties(currentBankAccountDTO,currentACCOUNT);
        currentACCOUNT.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));
        return currentACCOUNT;


    }


    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO  accountOperationDTO=new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation,accountOperationDTO);
        return accountOperationDTO;

    }


}
