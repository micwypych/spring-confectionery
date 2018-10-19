package pl.edu.agh.kis.databases.confectionery.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.kis.databases.confectionery.domain.Chocolate;

@Repository
public interface ChocolateRepository extends JpaRepository<Chocolate,String> {

}
