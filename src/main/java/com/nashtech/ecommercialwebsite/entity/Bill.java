package com.nashtech.ecommercialwebsite.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "bills")
@RequiredArgsConstructor
@Getter @Setter
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer ID;

    //Đã duyệt hay chưa duyệt
    @Column(name = "status")
    private Integer status;

    //Lấy ngày giờ hiện tại
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "create_date")
    private Date createDate;

    //Tong so tien duoc giam cua don hang - 1 don hang co the gom nhieu sp
    @Column(name = "discount_total")
    private BigDecimal discountTotal;

    //Tong so tien cua don hang
    @Column(name = "price_total")
    private BigDecimal priceTotal;

    @JsonIgnore
    @OneToMany(mappedBy = "bill")
    Set<BillDetail> billDetails;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

}
