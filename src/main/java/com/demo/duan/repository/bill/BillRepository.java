package com.demo.duan.repository.bill;

import com.demo.duan.entity.BillDetailEntity;
import com.demo.duan.entity.BillEntity;
import com.demo.duan.entity.ThongkeEntity;
import com.demo.duan.service.product.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<BillEntity, Integer> {
    @Query("select b from BillEntity b where b.status_order=:#{#status} ")
    List<BillEntity> all(String status);
    @Query ( value="select sum(ct.number) from Bill_detail  ct , Bill hd where ct.bill_id = hd.id and month(update_date)= :month and year(update_date)= :year" , nativeQuery = true)
    Integer Thongke(@Param("month")Integer month, @Param("year") Integer year);
    @Query(value = "Select Top 1 Product.name, SUM(Bill_detail.number) as Tongsoluongban\n" +
            "from Product join Bill_detail on Product.id = Bill_detail.product_id join Bill on Bill_detail.bill_id = Bill.id  \n" +
            "where Product.id = Bill_detail.product_id \n" +
            "AND MONTH(Bill.update_date)=11 and Product.id = Bill_detail.product_id group by Product.name", nativeQuery = true)
    Object Thongkespbanchay(@Param("month")Integer month);

    @Query(value = "Select Top 5 Product.name, SUM(Bill_detail.number) as Tongsoluongban\n" +
            "from Product join Bill_detail on Product.id = Bill_detail.product_id join Bill on Bill_detail.bill_id = Bill.id  \n" +
            "where Product.id = Bill_detail.product_id \n" +
            " group by Product.name ", nativeQuery = true)
    List<Object> Thongketop5spbanchay();

    @Query ( value="SELECT sum(hd.total) FROM Bill hd WHERE MONTH(hd.update_date) = :month " , nativeQuery = true)
    Double Thongkedoanhthu(@Param("month")Integer month);

    @Query ( value="SELECT count(hd.id) as sodonhuy\n" +
            "FROM  Bill hd\n" +
            "WHERE hd.status_order = N'Giao thành công' and  hd.status_order= N'Ðang chuẩn bị'" , nativeQuery = true)
    Integer Thongkedonhang();
//    @Query(value = "SELECT  Top 5 * FROM Customer kh, Bill WHERE Bill.email =kh.email AND Bill.total in (\n" +
//            "    SELECT MAX(hd.total)  FROM Bill hd  WHERE MONTH(hd.update_date)= :month \n" +
//            ")" ,nativeQuery = true)
//    List<Object> thongkekhachhang(@Param("month") Integer month);
}
