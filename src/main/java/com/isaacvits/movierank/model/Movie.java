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
	private String poster;
	// private String rottenRate;
	private String averageRating;
	private Integer numVotes;
	// private String year;
	// private String originalTitle;
	// private String originalLanguage;
	// private Number popularity;
	// private Integer voteCount;
	// private Number voteAverage;
	// private Bitmap poster;
	// private String overview;
	// private Integer runtime;
	// private Integer revenue;
	// private Integer budget;
	// private String imdbVotes;
	// private String imdbRate;

	public Movie() {

	}

	public String getListGenres() {
		return listGenres;
	}

	public void setListGenres(String listGenres) {
		this.listGenres = listGenres;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getAverageRating() {
		return averageRating;
	}

	public void setAverageRate(String averageRating) {
		this.averageRating = averageRating;
	}

	public Integer getNumVotes() {
		return numVotes;
	}

	public void setNumVotes (Integer numVotes) {
		this.numVotes = numVotes;
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

	public void setYear(String releaseDate) {
		this.year = releaseDate;
	}

//	public String getRottenRate() {
//		return rottenRate;
//	}
//
//	public void setRottenRate(String rottenRate) {
//		this.rottenRate = rottenRate;
//	}

	// public String getImdbRate() {
	// return imdbRate;
	// }
	//
	// public void setImdbRate(String imdbRate) {
	// this.imdbRate = imdbRate;
	// }
	//
	// public String getYear() {
	// return year;
	// }
	//
	// public void setYear(String year) {
	// this.year = year;
	// }

	// public String getOriginalTitle() {
	// return originalTitle;
	// }
	//
	// public void setOriginalTitle(String originalTitle) {
	// this.originalTitle = originalTitle;
	// }
	//
	// public Number getPopularity() {
	// return popularity;
	// }
	//
	// public void setPopularity(Number popularity) {
	// this.popularity = popularity;
	// }
	//
	// public Integer getVoteCount() {
	// return voteCount;
	// }
	//
	// public void setVoteCount(Integer voteCount) {
	// this.voteCount = voteCount;
	// }
	//
	// public Number getVoteAverage() {
	// return voteAverage;
	// }
	//
	// public void setVoteAverage(Number voteAverage) {
	// this.voteAverage = voteAverage;
	// }

	// public String rateScreen(){
	// return String.format("%.2f", getVoteAverage());
	// }
	//
	// public String popularityScreen(){
	// return String.format("%.2f", getPopularity());
	// }
	//
	// public String voteScreen() {return getVoteCount().toString();}

	// public String getOriginalLanguage() {
	// return originalLanguage;
	// }
	//
	// public void setOriginalLanguage(String originalLanguage) {
	// this.originalLanguage = originalLanguage;
	// }

	// public Bitmap getPoster() {
	// return poster;
	// }

	// public void setPoster(Bitmap poster) {
	// this.poster = poster;
	// }

	// public String getOverview() {
	// return overview;
	// }
	//
	// public void setOverview(String overview) {
	// this.overview = overview;
	// }

	// public Integer getRuntime() {
	// return runtime;
	// }
	//
	// public void setRuntime(Integer runtime) {
	// this.runtime = runtime;
	// }
	//
	// public Integer getRevenue() {
	// return revenue;
	// }
	//
	// public void setRevenue(Integer revenue) {
	// this.revenue = revenue;
	// }
	//
	// public Integer getBudget() {
	// return budget;
	// }
	//
	// public void setBudget(Integer budget) {
	// this.budget = budget;
	// }

	// public String getImdbVotes() {
	// return imdbVotes;
	// }
	//
	// public void setImdbVotes(String imdbVotes) {
	// this.imdbVotes = imdbVotes;
	// }

}
