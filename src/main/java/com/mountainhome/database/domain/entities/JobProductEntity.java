package com.mountainhome.database.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "job_product")
public class JobProductEntity extends JobResourceEntity{
}
