package zerobase.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import zerobase.reservation.dao.Reservation;
import zerobase.reservation.type.ReservationStatus;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMemberId(Long memberId);
    List<Reservation> findAllByStoreId(Long memberId);

    List<Reservation> findByMemberIdAndReservationStatusAndReviewIsNull(Long memberId, ReservationStatus reservationStatus);

}
