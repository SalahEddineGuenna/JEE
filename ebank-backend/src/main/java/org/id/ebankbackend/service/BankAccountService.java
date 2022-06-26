package org.id.ebankbackend.service;

import org.id.ebankbackend.dtos.*;
import org.id.ebankbackend.entities.BankAccount;
import org.id.ebankbackend.entities.CurrentACCOUNT;
import org.id.ebankbackend.entities.Customer;
import org.id.ebankbackend.entities.SavingAccount;
import org.id.ebankbackend.exceptions.BankAccountNotFoundException;
import org.id.ebankbackend.exceptions.BlanceNotSufficentException;
import org.id.ebankbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService  {

     /*Customer saveCustomer(Customer customer);*/

     CustomerDTO saveCustomer(CustomerDTO customerDTO);

     CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId ) throws CustomerNotFoundException;
     SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate , Long customerId ) throws CustomerNotFoundException;
     List<CustomerDTO> listCustomers();
     BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
     void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BlanceNotSufficentException;
     void credit(String accountId,double amount,String description) throws BankAccountNotFoundException  ;
     void transfer(String accountIdSource,String accountIdDestination,double amount) throws BlanceNotSufficentException, BankAccountNotFoundException;

     List<BankAccountDTO> bankAccountList();

     CustomerDTO getCustomer(Long costomerid) throws CustomerNotFoundException;

     CustomerDTO updateCustomer(CustomerDTO customerDTO);

     void deleteCustomer(Long cutomerid);

     List<AccountOperationDTO>  accountHistory(String accountId);

     AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;
}
