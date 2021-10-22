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
@Table(name = "Receipt")
public class ReceiptEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "staff_id")
    private StaffEntity staff;

    /*Thành tiền của tất cả sản phẩm trong phiếu nhập*/
    @Column(name="total")
    private BigDecimal total;

    @Column(name="create_date")
    private Date create_date;

    @Column(name="describe")
    private String describe;

    @Column(name="id_code")
    private String id_code;

    @OneToMany(mappedBy = "receipt")
    private List<ReceiptDetailEntity> receiptDetails;
}
