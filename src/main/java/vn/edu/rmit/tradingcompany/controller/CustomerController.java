package vn.edu.rmit.tradingcompany.controller;

import org.hibernate.HibernateException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import vn.edu.rmit.tradingcompany.model.*;
import vn.edu.rmit.tradingcompany.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.saveCustomer(customer);
    }

    // READ
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Customer getCustomer(@PathVariable int id) {
        return customerService.getCustomer(id);
    }

    @RequestMapping(path = "get", method = RequestMethod.GET)
    public Customer getCustomer(@RequestParam(value = "email", required = false) String email) {
        return customerService.getCustomer(email);
    }

    @RequestMapping(path = "{id}/revenue", method = RequestMethod.GET)
    public String getCustomerRevenue(@PathVariable int id,
                                     @RequestParam(value = "from", required = false) Long from,
                                     @RequestParam(value = "to", required = false) Long to) {
        return customerService.getRevenue(id, from, to);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Customer> getAllCustomers(Pageable pageable) {
        return customerService.getAllCustomers(pageable);
    }

    @RequestMapping(path = "search", method = RequestMethod.GET)
    public List<Customer> searchCustomers(@RequestParam(value = "name", required = false) String name,
                                          @RequestParam(value = "phone", required = false) String phone,
                                          @RequestParam(value = "address", required = false) String address) {
        return customerService.searchCustomers(name, phone, address);
    }

    // UPDATE
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public Customer updateCustomer(@RequestBody Customer customer) {
        return customerService.updateCustomer(customer);
    }

    // DELETE
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    public Customer deleteCustomer(@RequestBody Customer customer) {
        return customerService.deleteCustomer(customer);
    }
}
