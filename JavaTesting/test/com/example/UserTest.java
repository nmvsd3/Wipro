package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    static User user = null;
    static int i = 0;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        user = new User("madhu123", "admin", "pass@123");
        System.out.println("User object initialized");
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
        user = null;
        System.out.println("User object set to null");
    }

    @BeforeEach
    void setUp() throws Exception {
        // optional
    }

    @AfterEach
    void tearDown() throws Exception {
        i++;
        System.out.println("Test case : " + i + " passed");
    }

    @Test
    void testUsername() {
        assertEquals("madhu123", user.getUsername());
    }

    @Test
    void testRole() {
        assertEquals("admin", user.getRole());
    }

    @Test
    void testPassword() {
        assertEquals("pass@123", user.getPassword());
    }
}
