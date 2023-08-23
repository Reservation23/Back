package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.dto.ReservationDto;
import zerobase.reservation.dto.Result;
import zerobase.reservation.service.ReservationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> create(
            @RequestBody ReservationDto reservationDto
    ) {
        reservationDto = reservationService.join(reservationDto);
        return ResponseEntity.ok(reservationDto);
    }


    @GetMapping("/reservations/member/{memberId}")
    public ResponseEntity<Result> findAllByMemberId(
            @PathVariable("memberId") Long id
    ) {
        List<ReservationDto> reservationDtos = reservationService.findAllByMemberId(id);
        return ResponseEntity.ok(new Result(reservationDtos.size(), reservationDtos));
    }

    @GetMapping("/reservations/store/{storeId}")
    public ResponseEntity<Result> findAllByStoreId(
            @PathVariable("storeId") Long id
    ) {
        List<ReservationDto> reservationDtos = reservationService.findAllByStoreId(id);
        return ResponseEntity.ok(new Result(reservationDtos.size(), reservationDtos));
    }

    @GetMapping("reservations/without_review/member/{memberId}")
    public ResponseEntity<Result> findAllConfirmedReservationWithoutReview(
            @PathVariable("memberId") Long id
    ) {
        List<ReservationDto> reservationDtosWithoutReview = reservationService.findAllConfirmedReservationWithoutReview(id);
        return ResponseEntity.ok(new Result(reservationDtosWithoutReview.size(), reservationDtosWithoutReview));
    }

    @PutMapping("/reservation/confirm")
    public ResponseEntity<ReservationDto> confirm(
            @RequestParam Long reservationId
    ) {
        ReservationDto reservation = reservationService.confirmReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/reservation/cancel")
    public ResponseEntity<ReservationDto> cancel(
            @RequestParam Long reservationId
    ) {
        ReservationDto reservation = reservationService.cancelReservation(reservationId);
        return ResponseEntity.ok(reservation);
    }
}
