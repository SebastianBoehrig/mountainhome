package com.mountainhome.database.domain.entities;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "world_state")
public class WorldStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer day;
}
