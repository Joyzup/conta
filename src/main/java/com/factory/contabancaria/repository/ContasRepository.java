package com.factory.contabancaria.repository;

import com.factory.contabancaria.model.ContasModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContasRepository extends JpaRepository<ContasModel, Long> {
    Optional<ContasModel> findByNomeDoUsuarioLikeIgnoreCase(@Nullable String nomeDoUsuario);
}
