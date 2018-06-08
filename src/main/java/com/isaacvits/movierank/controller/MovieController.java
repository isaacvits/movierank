package com.isaacvits.movierank.controller;

import java.io.InputStream;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.isaacvits.movierank.importador.ImportadorMovie;
import com.isaacvits.movierank.model.Movie;
import com.isaacvits.movierank.service.IMovieService;
import com.isaacvits.movierank.service.MovieService;

@RestController
@EnableScheduling
public class MovieController {

	IMovieService iMovieService = new MovieService();

	static int qtdFilmes = 8;

	@RequestMapping(value = "/moviesNowPlaying", method = RequestMethod.GET, headers = "Accept=application/json")
	public List moviesNowPlaying() {
		qtdFilmes = 8;
		return iMovieService.getMoviesNowPlaying(qtdFilmes).getContent();

	}

	@RequestMapping(value = "/getListMovieDiscovery", method = RequestMethod.GET, headers = "Accept=application/json")
	public List getListMovieByTitle(@RequestParam("title") String title, @RequestParam("listGenres") String listGenres,
			@RequestParam("yearStart") Integer yearStart, @RequestParam("yearEnd") Integer yearEnd,
			@RequestParam("rateStart") Double rateStart, @RequestParam("rateEnd") Double rateEnd,
			@RequestParam("voteStart") Integer voteStart, @RequestParam("voteEnd") Integer voteEnd) {
		qtdFilmes = 8;
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(rateStart, rateEnd, voteStart, voteEnd,
				yearStart, yearEnd, listGenres, title, qtdFilmes).getContent();
	}

	@RequestMapping(value = "/nextMovies", method = RequestMethod.GET, headers = "Accept=application/json")
	public List moviesNext(@RequestParam("title") String title, @RequestParam("listGenres") String listGenres,
			@RequestParam("yearStart") Integer yearStart, @RequestParam("yearEnd") Integer yearEnd,
			@RequestParam("rateStart") Double rateStart, @RequestParam("rateEnd") Double rateEnd,
			@RequestParam("voteStart") Integer voteStart, @RequestParam("voteEnd") Integer voteEnd) {
		if (qtdFilmes < 32) {
			qtdFilmes += 8;
		}
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(rateStart, rateEnd, voteStart, voteEnd,
				yearStart, yearEnd, listGenres, title, qtdFilmes).getContent();
	}

	@ResponseBody
	@RequestMapping(value = "/moviePoster", method = RequestMethod.GET)
	public ResponseEntity<byte[]> moviePoster(@RequestParam("tconst") String tconst) {
		HttpHeaders headers = new HttpHeaders();
		URL url;
		try {
			url = new URL("https://img.omdbapi.com/?i=" + tconst + "&h=600&apikey=80e7475c");
			InputStream in = url.openStream();
			byte[] media = IOUtils.toByteArray(in);
			headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Scheduled(cron = "0 4 15 * * *")
	public void importar() {
		ImportadorMovie importadorMovie = new ImportadorMovie();
		importadorMovie.importar();
	}

}
