package com.avizhen.repository;

import com.avizhen.entity.User;
import com.avizhen.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = :name")
    User findByUsername(@Param("name") String name);

    Page<User> findByUsername(String username, Pageable pageable);

    Page<User> findByRole( Role role, Pageable pageable);

    Page<User> findByRoleAndUsername(Role role, String username, Pageable pageable);
}
