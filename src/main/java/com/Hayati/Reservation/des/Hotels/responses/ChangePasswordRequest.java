package com.Hayati.Reservation.des.Hotels.responses;

public class ChangePasswordRequest {

    private String newPassword;
    private String confirmPassword;
    private String token;  // Optional: for authentication purposes

    // Constructors
    public ChangePasswordRequest() {}

    public ChangePasswordRequest(String newPassword, String confirmPassword, String token) {
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.token = token;
    }

    // Getters and Setters
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
