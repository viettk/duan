package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Discount")
public class DiscountEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "value_discount")
    private int valueDiscount;

    @Column(name = "total")
    private float total;

    @Column(name = "number")
    private int number;

    @Column(name = "open_day")
    private LocalDate open_day;

    @Column(name = "end_day")
    private LocalDate end_day;

    @OneToMany(mappedBy = "discount")
    private List<BillEntity> bills;

}
