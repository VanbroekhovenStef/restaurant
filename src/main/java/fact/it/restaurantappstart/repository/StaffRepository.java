package fact.it.restaurantappstart.repository;

import fact.it.restaurantappstart.model.Staff;
import fact.it.restaurantappstart.model.Waiter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Waiter> findStaffById(long id);

    @Query("select w from Waiter w")
    List<Waiter> findWaiters();
}
