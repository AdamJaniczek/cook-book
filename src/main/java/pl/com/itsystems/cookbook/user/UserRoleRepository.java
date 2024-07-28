package pl.com.itsystems.cookbook.user;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);

    Set<UserRole> findAll();
}