package com.mundane.mail.utils;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

public class ActivationCodeGenerator {

    private static final String VALID_CHARACTERS = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int CODE_LENGTH = 20;
    private static final SecureRandom random = new SecureRandom();

    public static void main(String[] args) {
        String activationCode = generateActivationCode();
        System.out.println("Generated Activation Code: " + activationCode);
    }

    public static String generateActivationCode() {
        Set<Character> charSet = new HashSet<>();
        String code = "";

        while (charSet.size() < CODE_LENGTH) {
            char nextChar = VALID_CHARACTERS.charAt(random.nextInt(VALID_CHARACTERS.length()));
            if (!charSet.contains(nextChar)) {
                charSet.add(nextChar);
                code += nextChar;
            }
        }

        return code;
    }
}
