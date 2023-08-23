package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.dao.Store;

import javax.transaction.Transactional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Transactional
    void deleteById(Long id);
}
