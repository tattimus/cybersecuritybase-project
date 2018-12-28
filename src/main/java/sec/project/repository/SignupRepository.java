package sec.project.repository;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sec.project.domain.Signup;

public interface SignupRepository extends JpaRepository<Signup, Long> {
    @Query(value = "SELECT * FROM Signup WHERE name=?1", nativeQuery = true)
    List<Signup> getSignup(String name);

    final String qry = "INSERT INTO Signup (name, address) VALUES (?1, ?2)";
    @Transactional
    @Modifying
    @Query(value = qry , nativeQuery = true)
    void addSignup(String name, String address);
}
