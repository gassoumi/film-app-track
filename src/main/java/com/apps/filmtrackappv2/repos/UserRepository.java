package com.apps.filmtrackappv2.repos;

import com.apps.filmtrackappv2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
