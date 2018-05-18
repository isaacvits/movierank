package com.isaacvits.movierank.utilities;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.isaacvits.movierank.model.Genre;
import com.isaacvits.movierank.model.Movie;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by vitor on 23/05/17.
 */

public class OpenMoviesJSON {

	private final static String RESULTS = "results";
	private final static String OWM_MESSAGE_CODE = "cod";
	private final static String VOTE_AVERAGE = "vote_average";
	private final static String VOTE_COUNT = "vote_count";
	private final static String POPULARITY = "popularity";
	private final static String ORIGINAL_TITLE = "original_title";
	private final static String BUDGET = "budget";
	private final static String ID = "id";
	private final static String IMDB_ID = "imdb_id";
	private final static String IMDBID = "imdbID";
	private final static String ORIGINAL_LANGUAGE = "original_language";
	private final static String OVERVIEW = "overview";
	private final static String POSTER_PATH = "poster_path";
	private final static String POSTER = "Poster";
	private final static String REVENUE = "revenue";
	private final static String RUNTIME = "runtime";
	private final static String TITLE = "title";
	private final static String TITLE_OMDB = "Title";
	private final static String NAME = "name";
	private final static String IMDB_VOTES = "imdbVotes";
	private final static String IMDB_RATING = "imdbRating";
	private final static String YEAR = "Year";
	private final static String RELEASE_DATE = "release_date";
	private final static String SEARCH = "Search";
	private final static String GENRE_IDS = "genre_ids";
	private final static String GENRES = "genres";

