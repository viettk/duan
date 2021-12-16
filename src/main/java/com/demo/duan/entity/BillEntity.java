package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bill")
public class BillEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="create_date")
    private LocalDate create_date;

    @Column(name="update_date")
    private LocalDate update_date;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="total")
    private BigDecimal total;

    @Column(name="status_pay")
    private Integer status_pay;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="status_order")
    private Integer status_order;

    @Column(name="describe")
    private String describe;

    @Column(name="wards")
    private String wards;

    @Column(name="id_code")
    private String id_code;

    @Column(name="type_pay")
    private Boolean type_pay;

    @ManyToOne @JoinColumn(name = "staff_id")
    private StaffEntity staff;

    @ManyToOne @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @OneToMany(mappedBy = "bill")
    private List<BillDetailEntity> billDetails;
}
