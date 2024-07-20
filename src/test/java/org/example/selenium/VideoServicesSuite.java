package org.example.selenium;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        YoutubeTest.class,
        RutubeTest.class
})
public class VideoServicesSuite{}
