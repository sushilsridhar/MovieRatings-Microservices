package com.microservices.moviecatalogservice.models;

import java.util.List;

public class UserRating {

    public UserRating() {

    }

    private String userId;

    private List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
