package org.example.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "doctor")
public class Doctor {
    @Id
    private int id;

    @NonNull
    private String nombre;
    @NonNull
    private String especialidad;
    @NonNull
    private String telefono;


    @OneToOne(mappedBy = "doctor", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Cita cita;

}
