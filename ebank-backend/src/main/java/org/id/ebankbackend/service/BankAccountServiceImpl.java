package org.id.ebankbackend.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.id.ebankbackend.dtos.*;
import org.id.ebankbackend.entities.*;
import org.id.ebankbackend.enums.OperationType;
import org.id.ebankbackend.exceptions.BankAccountNotFoundException;
import org.id.ebankbackend.exceptions.BlanceNotSufficentException;
import org.id.ebankbackend.exceptions.CustomerNotFoundException;
import org.id.ebankbackend.mappers.BankAaccountMapperImpl;
import org.id.ebankbackend.repositories.AccountOpetrationRepository;
import org.id.ebankbackend.repositories.BankAccountRepository;
import org.id.ebankbackend.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
@Transactional
@Slf4j
public class BankAccountServiceImpl implements BankAccountService{

    private CustomerRepository customerRepository;

    private BankAccountRepository bankAccountRepository;

    private AccountOpetrationRepository accountOpetrationRepository;

    private BankAaccountMapperImpl bankAaccountMapper;
    /*Slf4j*/
    /*Logger log= LoggerFactory.getLogger(this.getClass().getName());*/

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=bankAaccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return bankAaccountMapper.fromCustomer(savedCustomer) ;
    }

    @Override
    public CurrentBankAccountDTO  saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {

        Customer customer =customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentACCOUNT currentACCOUNT=new CurrentACCOUNT();
        currentACCOUNT.setId(UUID.randomUUID().toString());
        currentACCOUNT.setCreatAt(new Date());
        currentACCOUNT.setBalance(initialBalance);
        currentACCOUNT.setOverDraft(overDraft);
        currentACCOUNT.setCustomer(customer);

        CurrentACCOUNT savedBankAccount= bankAccountRepository.save(currentACCOUNT);

        return bankAaccountMapper.fromCurrentBankAccount(savedBankAccount);


    }

    @Override
    public SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer =customerRepository.findById(customerId).orElse(null);
        if (customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount( );
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);

        SavingAccount savedBankAccount= bankAccountRepository.save(savingAccount);

        return bankAaccountMapper.fromSavingBankAccount(savedBankAccount);

    }




    @Override
    public List<CustomerDTO> listCustomers() {


        List<Customer> customers = customerRepository.findAll();

        /*List<CustomerDTO> customerDTOS= new ArrayList<>();

        for (Customer x:customers){
            CustomerDTO customerDTO=bankAaccountMapper.fromCustomer(x);
            customerDTOS.add(customerDTO);
        }*/

         List<CustomerDTO> customerDTOS=  customers.stream().
                 map(customer ->bankAaccountMapper.fromCustomer(customer) ).
                 collect(Collectors.toList());
        return  customerDTOS;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {

        BankAccount bankAccount= bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException("Bank noy found"));
    if (bankAccount instanceof SavingAccount){
        SavingAccount savingAccount=(SavingAccount) bankAccount;
        return bankAaccountMapper.fromSavingBankAccount(savingAccount);
    }else{
        CurrentACCOUNT currentACCOUNT=(CurrentACCOUNT) bankAccount;
        return bankAaccountMapper.fromCurrentBankAccount(currentACCOUNT);
    }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BlanceNotSufficentException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));

        if(bankAccount.getBalance()<amount){
            throw new BlanceNotSufficentException("Balance not sufficient");
        }
        AccountOperation x=new AccountOperation();
        x.setType(OperationType.DEBIT);
        x.setAmount(amount);
        x.setDescription(description);
        x.setOperationDate(new Date());
        x.setBankAccount(bankAccount);

        accountOpetrationRepository.save(x);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        bankAccountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()->new BankAccountNotFoundException("BankAccount not found"));

        AccountOperation x=new AccountOperation();
        x.setType(OperationType.CREDIT);
        x.setAmount(amount);
        x.setDescription(description);
        x.setOperationDate(new Date());
        x.setBankAccount(bankAccount);

        accountOpetrationRepository.save(x);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        bankAccountRepository.save(bankAccount);

    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BlanceNotSufficentException, BankAccountNotFoundException {
        debit(accountIdSource,amount,"Transfer to "+ accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+ accountIdSource );
    }


    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts = bankAccountRepository.findAll();

        List<BankAccountDTO> bankAccountDTOS =bankAccounts.stream().map(bankAccount->{
          if (bankAccount instanceof SavingAccount){
              SavingAccount savingAccount= (SavingAccount) bankAccounts;
              return bankAaccountMapper.fromSavingBankAccount(savingAccount);
          }else {
              CurrentACCOUNT currentAccount = (CurrentACCOUNT) bankAccount;
              return bankAaccountMapper.fromCurrentBankAccount(currentAccount);

          }
        }).collect(Collectors.toList());

        return bankAccountDTOS;
    }

    @Override
    public CustomerDTO getCustomer(Long costomerid) throws CustomerNotFoundException {

        Customer customer = customerRepository.findById(costomerid).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        return bankAaccountMapper.fromCustomer(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=bankAaccountMapper.fromCustomerDTO(customerDTO);
        Customer savedCustomer= customerRepository.save(customer);
        return bankAaccountMapper.fromCustomer(savedCustomer) ;
    }

    @Override
    public void deleteCustomer(Long cutomerid){
        customerRepository.deleteById(cutomerid);

    }

    @Override
    public List<AccountOperationDTO>  accountHistory(String accountId){

        List<AccountOperation> accountOperations = accountOpetrationRepository.findByBankAccountId(accountId);
         return  accountOperations.stream().map(op->bankAaccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId).orElse(null);
        if (bankAccount==null){
            throw new BankAccountNotFoundException("Account not found");
        }
        Page<AccountOperation> byBankAccountIdOrderByOperationDateDesc = accountOpetrationRepository.findByBankAccountIdOrderByOperationDateDesc(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<AccountOperationDTO> accountOperationDTOS = byBankAccountIdOrderByOperationDateDesc.stream().map(op -> bankAaccountMapper.fromAccountOperation(op)).collect(Collectors.toList());
        accountHistoryDTO.setAccountOperationDTO(accountOperationDTOS);
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setTotalPages(byBankAccountIdOrderByOperationDateDesc.getTotalPages());
        accountHistoryDTO.setPageSize(size);

        return accountHistoryDTO;
    }


}
