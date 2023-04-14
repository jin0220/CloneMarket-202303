package com.example.toy.service;

import com.example.toy.dto.LocationDto;
import com.example.toy.entity.Location;
import com.example.toy.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    private final LocationRepository repository;

    public List<LocationDto> getLocation(int page, String latitude, String longitude) {
        Pageable pageable = PageRequest.of(page, 20);
        List<LocationDto> list = repository.findNearLocation(latitude, longitude, pageable);
        for(int i = 0; i < 20; i++){
            log.info(list.get(i).getTown());
        }
        return repository.findNearLocation(latitude, longitude, pageable);
    }
}
