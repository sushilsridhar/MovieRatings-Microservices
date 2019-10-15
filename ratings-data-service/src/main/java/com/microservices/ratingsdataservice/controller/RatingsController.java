package com.microservices.ratingsdataservice.controller;

import com.microservices.ratingsdataservice.models.Rating;
import com.microservices.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsData")
public class RatingsController {

    @RequestMapping("movies/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 9);
    }

    @RequestMapping("user/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {

         List<Rating> ratings = Arrays.asList(
                new Rating("100", 9),
                new Rating("101",8)
        );

         UserRating userRating = new UserRating();
         userRating.setRatings(ratings);
         return userRating;
    }
}
