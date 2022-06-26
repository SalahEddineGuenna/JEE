package org.id.ebankbackend.repositories;

import org.id.ebankbackend.entities.BankAccount;
import org.id.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {


}
