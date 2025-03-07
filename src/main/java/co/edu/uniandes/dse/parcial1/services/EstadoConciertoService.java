package co.edu.uniandes.dse.parcial1.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.ConciertoEntity;
import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.EntityNotFoundException;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.ConciertoRepository;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadoConciertoService {

    @Autowired
    private ConciertoRepository conciertoRepository;

    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public ConciertoEntity replaceEstadio(Long conciertoId, Long estadioId) throws EntityNotFoundException, IllegalOperationException {
        log.info("incio proceso de asociar el estadio con ID {} al concierto con ID {}", estadioId, conciertoId);

        Optional<ConciertoEntity> conciertoEntity = conciertoRepository.findById(conciertoId);
        if (conciertoEntity.isEmpty())
            throw new EntityNotFoundException("concierto no encontrado");

        Optional<EstadioEntity> estadioEntity = estadioRepository.findById(estadioId);
        if (estadioEntity.isEmpty())
            throw new EntityNotFoundException("estadio no encontrado");

        ConciertoEntity concierto = conciertoEntity.get();
        EstadioEntity estadio = estadioEntity.get();

        if (concierto.getCapacidadConcierto() > estadio.getCapacidadMaxima()) {
            throw new IllegalOperationException("laa capacidad del concierto no puede superar la capacidad del estadio.");
        }

        if (estadio.getPrecioAlquiler() > concierto.getPresupuesto()) {
            throw new IllegalOperationException("El precio de alquiler del estadio no puede superar el presupuesto del concierto.");
        }

        List<ConciertoEntity> conciertosPrevios = estadio.getConciertos();
        for (ConciertoEntity c : conciertosPrevios) {
            long diferenciaDias = ChronoUnit.DAYS.between(c.getFechaConcierto(), concierto.getFechaConcierto());
            if (Math.abs(diferenciaDias) < 2) {
                throw new IllegalOperationException("debe haber al menos 2 días de diferencia entre los conciertos en el mismo estadio.");
            }
        }
        concierto.setEstadio(estadio);
        log.info("se ha asociado el estadio con id al concierto con id", estadioId, conciertoId);
        return conciertoRepository.save(concierto);
    }
}
