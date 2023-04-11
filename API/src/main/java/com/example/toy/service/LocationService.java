package com.example.toy.service;

import com.example.toy.dto.LocationDto;
import com.example.toy.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final LocationRepository repository;

    public List<LocationDto> getLocation(double latitude, double longitude) {
        log.info("1111" + repository.findLocation(latitude, longitude).get(0).getTown());
        return repository.findLocation(latitude, longitude);
    }
}
