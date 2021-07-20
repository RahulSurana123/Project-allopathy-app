package com.allopathy.management.repository;

import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.allopathy.management.constant.ClassIdConstants;
import com.allopathy.management.security.model.User;
import com.couchbase.client.core.error.CasMismatchException;
import com.couchbase.client.core.error.DecodingFailureException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.kv.ReplaceOptions;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserRepository {

	private static final String REFERENCE_LIST_ID_SUFFIX = "::"
			+ ClassIdConstants.USER_DOC_ID;
	
	private final Cluster cluster;
	private final Bucket bucket;
	private final Collection collection;
	
	@Autowired
	public UserRepository(Cluster cluster, Bucket bucket) {
		this.cluster = cluster;
		this.bucket = bucket;
		this.collection = bucket.defaultCollection();
	}
	
	@Autowired
	private Environment environment;
	
	private String n1qlSelectEntityDefaultWhere(String bucketName,
			boolean isCount) {
		String selectEntity = n1qlSelectEntity(bucketName, isCount);
		selectEntity += " WHERE `classId` = '" + ClassIdConstants.USER_DOC_ID
				+ "'";
		return selectEntity;
	}

	private String n1qlSelectEntity(String bucketName, boolean isCount) {
		String b = "`" + bucketName + "`";
		String entity = "META(" + b + ").cas AS version";
		String count = "COUNT(*) AS count";
		String selectEntity;
		if (isCount) {
			selectEntity = "SELECT " + count + " FROM " + b;
		} else {
			selectEntity = "SELECT " + b + ".* " + ", " + entity + " FROM " + b;
		}
		return selectEntity;
	}

	private String n1qlDeleteEntityDefaultWhere(String bucketName,
			boolean isCount) {
		String deleteEntity = n1qlDeleteEntity(bucketName);
		deleteEntity += " WHERE `classId` = '" + ClassIdConstants.USER_DOC_ID
				+ "'";
		return deleteEntity;
	}
	
	private String n1qlDeleteEntity(String bucketName) {
		String b = "`" + bucketName + "`";
		String deleteEntity = "DELETE FROM " + b;
		return deleteEntity;
	}	
	
	public Optional<User> findById(String id) {
		log.debug("Get document by id {}", id);
		try {
			GetResult result = collection.get(id);
			User user = result.contentAs(User.class);
			user.setVersion(String.valueOf(result.cas()));
			return Optional.of(user);
		} catch (DocumentNotFoundException | DecodingFailureException e) {
			return Optional.empty();
		}
	}
	
	public User save(User user) {
		MutationResult result;
		String version = user.getVersion();
		user.setVersion(null);
		if (StringUtils.isNotBlank(version)) {
			log.debug("Replace document {}", user);
			long cas = Long.parseLong(version);
			ReplaceOptions options = ReplaceOptions.replaceOptions().cas(cas);
			try {
				result = collection.replace( user.getId(),
						user, options);
			} catch (CasMismatchException e) {
				throw e;
			} catch (DocumentNotFoundException e) {
				throw e;
			}
		} else {
			log.debug("Insert document {}", user);
			try {
				result = collection.insert(user.getId(),
						user);
			} catch (DocumentExistsException e) {
				throw e;
			}
		}
		user.setVersion(String.valueOf(result.cas()));
		return user;
	}
	
}
