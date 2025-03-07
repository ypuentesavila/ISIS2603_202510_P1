package co.edu.uniandes.dse.parcial1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcial1.entities.EstadioEntity;
import co.edu.uniandes.dse.parcial1.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcial1.repositories.EstadioRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EstadioService {
    @Autowired
    private EstadioRepository estadioRepository;

    @Transactional
    public EstadioEntity createEstadio(EstadioEntity estadioEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación del estadio");

        if (estadioEntity.getNombreCiudad() == null || estadioEntity.getNombreCiudad().trim().length() < 3) {
            throw new IllegalOperationException("El nombre de la ciudad debe tener al menos 3 caracteres");
        }
        if (estadioEntity.getCapacidadMaxima() <= 1000) {
            throw new IllegalOperationException("La capacidad del estadio debe ser mayor a 1000 personas");
        }

        if (estadioEntity.getPrecioAlquiler() <= 100000) {
            throw new IllegalOperationException("El precio de alquiler debe ser mayor a 100000 dólares");
        }
        log.info("Termina proceso de creación del estadio");
        return estadioRepository.save(estadioEntity);
        
    }
}
