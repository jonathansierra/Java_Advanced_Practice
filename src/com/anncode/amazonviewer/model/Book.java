package com.anncode.amazonviewer.model;

import java.util.ArrayList;
import java.util.Date;

import com.anncode.util.AmazonUtil;

public class Book extends Publication implements IVisualizable {
	private int id;
	private String isbn;
	private boolean read;
	private int timeRead;
	private ArrayList<Page> pages;
	
	public Book(String title, Date edititionDate, String editorial, String[] authors, ArrayList<Page> pages) {
		super(title, edititionDate, editorial);
		setAuthors(authors);
		this.pages = pages;
	}
	
	public int getId() {
		return id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String isRead() {
		String leido = "";
		if(read== true) {
			leido = "Sí";
		}else {
			leido = "No";
		}
		
		return leido;
	}
	
	public void setRead(boolean readed) {
		this.read = readed;
	}
	
	public boolean getIsRead() {
		return read;
	}
	
	public int getTimeRead() {
		return timeRead;
	}
	
	public void setTimeRead(int timeRead) {
		this.timeRead = timeRead;
	}
	
	@Override
	public String toString() {
		String detailBook = "\n :: BOOK ::" + 
							"\n Title: " + getTitle() +
							"\n Editorial: " + getEditorial() + 
							"\n Edition Date: " + getEdititionDate() +
							"\n Authors: ";
		
		for (int i = 0; i < getAuthors().length; i++) {
			detailBook += "\t" + getAuthors()[i] + " ";
		}
		
		return  detailBook;
	}
	
	@Override
	public Date startToSee(Date dateI) {
		return dateI;
	}
	
	@Override
	public void stopToSee(Date dateI, Date dateF) {
		if (dateF.getTime() > dateI.getTime()) {
			setTimeRead((int)(dateF.getTime() - dateI.getTime()));
		}else {
			setTimeRead(0);
		}
	}
	
	public void view() {
		setRead(true);
		Date dateI = startToSee(new Date());
		
		int i = 0;
		do {
			System.out.println("...............");
			System.out.println("Page " + pages.get(i).getNumber());
			System.out.println(pages.get(i).getContent());
			System.out.println("...............");
			
			if(i != 0) {
				System.out.println("1 - Regresar Página");
			}
			
			System.out.println("2 - Siguiente Página");
			System.out.println("0 - Cerrar Libro \n");
			
			int respose = AmazonUtil.validateUserResponseMenu(0, 2);
			if(respose == 2) {
				i++;
			} else if(respose == 1) {
				i--;
			} else if(respose == 0) {
				break;
			}
		} while(i < pages.size());
		
		
		//Termine de verla
		stopToSee(dateI, new Date());
		System.out.println();
		System.out.println("Leíste: " + toString());
		System.out.println("Por: " + getTimeRead() + " milisegundos");
	}
	
	public static ArrayList<Book> makeBookList() {
		ArrayList<Book> books = new ArrayList<Book>();
		String[] authors = new String[3];
		for (int i = 0; i < 3; i++) {
			authors[i] = "author " + i;
		}
		
		ArrayList<Page> pages = new ArrayList<>();
		
		int pagina = 0;
		for (int i = 0; i < 3; i++) {
			pagina = i + 1;
			pages.add(new Book.Page(pagina, "Contenido de la página " + pagina));
		}
		
		for (int i = 1; i <= 5; i++) {
			books.add(new Book("Book " + i, new Date(), "editorial " + i, authors, pages));
		}
		
		return books;
	}
	
	public static class Page {
		private int number;
		private String content;
		
		public Page(int number, String content) {
			this.number = number;
			this.content = content;
		}

		public int getNumber() {
			return number;
		}
		
		public String getContent() {
			return content;
		}
	}
}