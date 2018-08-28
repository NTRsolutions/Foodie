package com.belac.ines.foodie;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginTest {

    @Test
    public void checkCredentials() {

        String email = "h";
        String pass = "h";
        Login login = new Login();

        int output = login.checkCredentials(email, pass);
        assertEquals(0, output);

        output = login.checkCredentials("", pass);
        assertEquals(3, output);

        output = login.checkCredentials(email, "");
        assertEquals(2, output);

        output = login.checkCredentials("", "");
        assertEquals(1, output);


    }
}