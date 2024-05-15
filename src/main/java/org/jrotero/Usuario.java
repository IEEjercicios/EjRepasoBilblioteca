package org.jrotero;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Scanner;

public class Usuario {
	public Scanner scanner = new Scanner(System.in);
	private String name;
	private String lastName;
	private int codUser;
	private int numDaysDevolution;
	
	private List<Libro> books = new ArrayList<Libro>();
	
	public Usuario( int codUser, String name, String lastName, int numDaysDevolution ) {
		this.codUser = codUser;
		this.name = name;
		this.lastName = lastName;
		this.numDaysDevolution = numDaysDevolution;
	}
	
	
	 public  void searchLoganBook( String title ) {
		int numBooks = 0;
		if ( books.size() < 1 ) {
			System.out.println( "no hay libros que tengas prestados" );
		}
		try {
			for (Libro libro : books) {
				if ( libro.getTitle().equalsIgnoreCase(title) ) {
					System.out.println( "Introduzca el estado del libro respecto a cuando te lo prestaron del 1(muy daniado) al 3(igual): " );
					int estado = scanner.nextInt();
					if ( estado < 1 || estado > 3 ) {
						System.out.println( "estado no valido" );
						searchLoganBook( title );
					}				
					
					double totalDebt = libro.calculateState(estado) + libro.calculateDevolution( numDaysDevolution );
					
					System.out.println( "---Deuda total---" + (totalDebt) );
					libro.setPrestado(false);
					libro.setLoganDate(null);
					libro.setCodUser((Integer) null);
					books.remove(numBooks);
					System.out.println( libro.toString() );
				}
				numBooks++;
			}
		} catch (ConcurrentModificationException e) { /*error para cuando haya algun error al convertir cadenas de texto*/ }
		
	}
	
	
	public String toString() {
		return codUser + " | Usuario " + name + " " + lastName + " tiene un plazo de devolución de  " + numDaysDevolution + " dias | " ;
	}

	public int getCodUser() {
		return codUser;
	}

	public List<Libro> getBooks() {
		return books;
	}

	public void setBooks(List<Libro> books) {
		this.books = books;
	}	

}
