package com.demo.duan.repository.customer;

import com.demo.duan.entity.CustomerEntity;
import com.demo.duan.service.customer.param.CustomerParam2;
import com.demo.duan.service.customer.paramcustomer.Customerparam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity , Integer> {

    Integer countAllByEmail(String email);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByEmailAndStatusIsFalse(String email);

    Optional<CustomerEntity> findByIdAndEmail(Integer id, String email);
    
    CustomerEntity getByEmail(String email);

    @Query("from CustomerEntity c where (:#{#param.name} is null or c.name like %:#{#param.name}%)" +
            "and (:#{#param.email} is null or c.email like %:#{#param.email}%)" +
            "and (:#{#param.status} = false or c.status = :#{#param.status})")
    Page<CustomerEntity> getAll(@Param("param")Customerparam param, Pageable pageable);

    @Query("from CustomerEntity c where (:#{#name} is null or c.name like %:#{#name}%)" +
            "and (:#{#email} is null or c.email like %:#{#email}%)" +
            "and (:#{#status} is null or c.status = :#{#status})")
    Page<CustomerEntity> findAllCustomer(String email,Boolean status,String name,Pageable page);
//    ---------------------------------------------------------
    List<CustomerEntity> findByIdIn(Integer[] ids);

    @Query(value = "select Customer.id, Customer.name, Customer.email, Customer.last_login,Customer.status,count(bill.id) as sumBill,COUNT(Bill_detail.number) as tongSP, sum(Bill.total) as total from Customer \n" +
            "JOIN Bill on Customer.email = Bill.email  join Bill_detail on Bill.id = Bill_detail.bill_id\n" +
            "Where Customer.id is not null \n" +
            "and (:#{#param.email} is null or Customer.email like %:#{#param.email}%)" +
            "and (:#{#param.name} is null or Customer.name like %:#{#param.name}%)" +
            "and (:#{#param.statusOrder} is null or Bill.status_order = :#{#param.statusOrder})" +
            "and (:#{#param.year} is null or  year(Bill.update_date) = :#{#param.year})" +
            "and (:#{#param.month} is null or  Month(Bill.update_date) = :#{#param.month})" +
            "group by Customer.id,Customer.email ,Customer.last_login,Customer.name,Customer.status",nativeQuery = true)
    Page<Map<String,Object>> findCustomer_Bill(@Param("param") CustomerParam2 param , Pageable page);

    @Query(value = "select Customer.id, Customer.name, Customer.email, Customer.last_login,Customer.status,count(bill.id) as sumBill,COUNT(Bill_detail.number) as tongSP, sum(Bill.total) as total from Customer \n" +
            "JOIN Bill on Customer.email = Bill.email  join Bill_detail on Bill.id = Bill_detail.bill_id\n" +
            "Where Customer.id is not null \n" +
            "and (:#{#param.email} is null or Customer.email like %:#{#param.email}%)" +
            "and (:#{#param.name} is null or Customer.name like %:#{#param.name}%)" +
            "and (:#{#param.statusOrder} is null or Bill.status_order = :#{#param.statusOrder})" +
            "and (:#{#param.year} is null or  year(Bill.update_date) = :#{#param.year})" +
            "and (:#{#param.month} is null or  Month(Bill.update_date) = :#{#param.month})" +
            "group by Customer.id,Customer.email ,Customer.last_login,Customer.name,Customer.status",nativeQuery = true)
    List<Map<String,Object>> findCustomer_Bill_ID (@Param("param")CustomerParam2 param );

    @Query("from CustomerEntity c where (:#{#name} is null or c.name like %:#{#name}%)" +
            "and (:#{#email} is null or c.email like %:#{#email}%)" +
            "and (:#{#status} is null or c.status = :#{#status})")
    List<CustomerEntity> findAllCustomer_ID(String email,Boolean status,String name);
//----------------------------------------------------------------------

    //Thống kê khách hàng cùng với số lần mua hàng, tổng giá trị all hóa đơn
    @Query(nativeQuery = true, value = "select customer.id, customer.email, count(bill.id), sum(bill.total) from customer " +
            "join bill on customer.email = bill.email group by customer.id order by count(bill.id);")
    Page<Object> findAllCustomerBought(Pageable pageable);

    @Query(value = "select b.update_date ,count(b.id), sum(b.total), sum(bd.number) from BillEntity b join BillDetailEntity bd on b.id = bd.bill.id " +
            "where b.update_date between :open and :end GROUP BY b.update_date order by b.update_date DESC")
    Page<Object> soSpBandc4(@Param("open") LocalDate open, @Param("end") LocalDate end, Pageable pageable);
}
