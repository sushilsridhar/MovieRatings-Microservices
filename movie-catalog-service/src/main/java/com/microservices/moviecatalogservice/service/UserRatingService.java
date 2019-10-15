package com.microservices.moviecatalogservice.service;

import com.microservices.moviecatalogservice.models.Rating;
import com.microservices.moviecatalogservice.models.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingService {

    @Autowired
    RestTemplate restTemplate;

    // circuit breaker pattern

    @HystrixCommand(fallbackMethod = "getFallBackUserRating",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            })
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsData/user/" + userId, UserRating.class);
    }

    public UserRating getFallBackUserRating(String userId) {
        UserRating userRating = new UserRating();
        userRating.setUserId(userId);
        userRating.setRatings(Arrays.asList(
                new Rating("100", 0)
        ));

        return userRating;
    }

    // hystric setup for bulkhead pattern

    /*@HystrixCommand(fallbackMethod = "getFallBackUserRating",
            threadPoolKey = "ratingsDataPool",
            threadPoolProperties = {
                @HystrixProperty(name = "coreSize", value = "20"),
                @HystrixProperty(name = "maxQueueSize", value = "10")
            }
            )
      public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://ratings-data-service/ratingsData/user/" + userId, UserRating.class);
     }*/
}
