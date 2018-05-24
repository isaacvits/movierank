package com.isaacvits.movierank.model;

//import android.graphics.Bitmap;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


/**
 * Created by vitor on 21/05/17.
 */

@Document(indexName = "movierank", type = "movie")
public class Movie {

	@Id
	private String tconst;
	private String title;
	private String year;
	private String listGenres;
	//private String poster;
	private String averageRating;
	private Integer numVotes;
	private String nowPlaying;

	public Movie() {

	}


	public String getTconst() {
		return tconst;
	}


	public void setTconst(String tconst) {
		this.tconst = tconst;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getYear() {
		return year;
	}


	public void setYear(String year) {
		this.year = year;
	}


	public String getListGenres() {
		return listGenres;
	}


	public void setListGenres(String listGenres) {
		this.listGenres = listGenres;
	}


//	public String getPoster() {
//		return poster;
//	}
//
//
//	public void setPoster(String poster) {
//		this.poster = poster;
//	}


	public String getAverageRating() {
		return averageRating;
	}


	public void setAverageRating(String averageRating) {
		this.averageRating = averageRating;
	}


	public Integer getNumVotes() {
		return numVotes;
	}


	public void setNumVotes(Integer numVotes) {
		this.numVotes = numVotes;
	}


	public String getNowPlaying() {
		return nowPlaying;
	}


	public void setNowPlaying(String nowPlaying) {
		this.nowPlaying = nowPlaying;
	}

}
