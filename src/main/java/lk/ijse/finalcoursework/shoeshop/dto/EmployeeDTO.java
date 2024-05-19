package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lk.ijse.finalcoursework.shoeshop.util.Gender;
import lk.ijse.finalcoursework.shoeshop.util.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String employeeCode;

    @NotBlank(message = "Employee Name cannot be blank")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid name format")
    private String employeeName;

    private String employeeProfilePic;

    private Gender gender;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    private Role accessRole;

    @NotNull(message = "Date of Birth cannot be null")
    private Date dob;

    @NotNull(message = "Date of Joining cannot be null")
    private Date dateOfJoin;

    private String attachedBranch;

    @NotBlank(message = "Address Line 01 cannot be blank")
    private String addressLine01;

    @NotBlank(message = "Address Line 02 cannot be blank")
    private String addressLine02;

    private String addressLine03;
    private String addressLine04;
    private String addressLine05;

    @NotBlank(message = "Contact number cannot be blank")
    @Pattern(regexp = "^\\+?[0-9()-]{1,11}$", message = "Invalid contact number format")
    private String contactNo;

    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;

    private String emergencyContact;
    private String emergencyContactPerson;

}
