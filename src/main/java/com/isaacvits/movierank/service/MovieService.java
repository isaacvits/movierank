package com.isaacvits.movierank.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.isaacvits.movierank.model.Movie;
import com.isaacvits.movierank.repository.IMovieRepository;
import com.isaacvits.movierank.repository.MovieRepository;
import com.isaacvits.movierank.utilities.ApiUrl;
import com.isaacvits.movierank.utilities.NetworkUtils;
import com.isaacvits.movierank.utilities.OpenMoviesJSON;

@Service
public class MovieService implements IMovieService {

	private IMovieRepository iMovieRepository;

	public MovieService() {
		MovieRepository movieRepository = new MovieRepository();
		setIMovieRepository(movieRepository);
	}

	public void setIMovieRepository(IMovieRepository iMovieRepository) {
		this.iMovieRepository = iMovieRepository;
	}

	public List<Movie> getMoviesNowPlaying() {
		URL tmdb = NetworkUtils.buildUrl(ApiUrl.TmdbURLNowPlayingKeyEnglish());
		String jsonResponse;
		try {
			jsonResponse = NetworkUtils.getResponseFromHttpUrl(tmdb);
			List<Movie> listMovieNowPlaying = OpenMoviesJSON.getNowPlayingMovies(jsonResponse);
			return listMovieNowPlaying;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Movie save(Movie movie) {
		return iMovieRepository.save(movie);
	}

	public Page<Movie> getListMovieByTitle(String title) {
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", title).minimumShouldMatch("100%");

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(matchQueryBuilder).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieVoteCount(Integer startCount, Integer endCount) {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbVotes").gte(startCount).lte(endCount)
				.boost(10);

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(rangeQueryBuilder).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieImdbRate(Double startRate, Double endRate) {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbRating").gte(startRate).lte(endRate);

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(rangeQueryBuilder).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieImdbRateByVote(Double startRate, Double endRate, Integer startCount,
			Integer endCount) {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbRating").gte(startRate).lte(endRate);
		RangeQueryBuilder rangeQueryBuilder2 = new RangeQueryBuilder("imdbVotes").gte(startCount).lte(endCount);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(rangeQueryBuilder)
				.withFilter(rangeQueryBuilder2).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieImdbRateByVoteByDate(Double startRate, Double endRate, Integer startCount,
			Integer endCount, Integer startYear, Integer endYear) {

		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbRating").gte(startRate).lte(endRate);
		RangeQueryBuilder rangeQueryBuilder2 = new RangeQueryBuilder("imdbVotes").gte(startCount).lte(endCount);
		RangeQueryBuilder rangeQueryBuilder3 = new RangeQueryBuilder("year").gte(startYear).lte(endYear);

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder().must(rangeQueryBuilder).must(rangeQueryBuilder2)
				.must(rangeQueryBuilder3);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(boolQueryBuilder).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieImdbRateByVoteByDateByGenre(Double startRate, Double endRate, Integer startCount,
			Integer endCount, Integer startYear, Integer endYear, String genres) {
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbRating").gte(startRate).lte(endRate);
		RangeQueryBuilder rangeQueryBuilder2 = new RangeQueryBuilder("imdbVotes").gte(startCount).lte(endCount);
		RangeQueryBuilder rangeQueryBuilder3 = new RangeQueryBuilder("year").gte(startYear).lte(endYear);
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("listGenres", genres);

		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder().must(rangeQueryBuilder).must(rangeQueryBuilder2)
				.must(rangeQueryBuilder3).must(matchQueryBuilder);
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(boolQueryBuilder).build();

		return iMovieRepository.search(searchQuery);
	}

	public Page<Movie> getListMovieImdbRateByVoteByDateByGenreByName(Double startRate, Double endRate,
			Integer startCount, Integer endCount, Integer startYear, Integer endYear, String genres, String title) {
		List<QueryBuilder> queryBuilders = new ArrayList<QueryBuilder>();
		
		if(title != null && !title.isEmpty()) {
			MatchQueryBuilder matchQueryBuilder2 = new MatchQueryBuilder("title", title).minimumShouldMatch("100%");
			queryBuilders.add(matchQueryBuilder2);
		}
		
		if(genres != null && !genres.isEmpty()) {
			MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("listGenres", genres);
			queryBuilders.add(matchQueryBuilder);
		}
		
		RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("imdbRating");
		if(startRate != null && !startRate.isNaN()) {
			rangeQueryBuilder.gte(startRate);
		}
		
		if(endRate != null && !endRate.isNaN()) {
			rangeQueryBuilder.lte(endRate);
		}
		
		if((startRate != null && !startRate.isNaN()) || (endRate != null && !endRate.isNaN())) {
			rangeQueryBuilder.boost(5.0f);
			queryBuilders.add(rangeQueryBuilder);
		}
		
		
		RangeQueryBuilder rangeQueryBuilder2 = new RangeQueryBuilder("imdbVotes");
		if(startCount != null) {
			rangeQueryBuilder2.gte(startCount);
		}
		
		if(endCount != null) {
			rangeQueryBuilder2.lte(endCount);
		}
		
		if(startCount != null  || endCount != null) {
			rangeQueryBuilder2.boost(10.0f);
			queryBuilders.add(rangeQueryBuilder2);
		}
		
		RangeQueryBuilder rangeQueryBuilder3 = new RangeQueryBuilder("year");
		if(startYear != null) {
			rangeQueryBuilder3.gte(startYear);
		}
		
		if(endYear != null) {
			rangeQueryBuilder3.lte(endYear);
		}
		
		if(startYear != null  || endYear != null) {
			queryBuilders.add(rangeQueryBuilder3);
		}
				
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		for (QueryBuilder queryBuilder : queryBuilders) {
			boolQueryBuilder.must(queryBuilder);
		}
		SortBuilder sortBuilder = new FieldSortBuilder("imdbVotes").order(SortOrder.DESC);
		SortBuilder sortBuilder2 = new FieldSortBuilder("imdbRating").order(SortOrder.DESC);
		
		Pageable page = new PageRequest(0, 8);
		
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFilter(boolQueryBuilder).
				withSort(sortBuilder).withSort(sortBuilder2).withPageable(page).build();
		
		Page<Movie> movies = iMovieRepository.search(searchQuery);
		
	
		for (Movie movie : movies) {
			URL omdb = NetworkUtils.buildUrl(ApiUrl.OmdbKeyID(movie.getImdbId()));
			try {
				String omdbResponse = NetworkUtils.getResponseFromHttpUrl(omdb);
				JSONObject jsonOmdb = new JSONObject(omdbResponse);
				movie.setPoster(jsonOmdb.getString("Poster"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return movies;
	}

	public Movie findMovieById(String id) {
		return iMovieRepository.findOne(id);
	}
	
	public boolean existMovie(String id) {
		return iMovieRepository.exists(id);
	}

}
