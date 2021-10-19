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
@Table(name = "Bill_detail")
public class BillDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne @JoinColumn(name = "bill_id")
    private BillEntity bill;

    /*Số lượng của 1 sản phẩm*/
    @Column(name="number")
    private Integer number;

    @Column(name="price")
    private BigDecimal price;

    /*Thành tiền của tất cả sản phẩm trong phiếu nhập*/
    @Column(name="total")
    private BigDecimal total;
}
