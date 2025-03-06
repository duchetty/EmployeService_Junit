package com.mc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="employe_id_gen",sequenceName = "employe_seq_gen", allocationSize = 1)
public class Employe {
    @Id
    @GeneratedValue(generator ="employe_id_gen")
    private long id;
    private String empName;
    private String email;
    private String address;
    private String salary;


}
