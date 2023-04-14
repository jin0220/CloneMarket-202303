package com.example.toy.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
public class LocationDto {

    private long num;
    private String district;
    private String city;
    private String town;
    private String township;
    private String village;
    private String latitude;
    private String longitude;
    private double distance;

    public LocationDto(String district, String city, String town, String township, double distance) {
        this.district = district;
        this.city = city;
        this.town = town;
        this.township = township;
        this.distance = distance;
    }
}
