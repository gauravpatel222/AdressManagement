package org.example.adreessmanagement.repository;

import org.example.adreessmanagement.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {


    AuthUser findByEmail(String attr0);
}
