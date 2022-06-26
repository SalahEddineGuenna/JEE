package org.id.ebankbackend.dtos;

import lombok.Data;
import org.id.ebankbackend.entities.BankAccount;

import java.util.Date;


@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private String Description;


    private BankAccount bankAccount;
}
