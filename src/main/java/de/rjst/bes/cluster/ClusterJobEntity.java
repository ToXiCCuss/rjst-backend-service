package de.rjst.bes.cluster;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cluster_job")
public class ClusterJobEntity {

    @Id
    @Column(nullable = false)
    private String id;

    private String hostname;

    private LocalDateTime created;

}
