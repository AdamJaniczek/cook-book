package pl.com.itsystems.cookbook.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findAllUsersByRoles_Name(String role);

    List<User> findAll();

    void deleteByEmail(String email);

}