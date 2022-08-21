package com.his.webtool.repository;

import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.his.webtool.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  @Transactional(readOnly = true)
  Optional<Users> findByUsername(String username);
}
