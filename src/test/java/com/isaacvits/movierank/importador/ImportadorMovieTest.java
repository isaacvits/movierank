package com.isaacvits.movierank.importador;

import org.junit.Before;
import org.junit.Test;

public class ImportadorMovieTest {

	private ImportadorMovie importadorMovie;
	private final boolean importar = false;

	@Before
	@Test
	public void testImportadorMovie() {
		importadorMovie = new ImportadorMovie();
	}

	@Test
	public void testImportar() {
		if (importar) {
			importadorMovie.importar();
		}
	}

}
