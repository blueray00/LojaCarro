package br.org.edu.ifrn.LojaCarro.integration;

import br.org.edu.ifrn.LojaCarro.LojaCarroApplication;
import br.org.edu.ifrn.LojaCarro.model.Carro;
import br.org.edu.ifrn.LojaCarro.services.CarroService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(classes = LojaCarroApplication.class)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class CarroIntegrationTest {

    @Autowired
    private CarroService carroService;

    @Autowired
    private MockMvc mockMvc;


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

        Exception exception = assertThrows(Exception.class, () -> {
            carroService.save(carro);
        });

        assertTrue(exception.getMessage().contains("modelo"));
    }


    @Test
    @Rollback
    void deveFalharAoSalvarCarroSemModelo() {

        Carro carro = new Carro();
        carro.setModelo(null);
        carro.setAno(2020);

        Exception exception = assertThrows(Exception.class, () -> {
            carroService.save(carro);
        });

        assertTrue(exception.getMessage().contains("obrigatório"));
    }


    @Test
    @Rollback
    void deveFalharAoSalvarCarroComAnoInvalido() {

        Carro carro = new Carro();
        carro.setModelo("Teste");
        carro.setAno(1500);

        Exception exception = assertThrows(Exception.class, () -> {
            carroService.save(carro);
        });

        assertTrue(exception.getMessage().contains("Ano inválido"));
    }


    // ---------------- TESTE COM @Sql ----------------


    @Test
    @Sql(
            scripts = "/dados.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    void deveListarCarrosComSql() throws Exception {

        mockMvc.perform(get("/carro"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].modelo").value("Civic"));
    }
}