package lk.ijse.finalcoursework.shoeshop.persistence.entity;

import jakarta.persistence.*;
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

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_code", unique = true, nullable = false)
    private String customerCode;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "join_date")
    private Date joinDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private Level level;

    @Column(name = "total_points", nullable = false)
    private int totalPoints;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address_line_01")
    private String addressLine01;

    @Column(name = "address_line_02")
    private String addressLine02;

    @Column(name = "address_line_03")
    private String addressLine03;

    @Column(name = "address_line_04")
    private String addressLine04;

    @Column(name = "address_line_05")
    private String addressLine05;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email")
    private String email;

    @Column(name = "recent_purchase_date_time")
    private Date recentPurchaseDateTime;
}
