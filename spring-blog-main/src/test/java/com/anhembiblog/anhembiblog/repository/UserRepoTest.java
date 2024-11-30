package com.anhembiblog.anhembiblog.repository;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

import com.anhembiblog.anhembiblog.model.User;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
public class UserRepoTest {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("Deve retornar o User de forma bem-sucedida do BD")
    void testFindByUsernameCase1() {
        // Dados do usuário
        String username = "Caso1";
        User user = new User(username, "123456", "caso1@gmail.com");
        entityManager.persist(user);
        entityManager.flush();

        // Buscando pelo usuário
        Optional<User> foundUser = userRepo.findByUsername(username);

        // Verifica se o usuário foi encontrado
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Não retorna o User no BD quando o User não existe")
    void testFindByUsernameCase2() {
        // Busca por um usuário que não existe
        Optional<User> foundUser = userRepo.findByUsername("UsuarioInexistente");

        // Verifica que o usuário não é vazio
        assertThat(foundUser).isEmpty();
    }
}