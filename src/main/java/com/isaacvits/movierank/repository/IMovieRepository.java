package com.isaacvits.movierank.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.isaacvits.movierank.model.Movie;

public interface IMovieRepository extends ElasticsearchRepository<Movie, String> {
	public List<Movie> getMoviesNowPlaying();
}
