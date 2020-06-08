/*
 * Copyright (C) 2018 HAWKORE S.L. (http://hawkore.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hawkore.ignite.connector.examples;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * IgniteConnectorSamplesSpringBootApp
 *
 * <p>
 *
 * Sample SpringBoot app to show Hawkore's Apache Ignite connector integration
 *
 * @author Manuel Núñez (manuel.nunez@hawkore.com)
 *
 */
@SpringBootApplication
@ImportResource("classpath:client-app-config.xml")
public class IgniteConnectorSamplesSpringBootApp extends SpringBootServletInitializer {

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper m = new ObjectMapper();
		m.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		return m;
	}

    /**
     *
     * @param args
     */
	public static void main(String[] args) {

	    System.setProperty("env", "test");

	    new IgniteConnectorSamplesSpringBootApp().configure(new SpringApplicationBuilder(IgniteConnectorSamplesSpringBootApp.class)).run(args);
	}

}

