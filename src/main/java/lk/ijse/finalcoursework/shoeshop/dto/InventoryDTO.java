package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class InventoryDTO {
    @NotBlank(message = "Item code is required")
    private String itemCode;

    @NotBlank(message = "Item description is required")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid item description format. Only alphanumeric characters and spaces are allowed.")
    private String itemDescription;

    private String itemPicture;

    @NotBlank(message = "Category is required")
    @Pattern(regexp = "^(International|Local)$", message = "Invalid category. Must be 'International' or 'Local'.")
    private String category;

    @NotNull(message = "Size is required")
    @Pattern(regexp = "^[1-9][0-9]*$", message = "Invalid size format. Must be a positive integer.")
    private Integer size;

    @NotBlank(message = "Supplier code is required")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Invalid supplier code format. Only alphanumeric characters are allowed.")
    private String supplierCode;

    @NotBlank(message = "Supplier name is required")
    @Pattern(regexp = "^[a-zA-Z\\s]*$", message = "Invalid supplier name format. Only alphabetic characters and spaces are allowed.")
    private String supplierName;

    @NotNull(message = "Unit price (sale) is required")
    private Double unitPriceSale;

    @NotNull(message = "Unit price (buy) is required")
    private Double unitPriceBuy;

    @NotNull(message = "Expected profit is required")
    private Double expectedProfit;

    @NotNull(message = "Profit margin is required")
    @Pattern(regexp = "^(0*(?:[1-9]\\d{0,2}(?:,\\d{3})*|0)(?:\\.\\d+)?|)$", message = "Invalid profit margin format. Must be a non-negative decimal number.")
    private Double profitMargin;

    @NotBlank(message = "Status is required")
    @Pattern(regexp = "^(low|available|not available)$", message = "Invalid status. Must be 'low', 'available', or 'not available'.")
    private String status;

    private Integer quantity;
}
