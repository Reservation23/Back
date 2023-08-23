package zerobase.reservation.dto;

import lombok.*;
import zerobase.reservation.dao.Member;
import zerobase.reservation.dao.Reservation;
import zerobase.reservation.dao.Review;
import zerobase.reservation.dao.Store;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long memberId;
    private Long storeId;
    private Long reservationId;
    private Long reviewId;
    private String content;

    public static Review toReviewEntity(Member member, Store store, Reservation reservation, ReviewDto reviewDto) {
        return new Review().builder()
                .member(member)
                .store(store)
                .reservation(reservation)
                .content(reviewDto.getContent())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
