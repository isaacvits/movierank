package com.isaacvits.movierank.service;

import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;

import com.isaacvits.movierank.model.Movie;

import junit.framework.TestCase;

public class MovieServiceTest extends TestCase {
	
	@Test
	public void testSave() {
		System.out.println(" **** testSave ****");
		IMovieService iMovieService = new MovieService();
		Movie m = new Movie();
		m.setTconst("tt0000003");
		m.setTitle("Pauvre Pierrot");
		m.setAverageRating("6.6");
		m.setNumVotes(939);
		m.setListGenres("Animation,Comedy,Romance");
		m.setYear("1891");
		iMovieService.save(m);
	}
	

	@Test
	public void testGetMoviesNowPlaying() {
		System.out.println(" **** testGetMoviesNowPlaying ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getMoviesNowPlaying(8);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle());
		}
		System.out.println(" **** EXECUTOU ****");
	}
	
	@Test
	public void testSetMoviesNowPlaying() {
		System.out.println(" **** testSetMoviesNowPlaying ****");
		IMovieService movieService = new MovieService();
		movieService.setMoviesNowPlaying();
		System.out.println(" **** EXECUTOU ****");
	}
	
	@Test
	public void testGetListMovieByTitle() {
		System.out.println(" **** testGetListMovieByTitle ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieByTitle("The Oxford");
		for (Movie movie : movies) {
			System.out.println(movie.getTitle());
		}
	}
	
	@Test
	public void testGetListMovieVoteCount() {
		System.out.println(" **** testGetListMovieVoteCount ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieVoteCount(20000, 50000);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " ---- " + movie.getNumVotes());
		}
	}
	
	@Test
	public void testGetListMovieImdbRate() {
		System.out.println(" **** testGetListMovieImdbRateByVote ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRate(6.5, 8.0);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " ---- " + movie.getAverageRating());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVote() {
		System.out.println(" **** testGetListMovieImdbRateByVote ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVote(7.5, 8.0, 20000, 50000);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " ---- " + 
		movie.getAverageRating() + " ----- " + movie.getNumVotes());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVoteByDate() {
		System.out.println(" **** testGetListMovieImdbRateByVoteByDate ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVoteByDate(7.5, 8.0, 20000,
				50000, 1984, 1999);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " Rate: " + 
		movie.getAverageRating() + " Votes: " + movie.getNumVotes() + " Year: " + movie.getYear());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVoteByDateByGenre() {
		System.out.println(" **** testGetListMovieImdbRateByVoteByDateByGenre ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVoteByDateByGenre(7.5, 8.0, 20000,
				50000, 1984, 1990, "Action");
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " Rate: " + 
		movie.getAverageRating() + " Votes: " + movie.getNumVotes() + " Year: " + 
					movie.getYear() + " Genres: " + movie.getListGenres());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVoteByDateByGenreByName() {
		System.out.println(" **** testGetListMovieImdbRateByVoteByDateByGenreByName ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVoteByDateByGenreByName(null, null, null,
				null, 1896, 1900, null, null, 8);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " Rate: " + 
		movie.getAverageRating() + " Votes: " + movie.getNumVotes() + " Year: " + 
					movie.getYear() + " Genres: " + movie.getListGenres());
		}
	}
	

}
