package com.epam.ryzvanovich.backendservicesmentoring.data;

import com.epam.ryzvanovich.backendservicesmentoring.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