	public static List<Movie> getNowPlayingMovies(String json) {

		List<Movie> movies = new ArrayList<Movie>();

		JSONObject forecastJson;
		try {
			forecastJson = new JSONObject(json);
			if (forecastJson.has(OWM_MESSAGE_CODE)) {
				int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

				switch (errorCode) {
				case HttpURLConnection.HTTP_OK:
					break;
				case HttpURLConnection.HTTP_NOT_FOUND:
					/* Location invalid */
					return null;
				default:
					/* Server probably down */
					return null;
				}
			}

			JSONArray movieArray = forecastJson.getJSONArray(RESULTS);

			for (int i = 0; i < 10; i++) {
				Movie m = new Movie();
				JSONObject movieForecast = movieArray.getJSONObject(i);

				//m.setId(movieForecast.getInt(ID));
				//m.setPoster(getTMDBImageMovie(movieForecast.getString(POSTER_PATH)));
				m.setTitle(movieForecast.getString(TITLE));
				// m.setVoteAverage(movieForecast.getDouble(VOTE_AVERAGE));
				// m.setReleaseDate(movieForecast.getString(RELEASE_DATE));

				URL TMDB_DETAILS = NetworkUtils.buildUrl(ApiUrl.TmdbDetailsIdKeyEnglish(m.getTconst().toString()));
				String jsonTmdbDetailResponse;
				try {
					jsonTmdbDetailResponse = NetworkUtils.getResponseFromHttpUrl(TMDB_DETAILS);
					JSONObject jsonTmdbDetail = new JSONObject(jsonTmdbDetailResponse);

					URL IMDB = NetworkUtils.buildUrl(ApiUrl.OmdbKeyID(jsonTmdbDetail.getString(IMDB_ID)));
					String jsonIMDBResponse = NetworkUtils.getResponseFromHttpUrl(IMDB);

					JSONObject jsonIMDB = new JSONObject(jsonIMDBResponse);

					if (!jsonIMDB.getBoolean("Response")) {
						continue;
					}

					//m.setImdbRate(jsonIMDB.getString(IMDB_RATING));
					//m.setImdbVotes(jsonIMDB.getString(IMDB_VOTES));
					//m.setYear(jsonIMDB.getString(YEAR));

					JSONArray arrayRate = jsonIMDB.getJSONArray("Ratings");

//					if (arrayRate.length() >= 2) {
//						JSONObject jsonRotten = arrayRate.getJSONObject(1);
//						m.setRottenRate(jsonRotten.getString("Value"));
//					}

					//m.setListGenres(getGenresByID(movieForecast));
					movies.add(m);

					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/* Is there an error? */
		return movies;
	}

	public static List<Movie> getSearchMovies(String json) throws JSONException, IOException {
		List<Movie> movies = new ArrayList<Movie>();

		JSONObject forecastJson = new JSONObject(json);

		/* Is there an error? */
		if (forecastJson.has(OWM_MESSAGE_CODE)) {
			int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

			switch (errorCode) {
			case HttpURLConnection.HTTP_OK:
				break;
			case HttpURLConnection.HTTP_NOT_FOUND:
				/* Location invalid */
				return null;
			default:
				/* Server probably down */
				return null;
			}
		}

		JSONArray movieArray = forecastJson.getJSONArray(SEARCH);

		for (int i = 0; i < movieArray.length(); i++) {
			Movie m = new Movie();
			JSONObject movieForecast = movieArray.getJSONObject(i);

			//m.setPoster(getImageMovie(movieForecast.getString(POSTER)));
			m.setTitle(movieForecast.getString(TITLE_OMDB));
			//m.setYear(movieForecast.getString(YEAR));

			URL IMDB = NetworkUtils.buildUrl(ApiUrl.OmdbKeyID(movieForecast.getString(IMDBID)));
			String jsonIMDBResponse = NetworkUtils.getResponseFromHttpUrl(IMDB);

			JSONObject jsonIMDB = new JSONObject(jsonIMDBResponse);

			if (!jsonIMDB.getBoolean("Response")) {
				continue;
			}

			//m.setImdbRate(jsonIMDB.getString(IMDB_RATING));
			//m.setImdbVotes(jsonIMDB.getString(IMDB_VOTES));

			JSONArray arrayRate = jsonIMDB.getJSONArray("Ratings");

//			if (arrayRate.length() >= 2) {
//				JSONObject jsonRotten = arrayRate.getJSONObject(1);
//				m.setRottenRate(jsonRotten.getString("Value"));
//			}

			movies.add(m);
		}

		return movies;
	}

	private static List<Genre> getGenresByID(JSONObject movie) throws JSONException {

		List<Genre> genresMovies = new ArrayList<Genre>();
		try {
			URL tmdbGenres = NetworkUtils.buildUrl(ApiUrl.TmdbURLGenresKeyEnglish());
			String jsonResponse = NetworkUtils.getResponseFromHttpUrl(tmdbGenres);

			JSONObject result = new JSONObject(jsonResponse);
			JSONArray allGenre = result.getJSONArray(GENRES);

			JSONArray genreArray = movie.getJSONArray(GENRE_IDS);

			for (int i = 0; i < genreArray.length(); i++) {
				Genre g = new Genre();
				g.setId(genreArray.getInt(i));
				for (int j = 0; j < allGenre.length(); j++) {
					JSONObject genre = allGenre.getJSONObject(j);
					Integer id = genre.getInt("id");
					if (g.getId().intValue() == id.intValue()) {
						g.setName(genre.getString("name"));
						break;
					}
				}
				genresMovies.add(g);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return genresMovies;
	}

	//private static Bitmap getTMDBImageMovie(String pathImage) {
		//URL tmdbImage = NetworkUtils.buildUrl(ApiUrl.TMDB_IMAGE.concat(pathImage));
		//Bitmap bmp = null;
		//try {
		//	bmp = BitmapFactory.decodeStream(tmdbImage.openConnection().getInputStream());
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
		//return bmp;
	//}

	//private static Bitmap getImageMovie(String pathImage) {
		//URL tmdbImage = NetworkUtils.buildUrl(pathImage);
		//Bitmap bmp = null;
		//try {
			//bmp = BitmapFactory.decodeStream(tmdbImage.openConnection().getInputStream());
		//} catch (IOException e) {
			//e.printStackTrace();
		//}
		//return bmp;
	//}

	public static JSONArray getJsonGenres() {
		URL tmdbGenres = NetworkUtils.buildUrl(ApiUrl.TmdbURLGenresKeyEnglish());
		String jsonResponse = null;
		try {
			jsonResponse = NetworkUtils.getResponseFromHttpUrl(tmdbGenres);

			JSONObject result = new JSONObject(jsonResponse);
			return result.getJSONArray(GENRES);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
