package org.example.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Recibe {

    @EmbeddedId//le asignamos la clave compuesta completa
    RecibePK id;

    @NonNull
    private LocalDate fecha_fin;

    @ManyToOne
    @MapsId("pacienteId")//Enlazamos la propiedad de la identidad con la de la clave compuesta (RecibePK)
    @JoinColumn(name = "id_paciente")
    Paciente paciente;

    @ManyToOne
    @MapsId("tratamientoId")
    @JoinColumn(name = "tratamiento_id")
    Tratamiento tratamiento;
}
