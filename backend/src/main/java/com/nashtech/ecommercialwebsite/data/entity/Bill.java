package com.nashtech.ecommercialwebsite.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "bills")
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter @Builder
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private  Integer id;

    //Đã duyệt hay chưa duyệt
    @Column(name = "status")
    private Integer status = 0;

    //Lấy ngày giờ hiện tại
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    @Column(name = "create_date")
    private Date createDate;

    //Tong so tien cua don hang
    @Column(name = "price_total")
    private Integer priceTotal;

    @JsonIgnore
    @OneToMany(mappedBy = "bill", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BillDetail> billDetails = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Account account;

}
