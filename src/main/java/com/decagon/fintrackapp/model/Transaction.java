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
    private ETransactionStatus status;


    @ManyToOne
    private TransactionCategory transactionCategory;
    @ManyToOne
    private TransactionType TransactionType;

}
