package br.org.edu.ifrn.LojaCarro.services;

import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.repository.CarroRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CarroServiceValidado {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private Validator validator;

    public Carro save(Carro c) {
        validar(c);
        return carroRepository.save(c);
    }

    public Carro update(Carro c) {

        if (c.getId() == null || !carroRepository.existsById(c.getId())) {
            throw new RuntimeException("Carro não encontrado");
        }

        validar(c);
        return carroRepository.save(c);
    }

    public void deleteById(Long id) {

        if (!carroRepository.existsById(id)) {
            throw new RuntimeException("Carro não encontrado");
        }

        carroRepository.deleteById(id);
    }

    public Optional<Carro> findById(Long id) {
        return carroRepository.findById(id);
    }

    public List<Carro> findAll() {
        return carroRepository.findAll();
    }

    private void validar(Carro carro) {
        Set<ConstraintViolation<Carro>> violations = validator.validate(carro);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}