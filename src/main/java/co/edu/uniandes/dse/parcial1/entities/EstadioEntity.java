package co.edu.uniandes.dse.parcial1.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EstadioEntity extends BaseEntity {
    private String nombre;
    private Long precioAlquiler;
    private String nombreCiudad;
    private Integer capacidadMaxima;

    @PodamExclude //sacado de lo del bookstore porque la relacion en teoria es la misma como editorial y el libroxd
    @OneToMany(mappedBy = "estadio", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<ConciertoEntity> conciertos = new ArrayList<>();
}

