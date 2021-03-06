package dev.pizzeria.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.pizzeria.Client;

/**
 * Contrôleur responsable du traitement de la réquête : POST /clients.
 */
public class ClientController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
	List<Client> arrayClient = new ArrayList<Client>();

	/**
	 * Page HTML de la réponse en cas d'insertion effectuée. Fichier présent
	 * dans le répertoire src/main/resources.
	 */
	public static final String TEMPLATE_CLIENT_INSERE = "templates/client_insere.html";
	public static final String TEMPLATE_LISTE_CLIENTS = "templates/liste-clients.html";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// récupération du paramètre nom
		// <input name="nom">
		String nom = req.getParameter("nom");
		LOGGER.info("Paramètre nom reçu " + nom);

		// récupération du paramètre prenom
		String prenom = req.getParameter("prenom");
		LOGGER.info("Paramètre prenom reçu " + prenom);

		// récupération du paramètre ville
		String ville = req.getParameter("ville");
		LOGGER.info("Paramètre ville reçu " + ville);

		// récupération du paramètre age
		String ageStr = req.getParameter("age");
		LOGGER.info("Paramètre age reçu " + ageStr);
		int age = Integer.parseInt(ageStr);

		// Creation d'un client
		Client client = new Client(nom, prenom, ville, age);
		arrayClient.add(client);

		for (Client clientCourant : arrayClient) {
			System.out.println(clientCourant);
		}

		// TODO insérer un nouveau client en base de données

		try {
			// réponse au format UTF-8 pour le support des accents
			resp.setCharacterEncoding("UTF-8");

			// récupération du contenu du fichier template
			String template = Files
					.readAllLines(
							Paths.get(this.getClass().getClassLoader().getResource(TEMPLATE_CLIENT_INSERE).toURI()))
					.stream().collect(Collectors.joining());

			// String listeClients = Files
			// .readAllLines(
			// Paths.get(this.getClass().getClassLoader().getResource(TEMPLATE_LISTE_CLIENTS).toURI()))
			// .stream().collect(Collectors.joining());

			// écriture dans le corps de la réponse
			genererPageClient(resp);

		} catch (URISyntaxException e) {
			LOGGER.error("Fichier HTML non trouvé", e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		genererPageClient(resp);
	}

	private void genererPageClient(HttpServletResponse resp) throws IOException {
		PrintWriter writer = resp.getWriter();

		String page = "";
		page += "<!doctype html>";
		page += "<html lang=\"fr\">";
		page += "<head>";
		page += "<meta charset=\"UTF-8\">";
		page += "<title>Titre liste des clients</title>";
		page += "</head>";
		page += "<body>";
		page += "<h1>Gestion des clients</h1>";
		page += "<h2>Liste des clients</h2>";
		page += "<section>";
		page += "<table>";
		page += "<tr>";
		page += "	<th>Id.</th>";
		page += "	<th>Nom</th>";
		page += "	<th>Prénom</th>";
		page += "<th>Ville</th>";
		page += "<th>Age</th>";
		page += "</tr>";
		page += "<tr>";
		for (int j = 0; j < arrayClient.size(); j++) {
			page += "<td>cel1</td>";
			for (int i = 0; i < arrayClient.size(); i++) {
				page += "<td>" + arrayClient.get(i).getNom() + "</td>";
				page += "<td>" + arrayClient.get(i).getPrenom() + "</td>";
				page += "<td>" + arrayClient.get(i).getVille() + "</td>";
				page += "<td>" + arrayClient.get(i).getAge() + "</td>";
				page += "</tr>";
			}
		}
		// page += "<td>" + arrayClient.get(0).getNom() + "</td>";
		// page += "<td>" + arrayClient.get(0).getPrenom() + "</td>";
		// page += "<td>" + arrayClient.get(0).getVille() + "</td>";
		// page += "<td>" + arrayClient.get(0).getAge() + "</td>";
		// page += "</tr>";
		// page += "<tr>";
		// page += "<td>cel6</td>";
		// page += "<td>cel7</td>";
		// page += "<td>cel8</td>";
		// page += "<td>cel9</td>";
		// page += "<td>cel10</td>";
		// page += "</tr>";
		// page += "<tr>";
		// page += "<td>cel11</td>";
		// page += "<td>cel12</td>";
		// page += "<td>cel13</td>";
		// page += "<td>cel14</td>";
		// page += "<td>cel15</td>";
		page += "</tr>";
		page += "</table>";
		page += "</section>";
		page += "<table>";
		page += "<tr>";
		// for (Client client : arrayClient) {
		// page += "<td>" + client.getNom() + "</td>";
		// page += "<td>" + client.getPrenom() + "</td>";
		// page += "<td>" + client.getVille() + "</td>";
		// page += "<td>" + client.getAge() + "</td>";
		// page += "</tr>";
		// }
		page += "<table>";
		page += "<footer>";
		page += "<a href=\"javascript:window.history.back();\">Retour</a>";
		page += "</footer>";
		page += "</body>";
		page += "</html>";
		writer.write(page);
	}
}
