package com.factory.contabancaria.repository;

import com.factory.contabancaria.model.ContasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ContasRepository extends JpaRepository<ContasModel, Long> {
    @Query("SELECT c.valorAtualConta FROM ContasModel c WHERE c.id = :id")
    BigDecimal findValorAtualContaById(@Param("id") Long id);

    @Query("SELECT c.id FROM ContasModel c WHERE c.nomeDoUsuario LIKE %:nomedousuario%")
    Long findIdByNomeDoUsuario(@Param("nomedousuario") String nomedousuario);
}
