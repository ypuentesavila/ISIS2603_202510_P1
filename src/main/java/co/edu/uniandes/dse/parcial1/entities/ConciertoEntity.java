package co.edu.uniandes.dse.parcial1.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class ConciertoEntity extends BaseEntity {

    private String nombre;
    private Long presupuesto;
    private String nombreArtista;
    private LocalDate fechaConcierto;
    private int capacidadConcierto;

    @PodamExclude
    @ManyToOne
    private EstadioEntity estadio; 
}

