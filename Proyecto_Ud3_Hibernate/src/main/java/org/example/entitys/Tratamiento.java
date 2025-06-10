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
@Table(name = "tratamiento")
public class Tratamiento {
    @Id
    private int id;

    @NonNull
    private String tipo;
    @NonNull
    @Column(columnDefinition = "Decimal(10,2)")//adaptamos el atributo costo a la definición del campo de la tabla
    private Double costo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id_hospital")
    private Hospital hospital;

    @OneToMany(mappedBy = "tratamiento", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<Recibe> listaRecibe = new ArrayList<>();
}
