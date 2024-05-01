package lk.ijse.finalcoursework.shoeshop.service.impl;

import lk.ijse.finalcoursework.shoeshop.dto.CustomerDTO;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Customer;
import lk.ijse.finalcoursework.shoeshop.persistence.repository.CustomerRepository;
import lk.ijse.finalcoursework.shoeshop.service.CustomerService;
import lk.ijse.finalcoursework.shoeshop.service.execption.DublicateRecordException;
import lk.ijse.finalcoursework.shoeshop.service.execption.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Service
@Transactional
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
        if(!customerRepository.existsByCustomerCode(id)){
            throw new NotFoundException("Customer "+id+" Not Found!");
        }
        return modelMapper.map(customerRepository.findByCustomerCode(id), CustomerDTO.class);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        if(customerRepository.existsByCustomerCode(customerDTO.getCustomerCode())){
            throw new DublicateRecordException("This Customer "+customerDTO.getCustomerCode()+" already exicts...");
        }
        return modelMapper.map(customerRepository.save(modelMapper.map(
                customerDTO, Customer.class)), CustomerDTO.class
        );
    }

    @Override
    public void updateCustomer(String id, CustomerDTO customerDTO) {
        Customer existingCustomer = customerRepository.findByCustomerCode(id);

        if(existingCustomer.getCustomerName().isEmpty()){
            throw new NotFoundException("Customer ID"+ id + "Not Found...");
        }

        existingCustomer.setCustomerName(customerDTO.getCustomerName());
        existingCustomer.setGender(customerDTO.getGender());

        customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(String id) {
        if(!customerRepository.existsByCustomerCode(id)){
            throw  new NotFoundException("Customer ID"+ id + "Not Found...");
        }
        customerRepository.deleteByCustomerCode(id);
    }
}
