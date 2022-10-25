package json_to_csv;

/**
 * Este código sirve para leer un arhivo json llamado datos.json y convertirlo en un archivo csv llamado data.csv con aquellos datos relevantes
 * Se utilizan las librerias opencsv u json.simple, habiendo que realizar los import que se encuentran debajo
 * @author jorgefreire
 *@version1.0
 */

import java.io.FileWriter;

import java.io.FilenameFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

public class Main {

	/**
	 * Esta es el método principal de la clase, que incluye los métodos específicos para leer el archivo JSON y convertir el archivo JSON en un archivo CSV
	 */
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		leerJSON();
		escribirCSV();

	}

	/**
	 * Se incia el método que lee el archivo JSON
	 * Dentro de este método se utilizan otros métodos para leer los JSONArray existentes
	 */
	
	private static void leerJSON() {
		
		JSONParser jsonParser = new JSONParser ();
		
		try(FileReader reader = new FileReader ("datos.json")) {
			
			JSONObject documento = (JSONObject) jsonParser.parse(reader);

			System.out.println("_____EL ARCHIVO CONTIENE EL SIGUIENTE JSON_____\n");
			System.out.println(documento + "\n");

			JSONObject resultados = (JSONObject)documento.get("search-results");
			System.out.println("_____RESULTADOS DE BUSQUEDA_____\n");
	
			String totales = (String) resultados.get("opensearch:totalResults");
			System.out.println("Resultados Totales :" + totales);
			
			String index = (String) resultados.get("opensearch:startIndex");
			System.out.println("Index: " + index);
			
			String itemspagina = (String) resultados.get("opensearch:itemsPerPage");
			System.out.println("Items por página: " + itemspagina);
			
			JSONObject query = (JSONObject) resultados.get("opensearch:Query");
			System.out.println("\n_____QUERY_____\n");
			
			String rol = (String) query.get("@role");
			System.out.println("Rol: " + rol);
			
			String TerminoBusqueda = (String) query.get("@searchTerms");
			System.out.println("Término buscado: " + TerminoBusqueda);
			
			String PaginaInicio = (String) query.get("@startPage");
			System.out.println("Página de inicio: " + PaginaInicio);
			
			JSONArray Links = (JSONArray) resultados.get("link");
			System.out.println("\n_____LINKS_____");
			for(Object Link: Links) {
				mostrarInformacionLink ((JSONObject) Link);
			}
				
			JSONArray Entrys = (JSONArray) resultados.get("entry");
			System.out.println("\n_____ENTRYS_____");
			for(Object Entry: Entrys) {
				mostrarInformacionEntry ((JSONObject) Entry);
			}
			
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();	
		} catch(IOException e) {
		e.printStackTrace();		
		} catch(ParseException e) {
		e.printStackTrace();
		} 
		
	}

	/**
	 * Método para poder leer el apartado links
	 */
	
	private static void mostrarInformacionLink(JSONObject link) {
		
		String fa = (String) link.get("@_fa");
		System.out.println("\nFa: " + fa);
		
		String ref = (String) link.get("@ref");
		System.out.println("Ref: " + ref);
		
		String href = (String) link.get("@href");
		System.out.println("Href: " + href);
		
		String type = (String) link.get("@type");
		System.out.println("Type: " + type);
	}
		
	/**
	 * Método para poder leer el apartado entry
	 */
	
	private static void mostrarInformacionEntry(JSONObject entry) {
		
		System.out.println("\n---ENTRY---");
		
		String faentry = (String) entry.get("@_fa");
		System.out.println("Fa: " + faentry);
		
		JSONArray linksEntrys = (JSONArray) entry.get("link");
		System.out.println("---\nLINKS");
		for(Object link2: linksEntrys) {
			mostrarInformacionLinks2 ((JSONObject) link2);
		}
		System.out.println("---");
		
		String url = (String) entry.get("prism:url");
		System.out.println("URL: " + url);
		
		String identificador = (String) entry.get("dc:identifier");
		System.out.println("SCOPUS_ID: " + identificador);
		
		String title = (String) entry.get("dc:title");
		System.out.println("TITLE: " + title);
		
		String creator = (String) entry.get("dc:creator");
		System.out.println("CREATOR: " + creator);
		
		String publicationName = (String) entry.get("prism:publicationName");
		System.out.println("PUBLICATION NAME: " + publicationName);
		
		String issn = (String) entry.get("prism:issn");
		System.out.println("ISNN: " + issn);
		
		String eissn = (String) entry.get("prism:elssn");
		System.out.println("EISNN:"+ eissn);
		
		String volume = (String) entry.get("prism:volume");
		System.out.println("VOLUME: " + volume);
		
		String issueIdentifier = (String) entry.get("prism:issueIdentifier");
		System.out.println("ISSUE IDENTIFIER: " + issueIdentifier);
		
		String pageRange = (String) entry.get("prism:pageRange");
		System.out.println("PAGE RANGE: " + pageRange);
		
		String coverDate = (String) entry.get("prism:coverDate");
		System.out.println("COVER DATE: " + coverDate);
		
		String coverDisplayDate = (String) entry.get("prism:coverDisplayDate");
		System.out.println("COVER DISPLAY DATE: " + coverDisplayDate);
		
		String doi = (String) entry.get("prism:doi");
		System.out.println("DOI: " + doi);
		
		String description = (String) entry.get("dc:description");
		System.out.println("DESCRIPTION: " + description);
		
		String citedBy = (String) entry.get("citedby-count");
		System.out.println("CITED BY - COUNT: " + citedBy);
		
		JSONArray affiliations = (JSONArray) entry.get("affiliation");
		System.out.println("---\nAFFILIATIONS");
		for(Object affiliation: affiliations) {
			mostrarInformacionAffiliation ((JSONObject) affiliation);
		}	
		System.out.println("---");
		
		String aggregationType = (String) entry.get("prism:aggregationType");
		System.out.println("AGGREGATION TYPE: " + aggregationType);
		
		String subtype = (String) entry.get("subtype");
		System.out.println("SUBTYPE: " + subtype);
		
		String subtypeDescription = (String) entry.get("subtypeDescription");
		System.out.println("SUBTYPE DESCRIPTION: " + subtypeDescription);
		
		JSONArray authors = (JSONArray) entry.get("author");
		System.out.println("---\nAUTHORS");
		for(Object author: authors) {
			mostrarInformacionAuthors ((JSONObject) author);
		}
		System.out.println("---");
		
		String keywords = (String) entry.get("authkeywords");
		System.out.println("KEYWORDS: " + keywords);
		
	}
	
	/**
	 * Método para poder leer el apartado link presente dentro del apartado entry
	 */
	
	private static void mostrarInformacionLinks2(JSONObject link2) {
		
		String fa3 = (String) link2.get("@_fa");
		System.out.println("Fa: " + fa3);
		
		String ref2 = (String) link2.get("@ref");
		System.out.println("Ref: " + ref2);
		
		String href2 = (String) link2.get("@href");
		System.out.println("Href: " + href2);
	}
	
	/**
	 * Método para poder leer el apartado affiliation presente dentro del apartado entry
	 */
	
	private static void mostrarInformacionAffiliation(JSONObject affiliation) {
		
		String fa4 = (String) affiliation.get("@_fa");
		System.out.println("Fa: " + fa4);
		
		String url2 = (String) affiliation.get("affiliation-url");
		System.out.println("URL: " + url2);
		
		String id = (String) affiliation.get("afid");
		System.out.println("ID: " + id);
		
		String name = (String) affiliation.get("affilname");
		System.out.println("Name: " + name);
		
		String city = (String) affiliation.get("affiliation-city");
		System.out.println("City: " + city);
		
		String country = (String) affiliation.get("affiliation-country");
		System.out.println("Country: " + country);
		
		JSONArray nameVariants = (JSONArray) affiliation.get("name-variant");
		for(Object nameVariant: nameVariants) {
			mostrarInformacionNameVariant ((JSONObject) nameVariant);
		}
	}
	
	/**
	 * Método para poder leer el apartado name variant presente dentro del apartado affiliation, a su vez perteneciente a entry
	 */
	
	private static void mostrarInformacionNameVariant(JSONObject nameVariant) {
		
		String fa5 = (String) nameVariant.get("@_fa");
		System.out.println("Fa: " + fa5);
		
		String institution = (String) nameVariant.get("$");
		System.out.println("Institution: "+ institution);
	}
	
	/**
	 * Método para poder leer el apartado authors presente dentro del apartado entry
	 */
	
	private static void mostrarInformacionAuthors(JSONObject autor) {
		
		String fa6 = (String) autor.get("@_fa");
		System.out.println("Fa: " + fa6);
		
		String url3 = (String) autor.get("author-url");
		System.out.println("URL: " + url3);
		
		String id2 = (String) autor.get("authid");
		System.out.println("ID: " + id2);
		
		String name2 = (String) autor.get("authname");
		System.out.println("Name: " + name2);
		
		String givenName = (String) autor.get("authname");
		System.out.println("Given name: " + givenName);
		
		String surname = (String) autor.get("surname");
		System.out.println("Surname: " + surname);
		
		String initials = (String) autor.get("initials");
		System.out.println("Initials: " + initials);
	}
		
	/**
	 * En este punto comienza a escribirse el codigo necesario para generar y escribir el archivo CSV
	 */
	
	private static void escribirCSV() {
		
		/**
		 * Iniciamos el CSVWriter
		 */
		
		CSVWriter headerWriter;	
			
		try {
				
			/**
			 * Se crea el archivo data.csv, se ponen los titulos o encabezados de cada categoría y se cierra el escritor
			 */
			
			headerWriter = new CSVWriter(new FileWriter("data.csv", true));
				
			String[] header = {"URL","SCOPUS_ID","TITLE", "CREATOR", "PUBLICATIONNAME", "ISSN", "EISSN", "VOLUME", "PAGERANGE", "COVERDATE", "COVERDISPLAYDATE", "DOI", "CITEDBY-COUNT", "AGGREGATIONTYPE", "SYBTYPE", "SUBTYPEDESCRIPTION", };
			headerWriter.writeNext(header);
			
			headerWriter.close();
				
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/**
		 * Se vuleven a obtener los datos del apartado entry, el cuál contiene los datos que nos interesan
		 */
		
		JSONParser jsonParser = new JSONParser ();
		
		try(FileReader reader = new FileReader ("datos.json")) {
			
			JSONObject documento = (JSONObject) jsonParser.parse(reader);
	
			JSONObject resultados = (JSONObject)documento.get("search-results");
			
			JSONArray Entrys = (JSONArray) resultados.get("entry");
			for(Object Entry: Entrys) {
				
				/**
				 * con este método escribiremos los datos del entry en el csv
				 */
				
				mostrarInformacionEntryR ((JSONObject) Entry);
			}
			
		} catch (FileNotFoundException e) {
		e.printStackTrace();	
		} catch(IOException e) {
			e.printStackTrace();		
		} catch(ParseException e) {
			e.printStackTrace();
		} 
		
	}
			
	/**
	 * Iniciamos el método que nos permitira escribir los datos del apartado entry en el csv
	 * comenzamos obteniendo los datos del entry que queremos escribir
	 */
	
	private static void mostrarInformacionEntryR(JSONObject entry) {
		
		String url = (String) entry.get("prism:url");
		
		String identificador = (String) entry.get("dc:identifier");
		
		String title = (String) entry.get("dc:title");
		
		String creator = (String) entry.get("dc:creator");
		
		String publicationName = (String) entry.get("prism:publicationName");
		
		String issn = (String) entry.get("prism:issn");
		
		String eissn = (String) entry.get("prism:elssn");
		
		String volume = (String) entry.get("prism:volume");
		
		String pageRange = (String) entry.get("prism:pageRange");
		
		String coverDate = (String) entry.get("prism:coverDate");
		
		String coverDisplayDate = (String) entry.get("prism:coverDisplayDate");
		
		String doi = (String) entry.get("prism:doi");

		String citedBy = (String) entry.get("citedby-count");
		
		String aggregationType = (String) entry.get("prism:aggregationType");
		
		String subtype = (String) entry.get("subtype");
		
		String subtypeDescription = (String) entry.get("subtypeDescription");
		
		/**
		 * Ahora escribimos los datos 
		 * MUY IMPORTANTE: El nombre del archivo debe ser el mismo (en este caso data.csv) para que no se cree uno nuevo
		 */
		
		CSVWriter datosWriter = null;
		
		try {
			
			datosWriter = new CSVWriter(new FileWriter("data.csv", true));
		
			
			String[] info = {url,identificador,title, creator, publicationName, issn, eissn, volume, pageRange, coverDate, coverDisplayDate, doi, citedBy, aggregationType, subtype, subtypeDescription};
			datosWriter.writeNext(info);
			
			datosWriter.close();
			
		} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
	}
	
	/**
	 * Ya tenemos nuestro archivo data.csv en la carpeta de nuestro proyecto
	 */
}