package lk.ijse.finalcoursework.shoeshop.service.impl;

import lk.ijse.finalcoursework.shoeshop.dto.CustomerDTO;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Customer;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.CustomerRepository;
import lk.ijse.finalcoursework.shoeshop.service.CustomerService;
import lk.ijse.finalcoursework.shoeshop.service.execption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Service
public class CustomerServiceImpl implements CustomerService {
    CustomerRepository customerRepository;
    ModelMapper modelMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll().stream().map(
                customer -> modelMapper.map(customer,CustomerDTO.class)
        ).toList();
    }

    @Override
    public CustomerDTO getCustomerDetails(String id) {
        if(!customerRepository.existsById(id)){
            throw  new NotFoundException("Customer ID"+ id + "Not Found...");
        }
        return customerRepository.findById(id).map(
                customer -> modelMapper.map(customer, CustomerDTO.class)
        ).get();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        return modelMapper.map(customerRepository.save(modelMapper.map(
                customerDTO, Customer.class)), CustomerDTO.class
        );
    }

    @Override
    public void updateCustomer(String id, CustomerDTO customerDTO) {
        if(!customerRepository.existsById(id)){
            throw  new NotFoundException("Customer ID"+ customerDTO.getCustomerCode() + "Not Found...");
        }
        customerRepository.save(modelMapper.map(customerDTO, Customer.class));
    }

    @Override
    public void deleteCustomer(String id) {
        if(!customerRepository.existsById(id)){
            throw  new NotFoundException("Customer ID"+ id + "Not Found...");
        }
        customerRepository.deleteById(id);
    }
}
