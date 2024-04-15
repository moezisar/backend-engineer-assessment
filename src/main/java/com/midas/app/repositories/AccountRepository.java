package com.midas.app.repositories;

import com.midas.app.models.Account;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
  Optional<Account> findAccountById(UUID uid);
}
