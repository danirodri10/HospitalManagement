package org.example.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;

//definimos la creación de la clave compuesta
@Embeddable
public class RecibePK implements Serializable {
    @Column(name = "tratamiento_id")
    int tratamientoId;

    @Column(name = "id_paciente")
    int pacienteId;

    @Column(name = "fecha_inicio")
    LocalDate fechaInicio;
}
