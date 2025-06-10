package org.example.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "hospital")
public class Hospital {
    @Id
    private int id;

    @NonNull
    private String nombre;
    @NonNull
    private String ubicacion;

    @OneToMany(mappedBy = "hospital", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Tratamiento> tratamientos = new ArrayList<>();

    //método para establecer la bidireccionalidad Tratamiento-Hospital
    public void addTratamiento(Tratamiento tratamiento) {
        this.tratamientos.add(tratamiento);
        tratamiento.setHospital(this);
    }
}
