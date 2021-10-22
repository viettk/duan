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
@Table(name = "Product")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private boolean status;

    @Column(name = "number")
    private Integer number;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "describe")
    private String describe;

    @Column(name = "photo")
    private String photo;

    @Column(name = "create_date")
    private Date createDate;

    @OneToMany(mappedBy = "product")
    private List<PhotoEntity> photos ;

    /* -------------------------- Phục vụ mô tả  ---------------------------------- */

    @Column(name = "sku")
    private String sku;

    /* Đặc điểm nổi bật */
    @Column(name = "trait")
    private String trait;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "price_release")
    private Integer price_release;

    @Column(name = "weight")
    private Float weight;

    @Column(name = "height")
    private Float height;

    @Column(name = "width")
    private Float width;

    @Column(name="length")
    private Float length;
    
    /* Bảng hóa đơn chi tiết*/
    @OneToMany(mappedBy = "product")
    private List<BillDetailEntity> billDetails;

    @OneToMany(mappedBy = "product")
    private List<CartDetailEntity> cartDetails;

    /*Phiếu nhập chi tiết*/
    @OneToMany(mappedBy = "product")
    List<ReceiptDetailEntity> receiptDetails ;

    @OneToMany(mappedBy = "product")
    List<FavoriteEntity> favorites ;

}
