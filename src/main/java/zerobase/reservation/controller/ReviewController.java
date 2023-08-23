package zerobase.reservation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.reservation.dto.Result;
import zerobase.reservation.dto.ReviewDto;
import zerobase.reservation.service.ReviewService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ReviewDto> create(
            @RequestBody ReviewDto reviewDto
    ) {
        reviewDto = reviewService.join(reviewDto);
        return ResponseEntity.ok(reviewDto);
    }

    @GetMapping("/reviews/member/{memberId}")
    public ResponseEntity<Result> findAllByMemberId(
            @PathVariable("memberId") Long id
    ) {
        List<ReviewDto> reviewDtosByMemberId = reviewService.findAllByMemberId(id);
        return ResponseEntity.ok(new Result(reviewDtosByMemberId.size(), reviewDtosByMemberId));
    }

    @GetMapping("/reviews/store/{storeId}")
    public ResponseEntity<Result> findAllByStoreId(
            @PathVariable("storeId") Long id
    ) {
        List<ReviewDto> reviewDtosByMemberId = reviewService.findAllByStoreId(id);
        return ResponseEntity.ok(new Result(reviewDtosByMemberId.size(),reviewDtosByMemberId));
    }

    @PutMapping("/review/{reviewId}")
    public ResponseEntity<ReviewDto> update(
            @RequestBody ReviewDto reviewDto,
            @PathVariable("reviewId") Long id
    ) {
        reviewDto = reviewService.updateStore(reviewDto, id);
        return ResponseEntity.ok(reviewDto);
    }

    @DeleteMapping("/review/{reviewId}")
    public void delete(
            @PathVariable("reviewId") Long id
    ) {
        reviewService.deleteStore(id);
    }
}
