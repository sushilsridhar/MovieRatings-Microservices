package com.microservices.moviecatalogservice.service;

import com.microservices.moviecatalogservice.models.CatalogItem;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.Rating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class MovieInfoService {

    @Autowired
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem",
        commandProperties = {
                @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
        })
    public CatalogItem getCatalogItem(Rating rating) {
        // Reactive programming in java
         /* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/"+ rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block(); */

        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/"+ rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getMovieName(),"DC movie", rating.getRating());
    }

    public CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("no movie", "", rating.getRating());
    }
}
