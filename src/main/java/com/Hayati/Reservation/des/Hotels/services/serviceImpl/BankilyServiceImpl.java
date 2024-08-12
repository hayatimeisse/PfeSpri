package com.Hayati.Reservation.des.Hotels.services.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.Hayati.Reservation.des.Hotels.dto.BankilyAuthResponse;
import com.Hayati.Reservation.des.Hotels.dto.BankilyPaymentRequest;
import com.Hayati.Reservation.des.Hotels.dto.PaymentResponse;
import com.Hayati.Reservation.des.Hotels.dto.TransactionCheckRequest;
import com.Hayati.Reservation.des.Hotels.dto.TransactionCheckResponse;
import com.Hayati.Reservation.des.Hotels.exceptions.PaymentException;
import com.Hayati.Reservation.des.Hotels.services.BankilyAuthService;
import com.Hayati.Reservation.des.Hotels.services.BankilyService;

import static com.Hayati.Reservation.des.Hotels.utils.OperationIdGenerator.generate;




@RequiredArgsConstructor
@Service
public class BankilyServiceImpl implements BankilyService {


    @Value("${payment.server.url}")
    private String paymentServerUrl;

    @Value("${transaction.server.url}")
    private  String CHECK_TRANSACTION_URL;

    private final BankilyAuthService authService;
    private final RestTemplate restTemplate;

    @Override
    public PaymentResponse makePayment(BankilyPaymentRequest request) throws PaymentException {
        // Authenticate and retrieve the token
        BankilyAuthResponse authResponse = authService.authenticate();
        if (authResponse == null || authResponse.getAccess_token() == null) {
            throw new PaymentException("Authentication failed");
        }
        // Set the operationId in the request automatically
        String generatedOperationId = generate(); // Make sure this is correctly referring to your utility class
        request.setOperationId(generatedOperationId);

        // Create the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + authResponse.getAccess_token());
        HttpEntity<BankilyPaymentRequest> entity = new HttpEntity<>(request, headers);

        // Send the payment request
        ResponseEntity<PaymentResponse> paymentResponse = restTemplate.postForEntity(paymentServerUrl, entity, PaymentResponse.class);
        // Check if the payment succeeded
        if (paymentResponse.getBody() != null) {
            PaymentResponse responseBody = paymentResponse.getBody();

            // Check the errorCode in the payment response
            switch (responseBody.getErrorCode()) {
                case "0": // Success
                    // Proceed to check the transaction
                    TransactionCheckResponse checkResponse = checkTransaction(generatedOperationId, authResponse.getAccess_token());
                    if (!"TS".equals(checkResponse.getStatus())) {
                        // If the transaction was not successful, you can throw an exception or handle the error as necessary
                        throw new PaymentException("Transaction check failed: status = " + checkResponse.getStatus());
                    }
                    break;
                case "2": // Invalid token
                    return paymentResponse.getBody();
                case "4": // Operation ID required
                    return paymentResponse.getBody();
                case "1": // Other error
                default:
                    return paymentResponse.getBody();
            }
        } else {
            // The payment failed due to no response body, consider this as an internal error
            throw new PaymentException("Payment failed: No response from payment service");
        }
        return paymentResponse.getBody(); // Return the response from the payment service
    }


    public TransactionCheckResponse checkTransaction(String operationId, String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);

            TransactionCheckRequest request = new TransactionCheckRequest();
            request.setOperationId(operationId);

            HttpEntity<TransactionCheckRequest> entity = new HttpEntity<>(request, headers);
            ResponseEntity<TransactionCheckResponse> response = restTemplate.postForEntity(CHECK_TRANSACTION_URL, entity, TransactionCheckResponse.class);

            // Check the response status here and handle different cases
            TransactionCheckResponse checkResponse = response.getBody();
            if (checkResponse != null && "TS".equals(checkResponse.getStatus())) {
                // Handle the transaction success
            } else if (checkResponse != null && "TF".equals(checkResponse.getStatus())) {
                // Handle the transaction failure
            } else if (checkResponse != null && "TA".equals(checkResponse.getStatus())) {
                // Handle the ambiguous transaction response
            }
            return checkResponse;

        } catch (ResourceAccessException e) {
            // Handle the timeout
            throw new PaymentException("Transaction check timed out");
        } catch (RestClientException e) {
            // Handle other HTTP client exceptions
            throw new PaymentException("Transaction check failed: " + e.getMessage());
        }
    }
}
