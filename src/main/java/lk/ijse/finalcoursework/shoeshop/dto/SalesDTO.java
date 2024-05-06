package lk.ijse.finalcoursework.shoeshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalesDTO {
    private List<SalesInventoryDTO> inventory;

    @NotBlank(message = "Order number is required")
    @Pattern(regexp = "^ORD\\d{4}$", message = "Invalid order number format. Must start with 'ORD' followed by 4 digits.")
    private String orderNo;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid name format")
    private String customerName;

    @NotNull(message = "Total price is required")
    private Double totalPrice;

    @NotNull(message = "Purchase date is required")
    private LocalDateTime purchaseDate;

    @NotBlank(message = "Payment method is required")
    @Pattern(regexp = "^(cash|card)$", message = "Invalid payment method. Must be 'cash' or 'card'.")
    private String paymentMethod;

    private Double addedPoints;

    @NotBlank(message = "Cashier name is required")
    @Pattern(regexp = "^[a-zA-Z]+(?:[ '-][a-zA-Z]+)*$", message = "Invalid name format")
    private String cashierName;
}
