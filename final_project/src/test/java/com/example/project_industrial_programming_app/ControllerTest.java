package com.example.project_industrial_programming_app;

import encryption.AESUtil;
import expression.Regular;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private MainWindowController controller;

    @BeforeEach
    void initializeController() {
        controller = new MainWindowController();
    }

    @Test
    void verifyEncryptionDecryption() {
        String plainText = "Sample text for encryption";
        String key = "encryptionKey";

        String encrypted = AESUtil.encrypt(plainText, key);
        String decrypted = AESUtil.decrypt(encrypted);

        assertEquals(plainText, decrypted, "Encryption and decryption processes are inconsistent.");
    }

    @Test
    void validateExpressionEvaluation() {
        Regular evaluator = new Regular();
        String expression = "3 * (4 + 5) - 6";
        String evaluationResult = evaluator.EvaluateExpression(expression);

        String expectedResult = "3 * (4 + 5) - 6 = 21.0"; // Adjusted to match actual output format
        assertEquals(expectedResult, evaluationResult, "Expression evaluation failed to produce the expected result.");
    }
}