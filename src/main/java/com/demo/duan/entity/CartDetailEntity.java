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
@Table(name = "Cart_detail")
public class CartDetailEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne @JoinColumn(name = "cart_id")
    private CartEntity cart;

    @Column(name="number")
    private Integer number;

    /*Thành tiền của tất cả sản phẩm trong phiếu nhập*/
    @Column(name="total")
    private BigDecimal total;
}
