package org.jrotero;
 
import java.time.Duration;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter;
import java.util.Date; 

public class Libro {
	
	// diferentes criterios como titulo, author, categoria o anio de publicacion
	
	//private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private String title;
	private String author;
	private String category;
	private Date publicationDate;
	private boolean prestado;
	
	private String loganDate;
	
	public Libro ( String title, String author, String category, Date publicationDate ) {
		this.title = title;
		this.author = author;
		this.category = category;
		this.publicationDate = publicationDate;
		this.prestado = false;
	}
	/*	este metodo calcula si el usuario tiene que pagar una multa al devolver un libro, primero calculando la fecha limite
	 * 	que tenia el usuario para hacer su debidad devolución y luego comparandola con la fecha actual
	 * 	segun la difernecia de dias habrá una multa u otra
	 */
	public double calculateDevolution( int loganDays  ) {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		LocalDate dateDevolution = LocalDate.now();					//coger fecha actual
		
		LocalDate dateLogan = LocalDate.parse(loganDate, format); 	//convertir cadena de texto a un formato fecha
		
		dateLogan = dateLogan.plusDays(loganDays);					//calcular la fecha del plazo valido para devolver del usuario
		
		Duration calculate = Duration.between ( dateDevolution.atStartOfDay(), dateLogan.atStartOfDay() );		//calcular diferencia entre fechas
		long totalDays = calculate.toDays ();										//convertir dias a numero
		
		double debt = Math.round( ( totalDays * -1 ) * 1.55 ) / 1.0;				//calcular multa
		
		if ( totalDays < 0 ) {														//comprobar si tiene que pagar una multa
			System.out.println( "Se ha pasado por " + ( totalDays * -1 ) + " de su plazo, debe " + debt + " euros por pasarse de dias" ); 
			return debt;
		}else if (  totalDays == 0 ){
			System.out.println( "es su ultimo dia para devolver!!!, no debe nada" );
			return 0;
		}else {
			System.out.println( "faltan " + totalDays + " dias de plazo para devolver, no debe nada" ); 
			return 0;
		}
	}
	// este metodo calcula la multa segun el nivel de danio que se le haya hecho a un libro
	public double calculateState ( int state ) {
		if ( state == 3 ) {
			System.out.println( "gracias por mantener el libro en buenas condiciones!!!, no debe nada" );
			return 0;
		}
		if (state == 2) {
			System.out.println( "lo intene cuidar mejor la proxima vez!!, debe 2 euros" );
			return 2;
		}
		if (state == 1) {
			System.out.println( "no deberian dejarle ningun libro, debe 5 euros" );
			return 5;
		}
		return 0;
	}
	
	
	public String toString() {
		return category + " | Libro " + title + " de " + author + " publicado en " + publicationDate + " | " + (prestado ? "PRESTADO" : "DISPONIBLE") ;
	}

	public String getTitle() {
		return title;
	}

	public String getAutor() {
		return author;
	}

	public String getCategory() {
		return category;
	}
	
	public Date getPublicationDate() {
		return publicationDate;
	}

	public boolean isPrestado() {
		return prestado;
	}

	public void setPrestado(boolean prestado) {
		this.prestado = prestado;
	}

	public String getLoganDate() {
		return loganDate;
	}

	public void setLoganDate(String loganDate) {
		this.loganDate = loganDate;
	}
	

}
