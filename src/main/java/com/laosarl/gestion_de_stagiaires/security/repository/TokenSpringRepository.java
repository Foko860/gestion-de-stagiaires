package com.laosarl.gestion_de_stagiaires.security.repository;

import com.laosarl.gestion_de_stagiaires.security.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenSpringRepository extends JpaRepository<Token, UUID> {

    @Query("""
        select t from Token t inner join UserNew u on t.user.id = u.id
        where u.id = :userId and (t.expired = false and t.revoked = false)
        """)
    List<Token> findAllValidTokensByUserId(UUID userId);

    Optional<Token> findByValue(String token);

    List<Token> findByUserIdAndExpiredFalseAndRevokedFalse(UUID userId);
}
