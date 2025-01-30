package ru.gotika.gotikaback.review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gotika.gotikaback.restaurant.model.Restaurant;
import ru.gotika.gotikaback.restaurant.repository.RestaurantRepository;
import ru.gotika.gotikaback.review.dto.ReviewDto;
import ru.gotika.gotikaback.review.mapper.ReviewMapper;
import ru.gotika.gotikaback.review.model.Review;
import ru.gotika.gotikaback.review.repository.ReviewRepository;
import ru.gotika.gotikaback.review.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;
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

    @Transactional
    @Override
    public ReviewDto createReview(ReviewDto reviewDto) {

        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        Review review = reviewMapper.reviewDtoToReview(reviewDto);
        review.setCreatedAt(LocalDateTime.now());

        Review savedReview = reviewRepository.save(review);

        updateAverageRating(savedReview.getRestaurant().getId());

        return reviewMapper.reviewToReviewDto(reviewRepository.save(review));
    }

    @Transactional
    public void updateAverageRating(Long restaurantId){
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {

            Restaurant restaurant = restaurantOptional.get();
            List<Review> reviews = restaurant.getReviewList();

            int sum = reviews.stream()
                    .map(Review::getRating)
                    .reduce(0, Integer::sum);

            float averageRating = reviews.isEmpty() ? 0 : (float) sum / reviews.size();

            averageRating = Math.max(1, Math.min(averageRating, 5));

            restaurant.setRating(averageRating);
            restaurantRepository.save(restaurant);
        }
    }

    @Transactional
    @Override
    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        if (reviewDto.getRating() != null && (reviewDto.getRating() < 1 || reviewDto.getRating() > 5)) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        return reviewRepository.findById(id).map(review -> {
            Review updatedReview = reviewMapper.reviewDtoToReview(reviewDto);
            updatedReview.setId(review.getId());
            reviewRepository.save(updatedReview);
            updateAverageRating(updatedReview.getRestaurant().getId());
            return reviewMapper.reviewToReviewDto(updatedReview);
        }).orElse(null);
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
