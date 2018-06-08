package com.isaacvits.movierank.utilities;

/**
 * Created by vitor on 01/06/17.
 */

public class ApiUrl {

    //URLs da API TMDB e suas chaves
    public static final String TMDB_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated?";
    public final static String TMDB_PATH_IMAGE = "https://image.tmdb.org/t/p/w500";
    public final static String TMDB_API_IMAGE = "https://api.themoviedb.org/3/find/";
    
    public final static String TMDB_GENRES = "https://api.themoviedb.org/3/genre/movie/list?";
    public final static String TMDB_LANGUAGES = "language=";
    public final static String TMDB_KEY = "api_key=a1582b1854be61efb97231716360a9a1";
    public final static String TMDB_PAGE = "page=";
    public final static String TMDB_ENGLISH = "en-US";
    public final static String TMDB_PORTUGUESE = "pt-BR";
    public final static String TMDB_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?";
    public final static String TMDB_POPULAR = "https://api.themoviedb.org/3/movie/popular?";
    public final static String TMDB_DETAILS = "https://api.themoviedb.org/3/movie/";
    public final static String TMDB_DISCOVER = "https://api.themoviedb.org/3/discover/movie?";
    public final static String TMDB_FIND_IMDB = "https://api.themoviedb.org/3/find/";
    public final static String EXTERNAL_RESOURCES_IMDB = "external_source=imdb_id";

    //URLs da API aberta do IMDB a API OMDB
    public final static String OMDB_BY_ID = "https://www.omdbapi.com/?";
        public final static String OMDB_KEY = "apikey=80e7475c";
    public final static String OMDB_ID = "i=";
    public final static String OMDB_TITLE = "t=";
    public final static String OMDB_YEAR = "y=";
    public final static String OMDB_SEARCH = "s=";

    public final static String AD_PARAMETRO = "&";


    public final static String TmdbURLTopRatedKeyEnglish (){
        return TMDB_TOP_RATED + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + AD_PARAMETRO + TMDB_ENGLISH +AD_PARAMETRO + TMDB_PAGE + "1";
    }

    public final static String TmdbURLGenresKeyEnglish (){
        return TMDB_GENRES + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + AD_PARAMETRO + TMDB_ENGLISH;
    }

    public final static String TmdbURLNowPlayingKeyEnglish(){
        return TMDB_NOW_PLAYING + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + AD_PARAMETRO + TMDB_PORTUGUESE +AD_PARAMETRO + TMDB_PAGE + "1";
    }
    
    public final static String TmdbURLPopularKeyEnglish(){
        return TMDB_POPULAR + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + AD_PARAMETRO + TMDB_ENGLISH +AD_PARAMETRO + TMDB_PAGE + "1";
    }
    

    public final static String TmdbURLIMDB(String idImdb){
        return TMDB_FIND_IMDB + idImdb + "?" + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + AD_PARAMETRO + TMDB_PORTUGUESE +AD_PARAMETRO + EXTERNAL_RESOURCES_IMDB;
    }

    public final static String OmdbKeyID(String ImdbID){
        return OMDB_BY_ID + OMDB_KEY + AD_PARAMETRO + OMDB_ID + ImdbID;
    }

    public final static String OmdbKeyTitleYear(String title, String year){
        return OMDB_BY_ID + OMDB_KEY + AD_PARAMETRO + OMDB_TITLE + title + AD_PARAMETRO + OMDB_YEAR + year;
    }

    public final static String TmdbDetailsIdKeyEnglish(String id){
        return TMDB_DETAILS + id + "?" + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + TMDB_ENGLISH;
    }
    
    public final static String TmdbExternalIdKey(String id){
        return TMDB_DETAILS + id + "/" + "external_ids" + "?" + TMDB_KEY;
    }

    public final static String OMDBKeyTitle(String title){
        return OMDB_BY_ID + OMDB_KEY + AD_PARAMETRO + OMDB_SEARCH + title;
    }
    
    public final static String TmdbImageExternalID(String imdbID) {
    	return TMDB_API_IMAGE + imdbID + "?" + TMDB_KEY + AD_PARAMETRO + TMDB_LANGUAGES + TMDB_ENGLISH + AD_PARAMETRO + "external_source=imdb_id";
    }

}
