package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lk.ijse.finalcoursework.shoeshop.util.Gender;
import lk.ijse.finalcoursework.shoeshop.util.Level;
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
public class CustomerDTO {
    @Null(message = "Supplier CODE is auto genarated")
    private String customerCode;
    @NotBlank(message = "Customer Name Cannot Be Null")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$\n", message = "Name not valid")
    private String customerName;
    @NotBlank(message = "Customer Gender Cannot Be Null")
    private Gender gender;
    @NotBlank(message = "Customer JoinDate Cannot Be Null")
    private Date joinDate;
    @NotBlank(message = "Customer Level Cannot Be Null")
    private Level level;
    @NotBlank(message = "Customer TotalPoints Cannot Be Null")
    @Pattern(regexp = "^[0-9]+$\n", message = "Totat Points not valid")
    private int totalPoints;
    @NotBlank(message = "Customer Date of Birth Cannot Be Null")
    private Date dob;
    @NotBlank(message = "Customer Address Line 01 Cannot Be Null")
    private String addressLine01;
    @NotBlank(message = "Customer Address Line 02 Cannot Be Null")
    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    @NotBlank(message = "Customer Contact Number Cannot Be Null")
    @Pattern(regexp = "^\\+?[0-9()-]+$\n", message = "Contact Number not valid")
    private String contactNo;
    @NotBlank(message = "Customer Email Cannot Be Null")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n", message = "Email not valid")
    private String email;
    @NotBlank(message = "Customer Recent Purchase Cannot Be Null")
    private Date recentPurchaseDateTime;
}
