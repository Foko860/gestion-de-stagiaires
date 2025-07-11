package com.laosarl.gestion_de_stagiaires.Repository;

import com.laosarl.gestion_de_stagiaires.domain.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Query("""
        select t from Token t inner join Supervisor u on t.user.id = u.id
        where u.id = :userId and (t.expired = false and t.revoked = false)
        """)
    List<Token> findAllValidTokensByUserId(UUID userId);

    Optional<Token> findByValue(String token);
}
