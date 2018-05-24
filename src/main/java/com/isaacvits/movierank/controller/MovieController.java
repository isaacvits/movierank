package com.isaacvits.movierank.controller;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;

import com.isaacvits.movierank.model.Genre;
import com.isaacvits.movierank.model.Movie;
import com.isaacvits.movierank.service.IMovieService;
import com.isaacvits.movierank.service.MovieService;

@RestController
public class MovieController {

	IMovieService iMovieService = new MovieService();

	@RequestMapping(value = "/moviesNowPlaying", method = RequestMethod.GET, headers = "Accept=application/json")
	public List moviesNowPlaying() {
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(5.0, 8.0, 1000, 50000, null, null, "", "")
				.getContent();
	}

	@RequestMapping(value = "/getListMovieDiscovery", method = RequestMethod.GET, headers = "Accept=application/json")
	public List getListMovieByTitle(@RequestParam("title") String title, @RequestParam("listGenres") String listGenres,
			@RequestParam("yearStart") Integer yearStart, @RequestParam("yearEnd") Integer yearEnd,
			@RequestParam("rateStart") Double rateStart, @RequestParam("rateEnd") Double rateEnd,
			@RequestParam("voteStart") Integer voteStart, @RequestParam("voteEnd") Integer voteEnd) {
		return iMovieService.getListMovieImdbRateByVoteByDateByGenreByName(rateStart, rateEnd, voteStart, voteEnd,
				yearStart, yearEnd, listGenres, title).getContent();
	}

	@ResponseBody
	@RequestMapping(value = "/moviePoster", method = RequestMethod.GET)
	public ResponseEntity<byte[]> moviePoster(@RequestParam("tconst") String tconst) {
		HttpHeaders headers = new HttpHeaders();
		URL url;
		try {
			url = new URL("http://img.omdbapi.com/?i=" + tconst + "&h=600&apikey=80e7475c");
			InputStream in = url.openStream();
			byte[] media = IOUtils.toByteArray(in);
			headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(media, headers, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return new ServletContextResource(servletContext,
		// "http://img.omdbapi.com/?i=" + tconst + "&h=600&apikey=80e7475c");
		// response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		// URL url;
		// try {
		// url = new URL();
		// InputStream in = url.openStream();
		//
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return null;
	}

}
