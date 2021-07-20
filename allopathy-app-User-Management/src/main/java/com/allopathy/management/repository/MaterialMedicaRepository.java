package com.allopathy.management.repository;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.allopathy.management.constant.ClassIdConstants;
import com.allopathy.management.data.model.MaterialMedica;
import com.allopathy.management.exception.CustomResponseStatusException;
import com.allopathy.management.exception.ErrorCodeEnum;
import com.couchbase.client.core.error.DecodingFailureException;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonArray;
import com.couchbase.client.java.kv.GetResult;
import com.couchbase.client.java.kv.MutationResult;
import com.couchbase.client.java.query.QueryOptions;
import com.couchbase.client.java.query.QueryResult;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MaterialMedicaRepository {

	private final Cluster cluster;
	private final Bucket bucket;
	private final Collection collection;	
	
	
	@Autowired
	public MaterialMedicaRepository(Cluster cluster, Bucket bucket) {
		this.cluster = cluster;
		this.bucket = bucket;
		this.collection = bucket.defaultCollection();
	}
	
	private String n1qlSelectEntityDefaultWhere(String bucketName,
			boolean isCount) {
		String selectEntity = n1qlSelectEntity(bucketName, isCount);
		selectEntity += " WHERE `classId` = '" + ClassIdConstants.MaterialMedica_Doc
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
	
	private List<MaterialMedica> getEntities(String query, JsonArray parameters) {
		log.debug("Query: \"{}\", Parameters: {}", query, parameters);
		QueryOptions options = QueryOptions.queryOptions()
				.parameters(parameters);
		QueryResult result = cluster.query(query, options);
		return result.rowsAs(MaterialMedica.class);
	}
	
	public Optional<MaterialMedica> findById(String id) {
		log.debug("Get document by id {}", id);
		try {
			GetResult result = collection.get(id);
			MaterialMedica account = result.contentAs(MaterialMedica.class);
			return Optional.ofNullable(account);
		} catch (DocumentNotFoundException | DecodingFailureException e) {
			return Optional.empty();
		}
	}
	
	public List<MaterialMedica> findAll() {
		String query = n1qlSelectEntityDefaultWhere(bucket.name(), false);
		return getEntities(query, JsonArray.create());
	}
	
	public MaterialMedica save(MaterialMedica materialMedica) {
		MutationResult result;
		Optional<MaterialMedica> copy = findById(materialMedica.getMedicalId());
		if (copy.isPresent()) {
			log.error("Document with this id already saved: {}",materialMedica);
			throw new CustomResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
					ErrorCodeEnum.EX2002);
		}
		if(materialMedica != null && StringUtils.isNotBlank( materialMedica.getId())) {
			log.debug("Insert document {}", materialMedica);
			try {
				result = collection.insert(materialMedica.getMedicalId(),
						materialMedica);
			} catch (DocumentExistsException e) {
				throw e;
			}
		}
		else {
			log.error("Document Id not Set for {}",materialMedica);
		}
		return materialMedica;
	}
	
}
