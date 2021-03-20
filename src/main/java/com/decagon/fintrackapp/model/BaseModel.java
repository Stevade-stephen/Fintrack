package com.decagon.fintrackapp.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 255)
    String slug;


    @CreationTimestamp
    @Column(name = ("created_at"))
    private LocalDateTime createdAt;


    @UpdateTimestamp
    @Column(name = ("updated_at"))
    private LocalDateTime updatedAt;

    @Column(name = ("deleted_at"))
    private LocalDateTime deletedAt;

    /**
     * bellow are FKs to the user table
     */
    @NotNull
    @Column(name = ("created_by"))

    private User createdBy;

    @NotNull
    @Column(name = ("updated_by"))
    private Long updatedBy;

    @Nullable
    @Column(name = ("deleted_by"))
    private Long deletedBy;

//Hello

}
