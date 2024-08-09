package com.Hayati.Reservation.des.Hotels.utils;


import java.util.concurrent.ThreadLocalRandom;

public class OperationIdGenerator {

    /**
     * Generates a unique operation ID using the current timestamp and a random number.
     * The format of the operation ID is <timestamp><randomNumber>.
     *
     * @return A unique, numeric operation ID.
     */
    public static String generate() {
        // Get the current timestamp in milliseconds
        long timestamp = System.currentTimeMillis();

        // Generate a random long number
        long randomNum = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);

        // Combine timestamp and random number to create a unique operation ID
        return String.valueOf(timestamp) + String.valueOf(randomNum);
    }
}
