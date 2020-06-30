package com.meeting.book.configuration;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@Configuration
public class DBConfiguration {
	
	
	  @Value("${spring.datasource.url}") 
	  String url;

	
	 @Bean 
	 public MongoClient mongoClient() { 
		 ConnectionString connectionString = new ConnectionString(url);
		 CodecRegistry pojoCodecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		 CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), 
                 pojoCodecRegistry);
		 MongoClientSettings clientSettings = MongoClientSettings.builder()
                 .applyConnectionString(connectionString)
                 .codecRegistry(codecRegistry)
                 .build();
		 return MongoClients.create(clientSettings);
	 
	 }
	 
}
