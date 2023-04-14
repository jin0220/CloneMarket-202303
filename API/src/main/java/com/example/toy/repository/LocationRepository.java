package com.example.toy.repository;

import com.example.toy.dto.LocationDto;
import com.example.toy.entity.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long>{
        @Query(value = "SELECT " +
                "new com.example.toy.dto.LocationDto( " +
                "l.district, " +
                "l.city, " +
                "l.town, " +
                "l.township, " +
                "( 6371 * ACOS( COS( RADIANS(:latitude) ) * COS( RADIANS( latitude ) ) * COS( RADIANS( longitude ) - RADIANS(:longitude) ) + SIN( RADIANS(:latitude) ) * SIN( RADIANS( latitude ) ) ) ) AS distance) " +
            "FROM Location as l " +
            "WHERE town != '0' " +
            "ORDER BY distance ")
    List<LocationDto> findNearLocation(@Param("latitude") String latitude, @Param("longitude") String longitude, Pageable pageable);
}