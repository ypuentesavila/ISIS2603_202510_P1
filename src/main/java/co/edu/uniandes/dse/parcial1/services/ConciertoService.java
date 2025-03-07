package co.edu.uniandes.dse.parcial1.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConciertoService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity createConcierto(ConciertoEntity conciertoEntity) throws EntityNotFoundException, IllegalOperationException {
        log.info("Inicia proceso de creacion del concierto");
        if (conciertoEntity.getEstadio() == null)
            throw new IllegalOperationException("El estadio no es valido");

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(conciertoEntity.getEstadio().getId());
        if (estadioEntity.isEmpty())
            throw new IllegalOperationException("El estadio no es valido");
        if (conciertoEntity.getFechaConcierto().isBefore(LocalDate.now()))
            throw new IllegalOperationException("La fecha del concierto no puede ser en el pasado");

        if (conciertoEntity.getCapacidadConcierto() <= 10)
            throw new IllegalOperationException("La capacidad del concierto debe ser mayor a 10 personas");

        if (conciertoEntity.getPresupuesto() <= 1000)
            throw new IllegalOperationException("El presupuesto debe ser mayor a 1000 dólares");

        conciertoEntity.setEstadio(estadioEntity.get());
        log.info("Termina proceso de creación del concierto"); //persistencia del concierto creado y asociado asu estadaio
        return conciertoRepository.save(conciertoEntity);
    }
}

