package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dao.Member;
import zerobase.reservation.dao.Reservation;
import zerobase.reservation.dao.Review;
import zerobase.reservation.dao.Store;
import zerobase.reservation.dto.ReservationDto;
import zerobase.reservation.repository.MemberRepository;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.StoreRepository;

import java.util.List;
import java.util.stream.Collectors;

import static zerobase.reservation.dto.ReservationDto.toReservationEntity;
import static zerobase.reservation.type.ReservationStatus.CANCELED;
import static zerobase.reservation.type.ReservationStatus.CONFIRMED;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    private static Review review;
    public ReservationDto join(ReservationDto reservationDto) {
        Member member = memberRepository.findById(reservationDto.getMemberId()).get();
        Store store = storeRepository.findById(reservationDto.getStoreId()).get();
        Review review = null;

        Reservation reservation = reservationRepository.save(toReservationEntity(member, store, review, reservationDto));
        return reservationToDto(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> findAllByMemberId(Long memberId) {
        return reservationRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::reservationToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> findAllByStoreId(Long StoreId) {
        return reservationRepository.findAllByStoreId(StoreId)
                .stream()
                .map(this::reservationToDto)
                .collect(Collectors.toList());
    }

    public ReservationDto confirmReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.setReservationStatus(CONFIRMED);
        //TODO : 예약시간 10분 안에 들어온 요청인지 확인 필요.
        return reservationToDto(reservation);
    }

    public ReservationDto cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        reservation.setReservationStatus(CANCELED);
        //TODO : 시간안에 확정 못할시에도 취소되도록 해야함.
        return reservationToDto(reservation);
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> findAllConfirmedReservationWithoutReview(Long memberId) {
        return reservationRepository
                .findByMemberIdAndReservationStatusAndReviewIsNull(memberId, CONFIRMED)
                .stream()
                .map(this::reservationToDto)
                .collect(Collectors.toList());
    }

    private ReservationDto reservationToDto(Reservation reservation) {
        return new ReservationDto().builder()
                .memberId(reservation.getMember().getId())
                .storeId(reservation.getStore().getId())
                .reservationId(reservation.getId())
                .time(reservation.getTime())
                .reservationStatus(reservation.getReservationStatus())
                .build();
    }
}
