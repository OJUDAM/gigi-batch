//package com.ujo.test.common;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.event.ApplicationStartedEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApplicationStartupValue implements ApplicationListener<ApplicationStartedEvent> {
//
//    @Value("${puzzle.apiKey}")
//    private String puzzleApiKey;
//    @Value("${puzzle.statisticsUrl}")
//    private String puzzleStatisticsUrl;
//    @Value("${puzzle.exitUrl}")
//    private String puzzleExitUrl;
//
//
//    @Override
//    public void onApplicationEvent(ApplicationStartedEvent event) {
//        System.out.println("puzzleApiKey = " + puzzleApiKey);
//        System.out.println("puzzleStatisticsUrl = " + puzzleStatisticsUrl);
//        System.out.println("puzzleExitUrl = " + puzzleExitUrl);
//    }
//}