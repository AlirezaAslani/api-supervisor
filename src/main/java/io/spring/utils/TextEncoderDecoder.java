package io.spring.utils;

import java.util.Base64;

public class TextEncoderDecoder {

    // Method to encode a string
    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    // Method to decode a string
    public static String decode(String encoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(encoded);
        return new String(decodedBytes);
    }

    public static void main(String[] args) {



        // Example usage
        String originalText = "hello";
        System.out.println("Original Text: " + originalText);

        String encodedText = encode(originalText);
        System.out.println("Encoded Text: " + encodedText);

        String decodedText = decode(encodedText);
        System.out.println("Decoded Text: " + decodedText);
    }
}
