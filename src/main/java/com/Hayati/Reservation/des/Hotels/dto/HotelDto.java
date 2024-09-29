package com.Hayati.Reservation.des.Hotels.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Getter
@Setter
public class HotelDto {

    private long id_hot;
    private String name;
    private String emplacement;
    private String evaluation;
    private Double latitude;
    private Double longitude;
    private String commentaires;
    private String notifications;
    private String imageUrl;
    private String description;
    private Set<Long> servicesIds;  // Set to hold service IDs associated with the hotel
    
    // Chaining setters for fluent usage
    public HotelDto setId_hot(long id_hot) {
        this.id_hot = id_hot;
        return this;
    }

    public HotelDto setName(String name) {
        this.name = name;
        return this;
    }

    public HotelDto setEmplacement(String emplacement) {
        this.emplacement = emplacement;
        return this;
    }

    public HotelDto setEvaluation(String evaluation) {
        this.evaluation = evaluation;
        return this;
    }

    public HotelDto setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public HotelDto setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public HotelDto setCommentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public HotelDto setNotifications(String notifications) {
        this.notifications = notifications;
        return this;
    }

    public HotelDto setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public HotelDto setDescription(String description) {
        this.description = description;
        return this;
    }

    public HotelDto setServicesIds(Set<Long> servicesIds) {
        this.servicesIds = servicesIds;
        return this;
    }

    // Getters (standard getters for the DTO fields)
    public long getId_hot() {
        return id_hot;
    }

    public String getName() {
        return name;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public String getNotifications() {
        return notifications;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public Set<Long> getServicesIds() {
        return servicesIds;
    }
}
