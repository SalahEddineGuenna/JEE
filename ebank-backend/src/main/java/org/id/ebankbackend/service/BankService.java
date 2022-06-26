package org.id.ebankbackend.service;


import org.id.ebankbackend.entities.BankAccount;
import org.id.ebankbackend.entities.CurrentACCOUNT;
import org.id.ebankbackend.entities.SavingAccount;
import org.id.ebankbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    public void consulter(){
        BankAccount bankAccount=bankAccountRepository.findById("12fbc2d5-0e8b-4df0-a785-11cca9d1765d ").orElse(null);
        if (bankAccount!=null){
            System.out.println("***********************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCreatAt());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentACCOUNT){
                System.out.println(((CurrentACCOUNT) bankAccount).getOverDraft());
            }else if (bankAccount instanceof SavingAccount){
                System.out.println(((SavingAccount) bankAccount).getInterestRate());
            }

            bankAccount.getAccountOperations().forEach(op->{
                System.out.println("================= ");
                System.out.println(op.getType());

                System.out.println(op.getOperationDate());



            });


    }
}
}