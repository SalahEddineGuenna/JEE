package org.id.ebankbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.id.ebankbackend.dtos.CustomerDTO;
import org.id.ebankbackend.entities.Customer;
import org.id.ebankbackend.exceptions.CustomerNotFoundException;
import org.id.ebankbackend.service.BankAccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CustomerRestController {

    private BankAccountService bankAccountService;


    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }

    @GetMapping("/customers/{id}")

    /*Pathavriable montrer que la valeur id {id}--->est parametre Long customerid*/
    public CustomerDTO getCustomer(@PathVariable(name="id") Long customerid) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerid);

    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){

     return  bankAccountService.saveCustomer(customerDTO);
    }


    @PutMapping("/customers/{customerId}")
    //put pour les mise a jour web service
    public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO){

     customerDTO.setId(customerId);
     return bankAccountService.updateCustomer(customerDTO);

    }


    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);

    }


}
