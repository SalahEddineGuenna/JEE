package org.id.ebankbackend;

import org.id.ebankbackend.dtos.BankAccountDTO;
import org.id.ebankbackend.dtos.CurrentBankAccountDTO;
import org.id.ebankbackend.dtos.CustomerDTO;
import org.id.ebankbackend.dtos.SavingBankAccountDTO;
import org.id.ebankbackend.entities.*;
import org.id.ebankbackend.enums.AccountStatus;
import org.id.ebankbackend.enums.OperationType;
import org.id.ebankbackend.exceptions.BankAccountNotFoundException;
import org.id.ebankbackend.exceptions.BlanceNotSufficentException;
import org.id.ebankbackend.exceptions.CustomerNotFoundException;
import org.id.ebankbackend.repositories.AccountOpetrationRepository;
import org.id.ebankbackend.repositories.BankAccountRepository;
import org.id.ebankbackend.repositories.CustomerRepository;
import org.id.ebankbackend.service.BankAccountService;
import org.id.ebankbackend.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EbankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("hassan","Yassine","Aicha").forEach(name-> {
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name + "@gmail.com");
                bankAccountService.saveCustomer(customer);



            });


            bankAccountService.listCustomers().forEach(customer -> {

                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000,customer.getId());
                    bankAccountService.saveCurrentBankAccount(Math.random()*120000,5.5,customer.getId());



                }catch (CustomerNotFoundException e){
                    e.printStackTrace();

                }

            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for (BankAccountDTO bankAccount : bankAccounts){
                String accountId;
                if (bankAccount instanceof SavingBankAccountDTO){
                    accountId=((SavingBankAccountDTO)bankAccount).getId();

                }else {
                    accountId=((CurrentBankAccountDTO)bankAccount).getId();
                }
                for (int i = 0; i < 10; i++) {
                    bankAccountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    bankAccountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }

            }

        };
    }


    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository, AccountOpetrationRepository accountOpetrationRepository,
                            BankAccountRepository bankAccountRepository){

return args->{
Stream.of("hassan","Yassine","Aicha").forEach(name-> {
    Customer customer = new Customer();
    customer.setName(name);
    customer.setEmail(name + "@gmail.com");
    customerRepository.save(customer);





});




customerRepository.findAll().forEach(cust->{
                CurrentACCOUNT currentaccount=new CurrentACCOUNT();
                currentaccount.setId(UUID.randomUUID().toString() );
                currentaccount.setBalance(Math.random()*9222);
                currentaccount.setCreatAt(new Date());
                currentaccount.setStatus(AccountStatus.CREATED);
                currentaccount.setCustomer(cust);
                currentaccount.setOverDraft(9000);
                bankAccountRepository.save(currentaccount);

        SavingAccount savingaccount=new SavingAccount();
        savingaccount.setId(UUID.randomUUID().toString());
        savingaccount.setBalance(Math.random()*9222);
         savingaccount.setCreatAt(new Date());
        savingaccount.setStatus(AccountStatus.CREATED);
        savingaccount.setCustomer(cust);
        savingaccount.setInterestRate(5.5);
        bankAccountRepository.save(savingaccount);



} );


bankAccountRepository.findAll().forEach(acc->{

    for (int i = 0; i < 10; i++) {
        AccountOperation accountOperation=new AccountOperation();
         accountOperation.setOperationDate(new Date());
         accountOperation.setAmount(Math.random()*2000);
         accountOperation.setType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
         accountOperation.setBankAccount(acc);
         accountOpetrationRepository.save(accountOperation);

    }



});

};
    }

















}
