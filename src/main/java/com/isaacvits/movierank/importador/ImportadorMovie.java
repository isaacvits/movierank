package com.isaacvits.movierank.importador;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

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

	private static final String TSV_FILENAME_DATA_RATING = "/home/vitor/rate.tsv";
	private static final String TSV_FILENAME_TITLE = "/home/vitor/title.tsv";
	private static final String URL_DATA_RATE = "https://datasets.imdbws.com/title.ratings.tsv.gz";
	private static final String URL_DATA_TITLE = "https://datasets.imdbws.com/title.basics.tsv.gz";
	private static final String PATH_LOCAL ="/home/vitor/";
	private static final String OUT_FILE_RATE = "rate.tsv";
	private static final String OUT_FILE_TITLE = "title.tsv";
 	
	private IMovieService iMovieService;

	public ImportadorMovie() {
		iMovieService = new MovieService();
	}

	public void importar() {
		try {
			
			File rate = baixarArquivo(URL_DATA_RATE);
			
			File title = baixarArquivo(URL_DATA_TITLE);
			
			descomprimirArquivo(rate, OUT_FILE_RATE);
			descomprimirArquivo(title, OUT_FILE_TITLE);
			
			readWithTsvBeanReader();
			iMovieService.setMoviesNowPlaying();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void descomprimirArquivo(File rate, String outFile) throws IOException, FileNotFoundException {
		GZIPInputStream in = new GZIPInputStream (new BufferedInputStream (new FileInputStream (rate)));
		
		String outFilename = outFile;
		OutputStream out = new FileOutputStream(PATH_LOCAL+outFilename);
		
		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
		    out.write(buf, 0, len);
		}
		// Close the file and stream
		in.close();
		out.close();
	}

	private File baixarArquivo(String URL) throws MalformedURLException, IOException, FileNotFoundException {
		URL url = new URL(URL);
		
		String nomeArquivoLocalRate = url.getPath();
		
		InputStream is = url.openStream();
		
		FileOutputStream fos = new FileOutputStream(PATH_LOCAL+nomeArquivoLocalRate);
		
		int umByte = 0;
		while ((umByte = is.read()) != -1){
			fos.write(umByte);
		}
		
		is.close();
		fos.close();
		return new File(PATH_LOCAL+nomeArquivoLocalRate);
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

				if (iMovieService.existMovie(movie.getTconst())) {
					movieExist = iMovieService.findMovieById(movie.getTconst());
					movieExist.setAverageRating(movie.getAverageRating());
					movieExist.setNumVotes(movie.getNumVotes());
					iMovieService.save(movieExist);
				} else {
					boolean encontrou = false;
					try {
						while (!encontrou) {
							movieTitle = beanReaderTitle.read(MovieSupport.class, headerTitle, processorsTitle);
							if (movieTitle.getTconst().equals(movie.getTconst())) {
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
						System.out.println("*** Id do filme que deu problema: **** " + movie.getTconst() + " ---- "
								+ e.getMessage());
						writer.println("*** Id do filme que deu problema: **** " + movie.getTconst() + " ---- "
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
