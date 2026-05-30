package br.org.edu.ifrn.LojaCarro.integration;

import br.org.edu.ifrn.LojaCarro.LojaCarroApplication;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroServiceValidado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LojaCarroApplication.class)
@Transactional
class CarroIntegrationTest {

    @Autowired
    private CarroServiceValidado carroService;

    // ---------------- CAMINHOS FELIZES ----------------

    @Test
    @Rollback
    void deveSalvarCarroNoBanco() {
        Carro carro = new Carro();
        carro.setModelo("Gol");
        carro.setAno(2020);

        Carro salvo = carroService.save(carro);

        assertNotNull(salvo.getId());
        assertEquals("Gol", salvo.getModelo());
    }

    @Test
    @Rollback
    void deveAtualizarCarroExistente() {
        Carro carro = new Carro();
        carro.setModelo("Onix");
        carro.setAno(2022);

        Carro salvo = carroService.save(carro);

        salvo.setAno(2023);

        Carro atualizado = carroService.update(salvo);

        assertEquals(2023, atualizado.getAno());
    }

    @Test
    @Rollback
    void deveDeletarCarroPorId() {
        Carro carro = new Carro();
        carro.setModelo("HB20");
        carro.setAno(2021);

        Carro salvo = carroService.save(carro);

        carroService.deleteById(salvo.getId());

        Optional<Carro> resultado = carroService.findById(salvo.getId());

        assertTrue(resultado.isEmpty());
    }

    @Test
    @Rollback
    void deveEncontrarCarroPorId() {
        Carro carro = new Carro();
        carro.setModelo("Fiesta");
        carro.setAno(2019);

        Carro salvo = carroService.save(carro);

        Optional<Carro> resultado = carroService.findById(salvo.getId());

        assertTrue(resultado.isPresent());
        assertEquals("Fiesta", resultado.get().getModelo());
    }

    @Test
    @Rollback
    void deveListarTodosOsCarros() {
        Carro c1 = new Carro();
        c1.setModelo("Gol");
        c1.setAno(2020);

        Carro c2 = new Carro();
        c2.setModelo("Onix");
        c2.setAno(2022);

        carroService.save(c1);
        carroService.save(c2);

        List<Carro> carros = carroService.findAll();

        assertEquals(2, carros.size());
    }

    // ---------------- CAMINHOS DE ERRO ----------------

    @Test
    @Rollback
    void deveFalharAoSalvarCarroComNomeMuitoGrande() {
        Carro carro = new Carro();
        carro.setModelo("X".repeat(200));
        carro.setAno(2020);

        assertThrows(RuntimeException.class, () -> {
            carroService.save(carro);
        });
    }

    @Test
    @Rollback
    void deveFalharAoSalvarCarroSemModelo() {
        Carro carro = new Carro();
        carro.setModelo(null);
        carro.setAno(2020);

        assertThrows(RuntimeException.class, () -> {
            carroService.save(carro);
        });
    }

    @Test
    @Rollback
    void deveFalharAoSalvarCarroComAnoInvalido() {
        Carro carro = new Carro();
        carro.setModelo("Teste");
        carro.setAno(1500);

        assertThrows(RuntimeException.class, () -> {
            carroService.save(carro);
        });
    }

    @Test
    @Rollback
    void deveFalharAoAtualizarCarroInexistente() {
        Carro carro = new Carro();
        carro.setId(999L);
        carro.setModelo("Uno");
        carro.setAno(2020);

        assertThrows(RuntimeException.class, () -> {
            carroService.update(carro);
        });
    }

    @Test
    @Rollback
    void deveRetornarVazioAoBuscarCarroInexistente() {
        Optional<Carro> resultado = carroService.findById(999999L);

        assertTrue(resultado.isEmpty());
    }
}