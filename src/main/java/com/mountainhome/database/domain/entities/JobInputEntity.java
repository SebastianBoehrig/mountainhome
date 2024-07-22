package com.mountainhome.database.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "job_input")
public class JobInputEntity extends JobResourceEntity{
}
