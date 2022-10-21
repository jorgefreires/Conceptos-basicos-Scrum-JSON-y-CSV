package json_to_csv;

import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.directory.SearchResult;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		leer();

	}

	private static void leer() {
		
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
	
	private static void mostrarInformacionLinks2(JSONObject link2) {
		
		String fa3 = (String) link2.get("@_fa");
		System.out.println("Fa: " + fa3);
		
		String ref2 = (String) link2.get("@ref");
		System.out.println("Ref: " + ref2);
		
		String href2 = (String) link2.get("@href");
		System.out.println("Href: " + href2);
	}
	
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
	
	private static void mostrarInformacionNameVariant(JSONObject nameVariant) {
		
		String fa5 = (String) nameVariant.get("@_fa");
		System.out.println("Fa: " + fa5);
		
		String institution = (String) nameVariant.get("$");
		System.out.println("Institution: "+ institution);
	}
	
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
	
}