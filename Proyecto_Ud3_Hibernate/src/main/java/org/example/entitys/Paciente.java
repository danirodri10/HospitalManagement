package org.example.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = "paciente")
public class Paciente {
    @Id
    private int id;

    @NonNull
    private String nombre;
    @NonNull
    private LocalDate fecha_nacimiento;
    @NonNull
    private String direccion;

    @OneToMany(mappedBy = "paciente", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Cita> citas = new ArrayList<>();

    @OneToMany(mappedBy = "paciente", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Recibe> listaRecibe = new ArrayList<>();

    //método para añadir citas y establecer la bidireccionalidad Cita-Paciente
    public void addCita(Cita cita) {
        this.citas.add(cita);
        cita.setPaciente(this);
    }
}
