package org.id.ebankbackend.repositories;

import org.id.ebankbackend.entities.AccountOperation;
import org.id.ebankbackend.entities.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountOpetrationRepository extends JpaRepository<AccountOperation,Long> {

    List<AccountOperation> findByBankAccountId(String accountId);
    Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accountId, Pageable pageable);


}
