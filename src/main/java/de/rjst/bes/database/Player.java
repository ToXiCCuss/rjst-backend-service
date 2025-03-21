package de.rjst.bes.database;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private ProcessState processState;

    private String pod;

    private String thread;

    private BigInteger balance = BigInteger.ZERO;

    private LocalDateTime created;

    private LocalDateTime updated;

}
