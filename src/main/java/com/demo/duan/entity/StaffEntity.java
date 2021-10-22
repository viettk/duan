package com.demo.duan.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Staff")
public class StaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="token")
    private String token;

    @Column(name="name")
    private String name;

    @Column(name="role")
    private Integer role;

    @Column(name="status")
    private boolean status;

    @Column(name="phone")
    private String phone;

    @OneToMany(mappedBy = "staff")
    private List<ReceiptEntity> receipts;

   /*Hóa đơn*/
    @OneToMany(mappedBy = "id")
    private List<BillEntity> bills;
}
