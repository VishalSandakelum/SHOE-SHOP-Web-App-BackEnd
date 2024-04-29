package lk.ijse.finalcoursework.shoeshop.api;

import lk.ijse.finalcoursework.shoeshop.dto.CustomerDTO;
import lk.ijse.finalcoursework.shoeshop.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@RestController
@RequestMapping("api/v0/customers")
public class CustomerAPI {
    private final CustomerService customerService;

    public CustomerAPI(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<CustomerDTO> getAllCustomer(){
        return null;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        System.out.println(customerDTO);
        return null;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CustomerDTO updateCustomer(@RequestBody CustomerDTO customerDTO){
        System.out.println(customerDTO.toString());
        return null;
    }

    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    CustomerDTO deleteCustomer(@RequestBody CustomerDTO customerDTO){
        System.out.println(customerDTO.toString());
        return null;
    }
}
