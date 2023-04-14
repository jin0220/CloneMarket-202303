package com.example.toy.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long num;
    private String district;
    private String city;
    private String town;
    private String township;
    private String village;
    private String latitude;
    private String longitude;
}
