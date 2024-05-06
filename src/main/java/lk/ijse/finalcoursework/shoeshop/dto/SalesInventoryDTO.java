package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lk.ijse.finalcoursework.shoeshop.persistence.entity.Sales;
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
public class SalesInventoryDTO {
    @Null
    private String id;

    @NotBlank(message = "Item code is required")
    @Pattern(regexp = "^SH\\d{3}$", message = "Invalid item code format. Must start with 'SH' followed by 3 digits.")
    private InventoryDTO inventory;

    @NotBlank(message = "Item description is required")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid item description format. Only alphanumeric characters and spaces are allowed.")
    private String itemDescription;

    @NotNull(message = "Size is required")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "Invalid size format. Must be a positive integer.")
    private Integer size;

    @NotNull(message = "Unit price is required")
    private Double unitPriceSale;

    @NotNull(message = "Item quantity is required")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "Invalid quantity format. Must be a positive integer.")
    private Integer quantity;

    private SalesDTO sales;
}
