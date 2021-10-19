package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
    private Date create_date;

    @Column(name="update_date")
    private Date update_date;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="total")
    private BigDecimal total;

    @Column(name="status_pay")
    private boolean status_pay;

    @Column(name="address")
    private String address;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="status_order")
    private String status_order;

    @Column(name="describe")
    private String describe;

    @Column(name="thema")
    private String thema;

    @Column(name="themb")
    private String themb;

    @Column(name="themc")
    private String themc;

    @Column(name="id_code")
    private String id_code;

    @ManyToOne @JoinColumn(name = "staff_id")
    private StaffEntity staff;

    @OneToOne @JoinColumn(name = "discount_id")
    private DiscountEntity discount;

    @OneToMany(mappedBy = "bill")
    private List<BillDetailEntity> billDetails;
}
