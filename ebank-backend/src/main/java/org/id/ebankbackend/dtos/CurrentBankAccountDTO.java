package org.id.ebankbackend.dtos;


import lombok.Data;
import org.id.ebankbackend.enums.AccountStatus;

import java.util.Date;


@Data

public  class CurrentBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date creatAt;

    private AccountStatus status;

    private CustomerDTO customerDTO;
    private double overDraft;


}
