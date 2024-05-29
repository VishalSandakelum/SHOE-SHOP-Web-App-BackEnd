package lk.ijse.finalcoursework.shoeshop.api;

import jakarta.validation.Valid;
import lk.ijse.finalcoursework.shoeshop.dto.CustomerDTO;
import lk.ijse.finalcoursework.shoeshop.dto.EmployeeDTO;
import lk.ijse.finalcoursework.shoeshop.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@RestController
@RequestMapping("api/v0/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.PATCH, RequestMethod.OPTIONS})
public class EmployeeAPI {
    private final EmployeeService employeeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<EmployeeDTO> getAllEmployee(){
        return employeeService.getAllEmployees();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    EmployeeDTO saveEmployee(@RequestPart("data") EmployeeDTO employeeDTO,@RequestPart("profilepic") MultipartFile profilepic){
        String base64ProfilePic = null;
        try {
            base64ProfilePic = Base64.getEncoder().encodeToString(profilepic.getBytes());
            employeeDTO.setEmployeeProfilePic(
                    base64ProfilePic
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeService.saveEmployee(employeeDTO);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void updateEmployee(@RequestPart("data") EmployeeDTO employeeDTO,@RequestPart("profilepic")MultipartFile profilepic){
        String base64ProfilePic = null;
        try {
            base64ProfilePic = Base64.getEncoder().encodeToString(profilepic.getBytes());
            employeeDTO.setEmployeeProfilePic(
                    base64ProfilePic
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        employeeService.updateEmployee(employeeDTO.getEmployeeCode(),employeeDTO);
    }

    @DeleteMapping(value = "/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    void deleteEmployee(@PathVariable("id") String customerCode){
        employeeService.deleteEmployee(customerCode);
    }

    @PatchMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    EmployeeDTO getEmployee(@PathVariable("id") String id){
        return employeeService.getEmployeeDetails(id);
    }

    @GetMapping("/nextid")
    @ResponseStatus(HttpStatus.ACCEPTED)
    String getNextEmployeeCode(){
        return employeeService.nextEmployeeCode();
    }

    @GetMapping("/dob")
    @ResponseStatus(HttpStatus.CREATED)
    List<EmployeeDTO>getAllEmployeesOrderByDob(){
        return employeeService.findAllEmployeesOrderByDob();
    }
}
