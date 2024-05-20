package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lk.ijse.finalcoursework.shoeshop.util.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierDTO {
    @NotBlank(message = "Supplier code is required")
    @Size(min = 1, max = 50, message = "Supplier code must be between 1 and 50 characters")
    private String supplierCode;

    @NotBlank(message = "Supplier name is required")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid name format")
    @Size(min = 1, max = 100, message = "Supplier name must be between 1 and 100 characters")
    private String supplierName;

    private Category category;

    @NotBlank(message = "Address Line 01 is required")
    private String addressLine01;

    private String addressLine02;
    private String addressLine03;
    private String addressLine04;
    private String addressLine05;
    private String addressLine06;

    @NotBlank(message = "Contact No.1 is required")
    @Pattern(regexp = "^\\+?[0-9()-]{1,11}$", message = "Invalid contact number format")
    private String contactNo1;

    @NotBlank(message = "Contact No.1 is required")
    @Pattern(regexp = "^\\+?[0-9()-]{1,11}$", message = "Invalid contact number format")
    private String landLineNo;

    private String email;
}
