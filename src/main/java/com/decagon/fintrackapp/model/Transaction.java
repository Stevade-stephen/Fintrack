package com.decagon.fintrackapp.model;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

@Getter
@Setter
@Table(name = ("transactions"))
@Entity
public class Transaction extends BaseModel{
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String amount;
    @NotBlank
    @Column(name = ("receipt_urls"))
    @Lob
    private String receiptUrls;
    @NotBlank
    @CreationTimestamp
    private Instant closedAt;

    @Enumerated(EnumType.STRING)
    private EStatus status;

    @Enumerated(EnumType.STRING)
    private ECategory category;


    @ManyToOne
    private TransactionType TransactionType;


    public Transaction(@NotBlank String title, @NotBlank String description, @NotBlank String amount,
                       @NotBlank String receiptUrls, ECategory category) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.receiptUrls = receiptUrls;
    }

    public Transaction() {
    }
}
