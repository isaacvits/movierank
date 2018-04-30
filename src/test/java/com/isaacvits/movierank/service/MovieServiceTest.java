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
		m.setImdbId("tt0000003");
		m.setTitle("Pauvre Pierrot");
		m.setImdbRating("6.6");
		m.setImdbVotes(939);
		m.setListGenres("Animation,Comedy,Romance");
		m.setYear("1891");
		iMovieService.save(m);
	}
	

//	@Test
//	public void testGetMoviesNowPlaying() {
//		IMovieService movieService = new MovieService();
//		List<Movie> movies = movieService.getMoviesNowPlaying();
//		for (Movie movie : movies) {
//			System.out.println(movie.getTitle());
//		}
//		assertNotNull("Filmes", movies);
//	}
	
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
			System.out.println(movie.getTitle() + " ---- " + movie.getImdbVotes());
		}
	}
	
	@Test
	public void testGetListMovieImdbRate() {
		System.out.println(" **** testGetListMovieImdbRateByVote ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRate(6.5, 8.0);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " ---- " + movie.getImdbRating());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVote() {
		System.out.println(" **** testGetListMovieImdbRateByVote ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVote(7.5, 8.0, 20000, 50000);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " ---- " + 
		movie.getImdbRating() + " ----- " + movie.getImdbVotes());
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
		movie.getImdbRating() + " Votes: " + movie.getImdbVotes() + " Year: " + movie.getYear());
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
		movie.getImdbRating() + " Votes: " + movie.getImdbVotes() + " Year: " + 
					movie.getYear() + " Genres: " + movie.getListGenres());
		}
	}
	
	@Test
	public void testGetListMovieImdbRateByVoteByDateByGenreByName() {
		System.out.println(" **** testGetListMovieImdbRateByVoteByDateByGenreByName ****");
		IMovieService movieService = new MovieService();
		Page<Movie> movies = movieService.getListMovieImdbRateByVoteByDateByGenreByName(null, null, null,
				null, 1896, 1900, null, null);
		for (Movie movie : movies) {
			System.out.println(movie.getTitle() + " Rate: " + 
		movie.getImdbRating() + " Votes: " + movie.getImdbVotes() + " Year: " + 
					movie.getYear() + " Genres: " + movie.getListGenres());
		}
	}
	

}