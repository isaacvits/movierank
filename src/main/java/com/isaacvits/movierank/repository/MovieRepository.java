package com.isaacvits.movierank.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;

import com.isaacvits.movierank.EsConfig;
import com.isaacvits.movierank.model.Movie;

public class MovieRepository extends SimpleElasticsearchRepository<Movie> implements IMovieRepository {

	public MovieRepository() {
		super(EsConfig.getEntityInformation(),
				EsConfig.getInstanceElasticsearchTemplate());
	}

	public List<Movie> getMoviesNowPlaying() {
		// TODO Auto-generated method stub
		return null;
	}

}
