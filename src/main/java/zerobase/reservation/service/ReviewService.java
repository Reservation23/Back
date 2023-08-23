package zerobase.reservation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.reservation.dao.Member;
import zerobase.reservation.dao.Reservation;
import zerobase.reservation.dao.Review;
import zerobase.reservation.dao.Store;
import zerobase.reservation.dto.ReviewDto;
import zerobase.reservation.repository.MemberRepository;
import zerobase.reservation.repository.ReservationRepository;
import zerobase.reservation.repository.ReviewRepository;
import zerobase.reservation.repository.StoreRepository;
import zerobase.reservation.type.ReservationStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static zerobase.reservation.dto.ReviewDto.toReviewEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final ReservationRepository reservationRepository;

    public ReviewDto join(ReviewDto reviewDto) {
        Member member = memberRepository.findById(reviewDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("No Member found with ID: " + reviewDto.getMemberId()));

        Store store = storeRepository.findById(reviewDto.getStoreId())
                .orElseThrow(() -> new NoSuchElementException("No Store found with ID: " + reviewDto.getStoreId()));

        Reservation reservation = reservationRepository.findById(reviewDto.getReservationId())
                .orElseThrow(() -> new NoSuchElementException("No Reservation found with ID: " + reviewDto.getReservationId()));

        // Check if reservation already has a review
        if(reservation.getReview() != null) {
            throw new IllegalStateException("This reservation already has a review.");
        }
        if(reservation.getReservationStatus()!= ReservationStatus.CONFIRMED){
            throw new IllegalStateException("예약이 확정되고 나서 사용 가능합니다.");
        }

        Review review = reviewRepository.save(toReviewEntity(member, store, reservation, reviewDto));

        reservation.setReview(review);

        return convertToDto(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findAllByMemberId(Long memberId) {
        return reviewRepository.findAllByMemberIdAndContentIsNotNull(memberId)
                .stream()
                .filter(review -> review.getStore() != null && review.getReservation() != null)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<ReviewDto> findAllByStoreId(Long StoreId) {
        return reviewRepository.findAllByStoreIdAndContentIsNotNull(StoreId)
                .stream()
                .filter(review -> review.getStore() != null && review.getReservation() != null)
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto updateStore(ReviewDto reviewDto, Long id) {
        Review review = reviewRepository.findById(id).get();

        if (reviewDto.getContent() != null && !reviewDto.getContent().isEmpty()) {
            review.setContent(reviewDto.getContent());
        }

        review.setUpdatedAt(LocalDateTime.now());
        return convertToDto(review);
    }

    public void deleteStore(Long id) {
        Optional<Review> reviewOpt = reviewRepository.findById(id);

        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();

            // 연관된 Reservation의 외래 키를 null로 설정
            Reservation reservation = review.getReservation();
            if (reservation != null) {
                reservation.setReview(null);
                reservationRepository.save(reservation);
            }

            // Review 삭제
            reviewRepository.deleteById(id);
        }
    }

    private ReviewDto convertToDto(Review review) {
        return ReviewDto.builder()
                .memberId(review.getMember().getId())
                .storeId(review.getStore().getId())
                .reservationId(review.getReservation().getId())
                .reviewId(review.getId())
                .content(review.getContent())
                .build();
    }
}
