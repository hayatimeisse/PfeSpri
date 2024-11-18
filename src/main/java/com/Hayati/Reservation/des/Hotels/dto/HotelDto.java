package com.Hayati.Reservation.des.Hotels.dto;

import com.Hayati.Reservation.des.Hotels.enumeration.Status;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) // Enable method chaining
public class HotelDto {
    private Long id_hot;
    private String name;
    private String emplacement;
    private String evaluation;
    private Double latitude;
    private Double longitude;
    private String description;
    private String commentaires;
    private String notifications;
    private String imageUrl;
    private Status status;

    private Long subscribe_id; 
    public Long getSubscribe_id() {
        return subscribe_id;
    }

    public HotelDto setSubscribe_id(Long subscribe_id) {
        this.subscribe_id = subscribe_id;
        return this;
    }
}
