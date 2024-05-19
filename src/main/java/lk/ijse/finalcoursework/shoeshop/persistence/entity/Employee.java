package lk.ijse.finalcoursework.shoeshop.persistence.entity;

import jakarta.persistence.*;
import lk.ijse.finalcoursework.shoeshop.util.Gender;
import lk.ijse.finalcoursework.shoeshop.util.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author: Vishal Sandakelum,
 * @Runtime version: 11.0.11+9-b1341.60 amd64
 **/

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @Column(name = "employee_code", unique = true, nullable = false)
    private String employeeCode;

    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "employee_profile_pic" , columnDefinition = "LONGTEXT")
    private String employeeProfilePic;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "designation", nullable = false)
    private String designation;

    @Enumerated(EnumType.STRING)
    @Column(name = "access_role")
    private Role accessRole;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "date_of_join")
    private Date dateOfJoin;

    @Column(name = "attached_branch")
    private String attachedBranch;

    @Column(name = "address_line_01", nullable = false)
    private String addressLine01;

    @Column(name = "address_line_02", nullable = false)
    private String addressLine02;

    @Column(name = "address_line_03")
    private String addressLine03;

    @Column(name = "address_line_04")
    private String addressLine04;

    @Column(name = "address_line_05")
    private String addressLine05;

    @Column(name = "contact_no", nullable = false)
    private String contactNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "emergency_contact_person")
    private String emergencyContactPerson;
}
