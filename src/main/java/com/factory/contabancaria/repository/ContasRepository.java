package com.factory.contabancaria.repository;

import com.factory.contabancaria.model.ContasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContasRepository extends JpaRepository<ContasModel, Long> {

     @Query("SELECT nome FROM ContasModel nome WHERE LOWER (nome.nomeDoUsuario) = LOWER(:nomeDoUsuario)")
     Optional<ContasModel> findByNome(String nomeDoUsuario);

}

