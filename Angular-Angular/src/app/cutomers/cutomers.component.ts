import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {CustomerService} from "../services/customer.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {Customer} from "../model/customer.model";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cutomers',
  templateUrl: './cutomers.component.html',
  styleUrls: ['./cutomers.component.css']
})
export class CutomersComponent implements OnInit {
  customers! :Observable<Array<Customer>>;
  errMessage !: object;
  searchFormGroup! :FormGroup;
  constructor(private customerService : CustomerService,private fb:FormBuilder, private router : Router) { }

  ngOnInit(): void {
this.searchFormGroup=this.fb.group({
  keyword : this.fb.control("")
})


this.handleSearchCustomers();
  }
  handleSearchCustomers(){
    let conf =confirm("Are you sure ?")
    if (!conf) return ;
    let kw=this.searchFormGroup?.value.keyword;
    this.customers=this.customerService.searchCustomers(kw).pipe(
      catchError(err=>{
        this.errMessage=err.message;
        return throwError(err);



      })


    );
  }


  handleDeleteCustomer(c: Customer) {
    this.customerService.deleteCustomer(c.id).subscribe({
      next : (resp) => {
        this.customers=this.customers.pipe(map(data=>{
          let index =data.indexOf(c);
          data.slice(index,1)
          return data;
        }));
      },
      error :err=>{
        console.log(err);
      }

    })

  }


    handleCustomerAccounts(customer: Customer) {
    this.router.navigateByUrl("/customer-accounts/"+customer.id,{state :customer});
  }

}
