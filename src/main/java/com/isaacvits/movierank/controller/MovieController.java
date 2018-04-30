package com.isaacvits.movierank.controller;

import java.util.List;

import org.springframework.data.domain.Page;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.isaacvits.movierank.model.Genre;
import com.isaacvits.movierank.model.Movie;
import com.isaacvits.movierank.service.IMovieService;
import com.isaacvits.movierank.service.MovieService;

@RestController
public class MovieController {

	IMovieService iMovieService = new MovieService();

	@RequestMapping(value = "/moviesNowPlaying", method = RequestMethod.GET, headers = "Accept=application/json")
	public List moviesNowPlaying() {
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(5.0, 8.0, 
				1000, 50000, null, null, "", "").getContent();
	}
	
	@RequestMapping(value = "/getListMovieDiscovery", method = RequestMethod.GET, headers = "Accept=application/json")
	public List getListMovieByTitle(@RequestParam("title") String title, @RequestParam("listGenres") String listGenres
			, @RequestParam("yearStart") Integer yearStart, @RequestParam("yearEnd") Integer yearEnd, 
			@RequestParam("rateStart") Double rateStart, @RequestParam("rateEnd") Double rateEnd, 
			@RequestParam("voteStart") Integer voteStart, @RequestParam("voteEnd") Integer voteEnd)
	{
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(rateStart, rateEnd, voteStart, voteEnd, yearStart, yearEnd, listGenres, title).getContent();
	}
}
