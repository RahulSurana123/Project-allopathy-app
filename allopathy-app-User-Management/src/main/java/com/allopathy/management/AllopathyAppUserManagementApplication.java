package com.allopathy.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.event.EventListener;

import com.allopathy.management.service.impl.DefaultDataLoadingServiceImpl;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@EnableDiscoveryClient
@Slf4j
public class AllopathyAppUserManagementApplication {

	@Autowired
	DefaultDataLoadingServiceImpl dataLoadingServiceImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(AllopathyAppUserManagementApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() {
		log.debug("Setting Up Application to have default user");
		dataLoadingServiceImpl.deafaultDataLoading();
	}
}
