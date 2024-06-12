package com.his.webtool.repository;


import com.his.webtool.entity.UserInform;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface UserInformRepository extends ReactiveCrudRepository<UserInform, Long> {

    /**
     * Retrieves {@link UserInform} username.
     *
     * @param username must not be {@literal null}.
     * @return the entity with the given id or {@literal Mono#empty()} if none found.
     * @throws IllegalArgumentException if {@literal id} is {@literal null}.
     */
    Mono<UserInform> findUserInformByUsername(String username);
}
