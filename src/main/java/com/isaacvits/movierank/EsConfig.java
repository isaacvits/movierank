package com.isaacvits.movierank;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.mapping.ElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchPersistentEntity;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.MappingElasticsearchEntityInformation;

import com.isaacvits.movierank.model.Movie;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class EsConfig {

	private static final String EsHost = "localhost";

	private static final int EsPort = 9300;

	private static final String EsClusterName = "elasticsearch";

	private static ElasticsearchOperations elasticsearchOperations;
	
	private static MappingElasticsearchEntityInformation<Movie, String> entityInformation;

	private static Client getClient() {

		Settings esSettings = Settings.settingsBuilder().put("cluster.name", EsClusterName).build();

		// https://www.elastic.co/guide/en/elasticsearch/guide/current/_transport_client_versus_node_client.html
		try {
			return TransportClient.builder().settings(esSettings).build()
					.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(EsHost), EsPort));
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ElasticsearchOperations getInstanceElasticsearchTemplate() {

		if (elasticsearchOperations == null) {
			elasticsearchOperations = new ElasticsearchTemplate(getClient());
		}

		return elasticsearchOperations;

	}
	
	public static ElasticsearchEntityInformation<Movie, String> getEntityInformation(){
		if(entityInformation == null) {
			ElasticsearchPersistentEntity<Movie> persistentEntityFor = getInstanceElasticsearchTemplate().getPersistentEntityFor(Movie.class);
			entityInformation = new MappingElasticsearchEntityInformation<Movie, String>(
					persistentEntityFor);
		}
		return entityInformation;
	}

	// Embedded Elasticsearch Server
	/*
	 * @Bean public ElasticsearchOperations elasticsearchTemplate() { return new
	 * ElasticsearchTemplate(nodeBuilder().local(true).node().client()); }
	 */

}
