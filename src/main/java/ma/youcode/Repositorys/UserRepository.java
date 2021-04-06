package ma.youcode.Repositorys;

import ma.youcode.Exceptions.AuthException;
import ma.youcode.Models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByEmail(String email) throws AuthException;

    Users findByEmailAndPwd(String email, String password) throws AuthException;

    Long countByEmail(String email);

    Users findById(Integer userId);
}
