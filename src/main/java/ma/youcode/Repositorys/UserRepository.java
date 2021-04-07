package ma.youcode.Repositorys;

import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email) throws AuthException;

    Long countByEmail(String email);


    List<Users> findByType(String type);

    Users findById(Integer userId);
}
