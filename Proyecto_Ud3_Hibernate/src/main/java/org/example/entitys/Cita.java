package org.example.entitys;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cita")
public class Cita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private LocalDate fecha;
    @NonNull
    private String estado;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    //método para emplear la asignación bidireccional Citas-Doctores
    public void setDoctorBidireccional(Doctor doctor) {
        this.doctor = doctor;
        doctor.setCita(this);
    }
}
