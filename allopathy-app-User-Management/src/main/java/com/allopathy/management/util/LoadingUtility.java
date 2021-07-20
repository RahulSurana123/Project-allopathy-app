package com.allopathy.management.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;

import org.springframework.stereotype.Service;

import com.allopathy.management.security.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoadingUtility {

	public User readUsers(String file) {
		String jsonObject = "";
		User user = null;
		String accountFileName = "default-data" + File.separator + file;
		log.debug("getting default user file {}", accountFileName);
		try (InputStream inputStream = getClass().getClassLoader()
				.getResourceAsStream(accountFileName)) {
			jsonObject = IOUtils.toString(inputStream,
					StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			log.error("Error while retrieving account file " + accountFileName,
					e);
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			user = mapper.readValue(jsonObject, User.class);
		} catch (IOException e) {
			log.warn("IOException in parsing JSON request: " + e.getMessage());
		}
		log.debug("User retieved and stored in the DB : {}", user);
		return user;
	}
}
