package com.Hayati.Reservation.des.Hotels.responses;

public class LoginResponse {
    private String token;
    private long expiresIn;
    private String userType;
    private Object userData;

    public String getToken() {
        return token;
    }

    public LoginResponse setToken(String token) {
        this.token = token;
        return this;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public LoginResponse setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public Object getUserData() {
        return userData;
    }

    public LoginResponse setUserData(Object userData) {
        this.userData = userData;
        return this;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", expiresIn=" + expiresIn +
                ", userType='" + userType + '\'' +
                ", userData=" + userData +
                '}';
    }
}
