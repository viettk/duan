package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Bill_Return_Detail")
public class BillReturnDetailEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne @JoinColumn(name = "bill_return_id")
    private BillReturnEntity billReturn;

    @Column(name="number")
    private Integer number;

    @Column(name="real_number")
    private Integer real_number;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="total")
    private BigDecimal total;
}
