package com.factory.contabancaria.repository;

import com.factory.contabancaria.model.ContasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContasRepository extends JpaRepository<ContasModel, Long> {

    /*@Query(value="SELECT * FROM tb_contas WHERE nome_do_usuario = :nome", nativeQuery = true)
    Optional<ContasModel> buscarPorNome(@Param("nome") String nome);*/

    Optional<ContasModel> findByNomeDoUsuario(String nome);
}
