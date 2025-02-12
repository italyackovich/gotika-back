package ru.gotika.gotikaback.review.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gotika.gotikaback.restaurant.exception.RestaurantNotFoundException;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.review.exception.ReviewNotFoundException;
import ru.gotika.gotikaback.review.mapper.ReviewMapper;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.review.repository.ReviewRepository;
import ru.gotika.gotikaback.review.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;

    @Override
    public List<ReviewDto> getAllReviews() {
        log.info("Get all reviews");
        return reviewMapper.reviewListToReviewDtoList(reviewRepository.findAll());
    }

    @Override
    public ReviewDto getReviewById(Long id) {
        log.info("Get review with id {}", id);
        return reviewMapper.reviewToReviewDto(reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + id + " not found")));
    }

    @Transactional
    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {

        Review review = reviewMapper.reviewDtoToReview(reviewDto);
        review.setCreatedAt(LocalDateTime.now());
        Review savedReview = reviewRepository.save(review);

        updateAverageRating(savedReview.getRestaurant().getId());
        log.info("Review created: {}", savedReview);
        return reviewMapper.reviewToReviewDto(savedReview);
    }

    public void updateAverageRating(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id " + restaurantId + " not found"));
        List<Review> reviews = restaurant.getReviewList();

        float averageRating = (float) reviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0f);

        averageRating = Math.max(1, Math.min(averageRating, 5));
        restaurant.setRating(averageRating);
        restaurantRepository.save(restaurant);
        log.info("Review updated averageRating: {} for restaurant with id {}", averageRating, restaurantId);
    }

    @Transactional
    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        return reviewRepository.findById(id).map(review -> {
            Review updatedReview = reviewMapper.reviewDtoToReview(reviewDto);
            updatedReview.setId(review.getId());
            reviewRepository.save(updatedReview);
            updateAverageRating(updatedReview.getRestaurant().getId());
            log.info("Review with id {} updated: {}", id, updatedReview);
            return reviewMapper.reviewToReviewDto(updatedReview);
        }).orElseThrow(() -> new ReviewNotFoundException("Review with id " + id + " not found"));
    }

    @Transactional
    @Override
    public void deleteReview(Long id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review with id " + id + " not found"));
        reviewRepository.deleteById(id);
        updateAverageRating(review.getRestaurant().getId());
        log.info("Review with id {} deleted", id);
    }
}
