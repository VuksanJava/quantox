package com.quantox.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "maschine")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Maschine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String uid;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_create_by")
    private User createdBy;

    private Status status;

    @Column(name = "CREATE", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @Column(name = "ACTIVE")
    private Boolean active;

}
