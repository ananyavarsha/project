package com.justin.cruddemos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.justin.cruddemos.dto.CustomerDTO;
import com.justin.cruddemos.model.Customer;
import com.justin.cruddemos.model.RestMessage;
import com.justin.cruddemos.repository.CustomerRepository;
import com.justin.cruddemos.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String RECORD_NOT_FOUND = "Record Not found for the give Customer Number";

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    MessageSource messageSource;

    public ResponseEntity<RestMessage> saveCustomer(CustomerDTO customer) {
        RestMessage successMessage;

        Customer custFromDb = customerRepository.findByCustomerNumber(customer.getCustomerNumber());
        if (custFromDb != null) {
            successMessage = new RestMessage("Record Already Present with the give Customer Number",
                    HttpStatus.CONFLICT, 10002);
            return new ResponseEntity<>(successMessage, HttpStatus.CONFLICT);

        } else {
            Customer cust = setCustomer(customer);
            customerRepository.save(cust);

            successMessage = new RestMessage("Successfully Created", HttpStatus.CREATED, 10001);
            return new ResponseEntity<>(successMessage, HttpStatus.CREATED);

        }
    }

    private Customer setCustomer(CustomerDTO customer) {
        Customer cust = new Customer();
        cust.setAge(customer.getAge());
        cust.setCustomerNumber(customer.getCustomerNumber());
        cust.setFirstName(customer.getFirstName());
        cust.setLastName(customer.getLastName());
        return cust;
    }

    public ResponseEntity<RestMessage> updateCustomer(CustomerDTO customer) {
        Customer cust = customerRepository.findByCustomerNumber(customer.getCustomerNumber());
        RestMessage successMessage;
        if (cust != null) {
            cust.setAge(customer.getAge());
            cust.setFirstName(customer.getFirstName());
            cust.setLastName(customer.getLastName());
            customerRepository.save(cust);
            successMessage = new RestMessage("Successfully Updated", HttpStatus.OK, 10003);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            successMessage = new RestMessage(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, 10004);
            return new ResponseEntity<>(successMessage, HttpStatus.NOT_FOUND);
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<List<Customer>> getCustomers() {
        List<Customer> list = customerRepository.findAll();
        if (list.isEmpty()) {
            RestMessage successMessage = new RestMessage(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, 10004);
            return new ResponseEntity(successMessage, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<Customer> getCustomerById(String customerNumber) {
        Customer custFromDb = customerRepository.findByCustomerNumber(customerNumber);

        RestMessage successMessage;
        if (custFromDb != null) {
            return new ResponseEntity<>(custFromDb, HttpStatus.OK);
        } else {
            successMessage = new RestMessage(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, 10004);
            return new ResponseEntity(successMessage, HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<RestMessage> deleteCustomerById(String customerNumber) {
        Customer custFromDb = customerRepository.findByCustomerNumber(customerNumber);

        RestMessage successMessage;
        if (custFromDb != null) {
            customerRepository.deleteById(custFromDb.getId());
            successMessage = new RestMessage("Successfully Deleted", HttpStatus.OK, 10003);
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } else {
            successMessage = new RestMessage(RECORD_NOT_FOUND, HttpStatus.NOT_FOUND, 10004);
            return new ResponseEntity<>(successMessage, HttpStatus.NOT_FOUND);
        }
    }

}
