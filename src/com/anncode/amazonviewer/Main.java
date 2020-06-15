package com.anncode.amazonviewer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.anncode.amazonviewer.model.Book;
import com.anncode.amazonviewer.model.Chapter;
import com.anncode.amazonviewer.model.Magazine;
import com.anncode.amazonviewer.model.Movie;
import com.anncode.amazonviewer.model.Serie;
import com.anncode.makereport.Report;
import com.anncode.util.AmazonUtil;

/**
 * <h1>AmazonViewer</h1>
 * AmazonViewer es un programa que permite visualizar Movies, Series con sus respectivos Chapters,
 * Books y Magazines. Te permite generar reportes generales y con fecha del d�a.
 * <br>
 * Existen algunas reglas como que todos los elementos deben ser visualizados o le�dos a excepci�n
 * de las Magazines, est�s solo pueden ser vistas a modo de exposici�n sin ser le�das.
 * 
 * @author Geovani
 * @version 1.1
 * @since 2018
 * */

public class Main {

	public static void main(String[] args) {
		showMenu();
	}
	
	public static void showMenu() {
		int exit = 0;
		do {
			System.out.println("BIENVENIDOS AMAZON VIEWER");
			System.out.println("");
			System.out.println("Selecciona el número de la opción deseada");
			System.out.println("1. Movies");
			System.out.println("2. Series");
			System.out.println("3. Books");
			System.out.println("4. Magazines");
			System.out.println("5. Report");
			System.out.println("6. Report Today");
			System.out.println("0. Exit");
			
			int response = AmazonUtil.validateUserResponseMenu(0, 6);
			switch (response) {
				case 0:
					exit = 0; break;
				case 1:
					showMovies(); break;
				case 2:
					showSeries(); break;
				case 3:
					showBooks(); break;
				case 4:
					showMagazines(); break;
				case 5:
					makeReport();
					exit = 1; break;
				case 6:
					makeReport(new Date());
					exit = 1; break;
				default:
					System.out.println();
					System.out.println("....��Selecciona una opci�n!!....");
					System.out.println();
					exit = 1; break;
			}
		}while(exit != 0);
	}
	
	static ArrayList<Movie> movies = new ArrayList<>();	
	static ArrayList<Book> books= Book.makeBookList();
	static ArrayList<Serie> series = Serie.makeSeriesList();
	
	public static void showMovies() {
		movies = Movie.makeMoviesList();
		int exit = 1;
		do {
			System.out.println();
			System.out.println(":: MOVIES ::");
			System.out.println();
			
			AtomicInteger atomicInteger = new AtomicInteger(1);
			movies.forEach(m -> System.out.println(atomicInteger.getAndIncrement() + ", " + m.getTitle() + " Visto: "+ m.isViewed()));
			
			/*
			for (int i = 0; i < movies.size(); i++) { //1. Movie 1
				System.out.println(i+1 + ". " + movies.get(i).getTitle() + " Visto: " + movies.get(i).isViewed());
			}*/
			
			System.out.println("0. Regresar al Menu");
			System.out.println();
			
			int response = AmazonUtil.validateUserResponseMenu(0, movies.size());
			if(response == 0) {
				exit = 0;
				showMenu();
				break;
			}
			
			if (response > 0) {
				Movie movieSelected = movies.get(response - 1);
				movieSelected.view();
			}
		}while(exit !=0);
	}
	
	public static void showSeries() {
		int exit = 1;
		do {
			System.out.println();
			System.out.println(":: SERIES ::");
			System.out.println();
			
			for (int i = 0; i < series.size(); i++) {
				System.out.println(i+1 + ". " + series.get(i).getTitle() + " Visto: " + series.get(i).isViewed());
			}
			
			System.out.println("0. Regresar al Menu");
			System.out.println();
			
			int response = AmazonUtil.validateUserResponseMenu(0, series.size());
			if(response == 0) {
				exit = 0;
				showMenu();
			}
			
			if(response > 0) {
				showChapters(series.get(response-1).getChapters());
			}
		}while(exit !=0);
	}
	
	public static void showChapters(ArrayList<Chapter> chaptersOfSerieSelected) {
		int exit = 1;
		do {
			System.out.println();
			System.out.println(":: CHAPTERS ::");
			System.out.println();

			for (int i = 0; i < chaptersOfSerieSelected.size(); i++) {
				System.out.println(i+1 + ". " + chaptersOfSerieSelected.get(i).getTitle() + " Visto: " + chaptersOfSerieSelected.get(i).isViewed());
			}
			
			System.out.println("0. Regresar al Menu");
			System.out.println();
			
			int response = AmazonUtil.validateUserResponseMenu(0, chaptersOfSerieSelected.size());
			
			if(response == 0) {
				exit = 0;
			}
			
			if(response > 0) {
				Chapter chapterSelected = chaptersOfSerieSelected.get(response-1);
				chapterSelected.view();
			}
		}while(exit !=0);
	}
	
