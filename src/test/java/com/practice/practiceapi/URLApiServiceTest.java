package com.practice.practiceapi;

import com.practice.practiceapi.services.URLApiService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLApiServiceTest extends PracticeApiApplicationTests {

    @Autowired
    private URLApiService urlApiService;
    private static String shortUrl = "nicoapp.com/1df018842d92bf0e2ffdf0734e297b6e952a443425046daaa977678961d0281c7";
    private static String originalUrl = "http://www.facebook.com";

    @Test
    void testExpand() {
        assertEquals(originalUrl, urlApiService.expand(shortUrl));
    }

}
