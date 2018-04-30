package com.isaacvits.movierank.importador;

import java.io.FileReader;
import java.io.PrintWriter;

import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.ParseBool;

import com.isaacvits.movierank.model.Movie;
import com.isaacvits.movierank.model.MovieSupport;
import com.isaacvits.movierank.service.IMovieService;
import com.isaacvits.movierank.service.MovieService;

public class ImportadorMovie {

	private static final String TSV_FILENAME_DATA_RATING = "src/main/resources/data.tsv";
	private static final String TSV_FILENAME_TITLE = "src/main/resources/data2.tsv";

	private IMovieService iMovieService;

	public ImportadorMovie() {
		iMovieService = new MovieService();
	}

	public void Importar() {
		try {
			readWithTsvBeanReader();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static CellProcessor[] getProcessorsRatings() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), new NotNull(),
				new Optional(new ParseInt()) };
		return processors;
	}

	private static CellProcessor[] getProcessorsTitle() {
		final CellProcessor[] processors = new CellProcessor[] { new UniqueHashCode(), new NotNull(), new NotNull(),
				new NotNull(), new ParseBool(), new NotNull(), new NotNull(), new NotNull(), new NotNull() };
		return processors;
	}

	private void readWithTsvBeanReader() throws Exception {
		ICsvBeanReader beanReader = null;
		ICsvBeanReader beanReaderTitle = null;
		PrintWriter writer = new PrintWriter("filmes-problema.txt", "UTF-8");
		try {
			beanReader = new CsvBeanReader(new FileReader(TSV_FILENAME_DATA_RATING), CsvPreference.TAB_PREFERENCE);
			beanReaderTitle = new CsvBeanReader(new FileReader(TSV_FILENAME_TITLE), CsvPreference.TAB_PREFERENCE);
			final String[] header = beanReader.getHeader(true);
			final String[] headerTitle = beanReaderTitle.getHeader(true);
			final CellProcessor[] processors = getProcessorsRatings();
			final CellProcessor[] processorsTitle = getProcessorsTitle();

			Movie movie;
			Movie movieExist;
			MovieSupport movieTitle;
			long tempoInicio = System.currentTimeMillis();

			while ((movie = beanReader.read(Movie.class, header, processors)) != null) {

				if (iMovieService.existMovie(movie.getImdbId())) {
					movieExist = iMovieService.findMovieById(movie.getImdbId());
					movieExist.setImdbRating(movie.getImdbRating());
					movieExist.setImdbVotes(movie.getImdbVotes());
					iMovieService.save(movieExist);
				} else {
					boolean encontrou = false;
					try {
						while (!encontrou) {
							movieTitle = beanReaderTitle.read(MovieSupport.class, headerTitle, processorsTitle);
							if (movieTitle.getTconst().equals(movie.getImdbId())) {
								movie.setTitle(movieTitle.getOriginalTitle());
								movie.setListGenres(movieTitle.getGenres());
								if (!movieTitle.getStartYear().equals("\\N")) {
									movie.setYear(movieTitle.getStartYear());
								}
								encontrou = true;
							}
						}
						iMovieService.save(movie);
					} catch (Exception e) {
						System.out.println("*** Id do filme que deu problema: **** " + movie.getImdbId() + " ---- "
								+ e.getMessage());
						writer.println("*** Id do filme que deu problema: **** " + movie.getImdbId() + " ---- "
								+ e.getMessage());
					}
				}
				System.out.println(beanReader.getLineNumber());
			}
			long tempoFinal = System.currentTimeMillis();
			float tempoTotal = (tempoFinal - tempoInicio) / 1000;
			System.out.println("***************** Tempo de Importação:" + tempoTotal);
		} finally {
			if (beanReader != null) {
				beanReader.close();
			}
			if (beanReaderTitle != null) {
				beanReaderTitle.close();
			}
			writer.close();
		}
	}

}