	public static void showBooks() {
		int exit = 1;
		do {
			System.out.println();
			System.out.println(":: BOOKS ::");
			System.out.println();
			
			for (int i = 0; i < books.size(); i++) { //1. Book 1
				System.out.println(i+1 + ". " + books.get(i).getTitle() + " Le�do: " + books.get(i).isRead());
			}
			
			System.out.println("0. Regresar al Menu");
			System.out.println();
			
			int response = AmazonUtil.validateUserResponseMenu(0, books.size());
			
			if(response == 0) {
				exit = 0;
				showMenu();
			}
			
			if(response > 0) {
				Book bookSelected = books.get(response-1);
				bookSelected.view();
			}
		}while(exit !=0);
	}
	
	public static void showMagazines() {
		ArrayList<Magazine> magazines = Magazine.makeMagazineList();
		int exit = 0;
		do {
			System.out.println();
			System.out.println(":: MAGAZINES ::");
			System.out.println();
			
			for (int i = 0; i < magazines.size(); i++) { //1. Book 1
				System.out.println(i+1 + ". " + magazines.get(i).getTitle());
			}
			
			System.out.println("0. Regresar al Menu");
			System.out.println();
			
			int response = AmazonUtil.validateUserResponseMenu(0, 0);
			
			if(response == 0) {
				exit = 0;
				showMenu();
			}
		}while(exit !=0);
	}
	
	public static void makeReport() {
		Report report = new Report();
		report.setNameFile("reporte");
		report.setExtension("txt");
		report.setTitle(":: VISTOS ::");
		StringBuilder contentReport = new StringBuilder();
		
		movies.stream()
			.filter(m -> m.getIsViewed())
			.forEach(m -> contentReport.append(m.toString() + "\n"));
		
		/*
		for (Movie movie : movies) {
			if (movie.getIsViewed()) {
				contentReport += movie.toString() + "\n";
			}
		}*/
		
		Consumer<Serie> seriesEach = s -> {
			ArrayList<Chapter> chapters = s.getChapters();
			chapters.stream().filter(c -> c.getIsViewed()).forEach(c -> contentReport.append(c.toString() + "\n"));
		};
		series.stream().forEach(seriesEach);
		
		/*
		for (Serie serie : series) {
			ArrayList<Chapter> chapters = serie.getChapters();
			for (Chapter chapter : chapters) {
				if (chapter.getIsViewed()) {
					contentReport += chapter.toString() + "\n";
				}
			}	
		} */
		
		books.stream()
		.filter(b -> b.getIsRead())
		.forEach(b -> contentReport.append(b.toString() + "\n"));
		
		/*
		for (Book book : books) {
			if (book.getIsRead()) {
				contentReport += book.toString() + "\n";
			}
		}*/

		report.setContent(contentReport.toString());
		report.makeReport();
		System.out.println("Reporte Generado");
		System.out.println();
	}
	
	public static void makeReport(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-h-m-s-S");
		String dateString = df.format(date);
		Report report = new Report();
		
		report.setNameFile("reporte" + dateString);
		report.setExtension("txt");
		report.setTitle(":: VISTOS ::");
		
		SimpleDateFormat dfNameDays = new SimpleDateFormat("E, W MMM Y");
		dateString = dfNameDays.format(date);
		StringBuilder contentReport = new StringBuilder("Date: " + dateString + "\n\n\n");
		
		
		Predicate<Movie> moviesPredicate = m -> m.getIsViewed();
		movies.stream().filter(moviesPredicate).forEach(m -> contentReport.append(m.toString() + "\n"));
		
		/*
		for (Movie movie : movies) {
			if (movie.getIsViewed()) {
				contentReport += movie.toString() + "\n";
			}
		}*/
		
		Consumer<Serie> seriesViewed = s -> {
			ArrayList<Chapter> chapters = s.getChapters();
			chapters.stream().filter(c -> c.getIsViewed()).forEach(c -> contentReport.append(c.toString() + "\n"));
		};
		
		series.stream().forEach(seriesViewed);
		
		/*
		for (Serie serie : series) {
			ArrayList<Chapter> chapters = serie.getChapters();
			for (Chapter chapter : chapters) {
				if (chapter.getIsViewed()) {
					contentReport += chapter.toString() + "\n";
				}
			}
		}*/
		
		Predicate<Book> bookPredicate = b -> b.getIsRead();
		books.stream().filter(bookPredicate).forEach(b -> contentReport.append(b.toString() + "\n"));
		
		/*
		for (Book book : books) {
			if (book.getIsRead()) {
				contentReport += book.toString() + "\n";
			}
		}*/
		
		report.setContent(contentReport.toString());
		report.makeReport();
		
		System.out.println("Reporte Generado");
		System.out.println();
	}
}