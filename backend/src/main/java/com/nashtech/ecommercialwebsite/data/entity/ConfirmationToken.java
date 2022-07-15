package com.nashtech.ecommercialwebsite.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "confirmation_token")
@NoArgsConstructor
@Getter @Setter
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, name = "create_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(nullable = false,
    name = "user_id")
    private Account account;

    @Column(nullable = false, name = "expired_at")
    private LocalDateTime expiredAt;

    @Column(nullable = true, name = "confirmed_at")
    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token,
                             LocalDateTime createAt,
                             LocalDateTime expiredAt,
                             Account account) {
        this.token = token;
        this.createAt = createAt;
        this.expiredAt = expiredAt;
        this.account = account;
    }
/*
    public ConfirmationToken(Long id,
                             String token,
                             LocalDateTime createAt,
                             Account account,
                             LocalDateTime expiredAt,
                             LocalDateTime confirmedAt) {
        this.id = id;
        this.token = token;
        this.createAt = createAt;
        this.account = account;
        this.expiredAt = expiredAt;
        this.confirmedAt = confirmedAt;
    }*/


}
