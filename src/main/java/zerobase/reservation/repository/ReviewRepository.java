package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.dao.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByMemberId(Long memberId);
    List<Review> findAllByMemberIdAndContentIsNotNull(Long memberId);

    List<Review> findAllByStoreId(Long storeId);
    List<Review> findAllByStoreIdAndContentIsNotNull(Long storeId);
}
