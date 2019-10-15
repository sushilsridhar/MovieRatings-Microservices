package com.microservices.moviecatalogservice.controller;

import com.microservices.moviecatalogservice.models.CatalogItem;
import com.microservices.moviecatalogservice.models.Movie;
import com.microservices.moviecatalogservice.models.Rating;
import com.microservices.moviecatalogservice.models.UserRating;
import com.microservices.moviecatalogservice.service.MovieInfoService;
import com.microservices.moviecatalogservice.service.UserRatingService;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogController {

    @Autowired
    WebClient.Builder webClientBuilder;

    @Autowired
    MovieInfoService movieInfoService;

    @Autowired
    UserRatingService userRatingService;

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

        UserRating ratings = userRatingService.getUserRating(userId);

        // WebClient.Builder builder = WebClient.builder();

        return ratings.getRatings()
                .stream()
                .map(rating -> movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }
}
