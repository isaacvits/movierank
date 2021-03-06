package com.isaacvits.movierank.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.isaacvits.movierank.model.Movie;

public interface IMovieService {

	public Page<Movie> getMoviesNowPlaying(int qtd);
	
	public void setMoviesNowPlaying();

	public Movie save(Movie movie);
	
	public Page<Movie> getListMovieByTitle(String title);
	
	public Page<Movie> getListMovieVoteCount(Integer startCount, Integer endCount);
	
	public Page<Movie> getListMovieImdbRate(Double startRate, Double endRate);
	
	public Page<Movie> getListMovieImdbRateByVote(Double startRate, Double endRate, 
			Integer startCount, Integer endCount);
	
	public Page<Movie> getListMovieImdbRateByVoteByDate(Double startRate, Double endRate, 
			Integer startCount, Integer endCount, Integer startYear, Integer endYear);
	
	public Page<Movie> getListMovieImdbRateByVoteByDateByGenre(Double startRate, Double endRate, 
			Integer startCount, Integer endCount, Integer startYear, Integer endYear, String genres);
	
	public Page<Movie> getListMovieImdbRateByVoteByDateByGenreByName(Double startRate, Double endRate, Integer startCount,
			Integer endCount, Integer startYear, Integer endYear, String genres, String title, int qtd);
	
	public Page<Movie> getMovie(Pageable pageable);
	
	public Movie findMovieById(String id);
	
	public boolean existMovie(String id);
}
