package com.Hayati.Reservation.des.Hotels.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardInfoDTO {

    @NotBlank
    @Pattern(regexp = "\\d{16}", message = "Card number must be 16 digits")
    private String cardNumber;

    @NotBlank
    private String cardholderName;

    @NotBlank
    @Pattern(regexp = "(0[1-9]|1[0-2])/\\d{2}", message = "Expiration date must be in MM/YY format")
    private String expirationDate;

    @NotBlank
    @Pattern(regexp = "\\d{3,4}", message = "CVC must be 3 or 4 digits")
    private String cvc;

    @NotBlank
    private String country;

    // Getters and Setters
    //...
}
