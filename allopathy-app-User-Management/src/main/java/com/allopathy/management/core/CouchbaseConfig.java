package com.allopathy.management.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;

@Configuration
public class CouchbaseConfig {

	@Autowired
	Cluster cluster;

    @Autowired
    Environment environment;

    @Bean
	public Bucket couchbaseBucket() {
		return cluster.bucket(environment
				.getRequiredProperty("spring.couchbase.bucket-name"));
	}
	
}
