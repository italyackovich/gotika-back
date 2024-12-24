package ru.gotika.gotikaback.review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.review.mapper.ReviewMapper;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.review.repository.ReviewRepository;
import ru.gotika.gotikaback.review.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDto> getAllReviews() {
        return reviewMapper.reviewListToReviewDtoList(reviewRepository.findAll());
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        return reviewMapper.reviewToReviewDto(reviewRepository.findById(id).orElse(null));
    }

    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = reviewMapper.reviewDtoToReview(reviewDto);
        review.setCreatedAt(LocalDateTime.now());
        return reviewMapper.reviewToReviewDto(reviewRepository.save(review));
    }

    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        return reviewRepository.findById(id).map(review -> {
            reviewRepository.save(reviewMapper.reviewDtoToReview(reviewDto));
            return reviewDto;
        }).orElse(null);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
