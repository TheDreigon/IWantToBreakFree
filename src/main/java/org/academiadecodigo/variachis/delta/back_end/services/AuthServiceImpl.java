package org.academiadecodigo.variachis.delta.back_end.services;

import javassist.NotFoundException;
import org.academiadecodigo.variachis.delta.back_end.converters.AuthCustomerDTOToCustomer;
import org.academiadecodigo.variachis.delta.back_end.dto.AuthCustomerDTO;
import org.academiadecodigo.variachis.delta.back_end.dto.CustomerDTO;
import org.academiadecodigo.variachis.delta.back_end.exceptions.CustomerNotFoundException;
import org.academiadecodigo.variachis.delta.back_end.persistence.dao.CustomerDAO;
import org.academiadecodigo.variachis.delta.back_end.persistence.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private Integer accessingCustomerId;
    private CustomerService customerService;
    private AuthCustomerDTOToCustomer authCustomerDTOToCustomer;
    private CustomerDAO customerDAO;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Autowired
    public void setAuthCustomerDTOToCustomer(AuthCustomerDTOToCustomer authCustomerDTOToCustomer) {
        this.authCustomerDTOToCustomer = authCustomerDTOToCustomer;
    }

    @Autowired
    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    public Customer verify(AuthCustomerDTO authCustomerDTO) throws CustomerNotFoundException {


        Customer customer = authCustomerDTOToCustomer.convert(authCustomerDTO);

        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        Customer customer1 = customerDAO.getByName(customer.getUsername());


        if (customer == null) {
            throw new CustomerNotFoundException();
        }

        return customerDAO.verifyPassword(customer1, customer.getPassword());
    }
}
