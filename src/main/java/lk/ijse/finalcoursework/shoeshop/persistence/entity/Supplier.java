package lk.ijse.finalcoursework.shoeshop.persistence.entity;

import jakarta.persistence.*;
import lk.ijse.finalcoursework.shoeshop.util.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "suppliers")
public class Supplier {
    @Id
    @Column(name = "supplier_code", unique = true, nullable = false)
    private String supplierCode;

    @Column(name = "supplier_name", nullable = false)
    private String supplierName;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "address_line_01", nullable = false)
    private String addressLine01;

    @Column(name = "address_line_02")
    private String addressLine02;

    @Column(name = "address_line_03")
    private String addressLine03;

    @Column(name = "address_line_04")
    private String addressLine04;

    @Column(name = "address_line_05")
    private String addressLine05;

    @Column(name = "address_line_06")
    private String addressLine06;

    @Column(name = "contact_no1", nullable = false)
    private String contactNo1;

    @Column(name = "land_line_no", nullable = false)
    private String landLineNo;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy =  "supplier")
    private List<Inventory> inventory = new ArrayList<>();
}
