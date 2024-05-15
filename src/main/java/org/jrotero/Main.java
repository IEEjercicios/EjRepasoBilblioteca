package org.jrotero;

 
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static Scanner scanner;
	public static Scanner scanner1;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	
	public static Libro book;
	public static Usuario user;
	
	public static List<Libro> books = new ArrayList<Libro>(); 
	public static List<Usuario> users = new ArrayList<Usuario>();

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		scanner1 = new Scanner(System.in);
		int option = -1;
		
		
		/*Desarrollar un sistema para gestionar las operaciones en una biblioteca. 

		Los usuarios del sistema deben poder:
		
		Consultar libros disponibles:
		
		-Buscar libros por diferentes criterios como totulo, autor, categoroa o aoo de publicacion.
		
		Realizar prostamos de libros:
		
		-Los usuarios pueden tomar prestados uno o mas libros, especificando la duracion del prostamo.
		-El sistema debe registrar quion ha tomado el libro y cuondo debe ser devuelto.
		
		Devolver libros:
		
		-Registrar la devoluciode libros y verificar su estado al retorno.
		-Aplicar penalizaciones si el libro se devuelve tarde o daniado
		
		Registrar nuevos libros y usuarios:
		
		-Aniadir nuevos libros al sistema con toda la informacion relevante.
		-Registrar nuevos usuarios en el sistema.
		*/
		
		/* no se como agregar el libro con fecha sin que me de error
		book = new Libro("LA CELESTINA", "Fernando de Rojas", "Tragicomedia", "14/11/1499");
		books.add(book);
		book = new Libro("CAMPOS DE FRESA", "Jordi Sierra", "Ficcion", 23/10/1997);
		books.add(book);
		book = new Libro("MERMELADA DE NARANJA AMARGA", "Ana Vara de Rey", "Novela", 30-12-2011);
		books.add(book);
		*/
		user = new Usuario( 1, "Briggitte", "Castillo", 15 );
		users.add(user); 
		user = new Usuario( 2, "Mariela", "Morales", 30 );
		users.add(user); 
		user = new Usuario( 3, "Erasmo", "De La Cruz", 27 );
		users.add(user); 
		
		book = new Libro("LA CELESTINA", "Fernando de Rojas", "Tragicomedia", null);
		books.add(book);
		book = new Libro("CAMPOS DE FRESA", "Jordi Sierra", "Ficcion", null);
		books.add(book);
		book = new Libro("MERMELADA DE NARANJA AMARGA", "Ana Vara de Rey", "Novela", null);
		books.add(book);
		book = new Libro("NARUTO", "Kishimoto", "Manga", null);
		books.add(book);
		
		book.setPrestado(true);											//agregar un libro ('NARUTO') al usuario - 3
		LocalDateTime dateTime = LocalDateTime.of(2024,05,10,1,1);
		String formattedDateTime = dateTime.format(formatter);
		book.setLoganDate( formattedDateTime );
		user.getBooks().add(book);
		books.add(book);
		
		
		while ( option != 0 ) { 
			System.out.println( "\n-Menu BIBLIOTECA-" 
								+ "\n1. Registrarse"
								+ "\n2. Agregar libro" 
								+ "\n3. Devolver libro" 
								+ "\n4. Tomar prestado"
								+ "\n5. Buscar libro"
								+ "\n0. Salir\n");
			System.out.println("Introduzca que desea hacer: ");
			try {
				option = scanner.nextInt();
			}catch( InputMismatchException e ) {};
			
			scanner.nextLine();
			
			switch ( option ) {
			case 1:	//registrarse
				int numUsers = users.size() + 1;
				System.out.println( "Introduzca su/s apellido/s: " );
				String lastName = scanner.nextLine();
				System.out.println( "Introduzca su nombre: " );
				String name = scanner.nextLine();
				System.out.println( "Introduzca el plazo de dias qe tenga para devolver un libro: " );
				int numDaysevolution = scanner.nextInt();	
				user = new Usuario( numUsers, name, lastName, numDaysevolution ); 
				System.out.println( user.toString() );
				users.add(user);	
				break;
				
			case 2: //Agregar libro
				System.out.println( "Introduzca el titulo: " ); 
				String title = scanner.nextLine().toUpperCase();
				System.out.println( "Introduzca el autor: " ); 
				String autor = scanner.nextLine();
				System.out.println( "Introduzca la categoria: " ); 
				String category = scanner.nextLine();
				System.out.println( "Introduzca la fecha de publicacion: " );
				try {
					Date publicationDate = dateFormat.parse(scanner.nextLine());
					book = new Libro(title, autor, category, publicationDate);
					System.out.println( book.toString() );
					books.add(book);
				} catch (ParseException e) {
					System.out.println( "Introducir una fecha valida." );
				} 
				break;
				
			case 3: //Devolver libro
				System.out.println( "Introduzca su codigo de usuario: " );
				int codUser = scanner.nextInt();
				
				for (Usuario user : users) {
					if ( user.getCodUser() == codUser ) {
						System.out.println( "Introduzca el titulo: " ); 
						title = scanner1.nextLine(); 
						user.searchLoganBook(title);
					}
				}
				break;
				
			case 4:	//Tomar prestado
				System.out.println( "Introduzca su codigo de usuario: " );
				codUser = scanner.nextInt();
				
				for (Usuario user : users) {
					if ( user.getCodUser() == codUser ) {
						searchBookForLogan( user );
						
					}
				}
				break;
							
			case 5:	//Buscar libro
				System.out.println( "--Lista de libros--" );
				for (Libro libro : books) { 
					System.out.println( libro.toString() );
				}
				System.out.println(  "Filtros: TITULO, AUTOR, CATEGORIA, FECHA PUBLICACION, NINGUNO"
									+ "\nIntroduce filtro que aplicar:" );
				String filtre = scanner.nextLine().toUpperCase();
				if ( !filtre.equals("NINGUNO") ) {
					searchBook( filtre );
				}
				break; 	
				
			default: 
				System.out.println( "no le hemos entendido..." );
				break;
			}
			scanner.nextLine();
		}
	}
	
	public static void searchBook( String filtre ) {
		System.out.println( "Introduzca el/la " + filtre.toLowerCase() + ": " );
		String answer = scanner.nextLine().toUpperCase();
		System.out.println( "--Lista de libros--" );
		switch ( filtre ) {
		case "TITULO":
			for (Libro libro : books) {
				if ( libro.getTitle().contains(answer) ) {
					System.out.println( libro.toString() );
				}
			}
			break;
		case "AUTOR":
			for (Libro libro : books) {
				if ( libro.getAutor().contains(answer) ) {
					System.out.println( libro.toString() );
				}
			}
			break;
		case "CATEGORIA":
			for (Libro libro : books) {
				if ( libro.getCategory().equalsIgnoreCase(answer) ) {
					System.out.println( libro.toString() );
				}
			}	
		break;
		case "FECHA PUBLICACION":
			Date answerDate = null;
			try {
				answerDate = dateFormat.parse(answer);
			} catch (ParseException e) {
				System.out.println( "Introduzca una fecha valida." );
				searchBook( filtre );
			}
			for (Libro libro : books) {
				if ( libro.getPublicationDate().equals(answerDate) ) {
					System.out.println( libro.toString() );
				}
			}
		break;
		default:
			System.out.println( "no existe tal filtro" );
			break;
		}
	}
	
	public static void searchBookForLogan( Usuario myUser ) {
		System.out.println( "Introduzca el titulo: " ); 
		String title = scanner1.nextLine(); 
		
		for (Libro libro : books) {
			if ( libro.getTitle().equalsIgnoreCase(title) && !libro.isPrestado() ) { 
				myUser.getBooks().add(libro);
				libro.setPrestado(true);
				
				LocalDateTime dateTime = LocalDateTime.now();
				String formattedDateTime = dateTime.format(formatter);
				libro.setLoganDate( formattedDateTime );
				
				System.out.println( libro.toString() );
			}else if ( libro.getTitle().equalsIgnoreCase(title) && libro.isPrestado() ){
				System.out.println( "lo sentimos, libro no disponible" );
			}
		}
	}
}
