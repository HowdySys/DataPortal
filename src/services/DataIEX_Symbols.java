package services;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.*;

import secret.IEX_API;
import util.Util;
import util.UtilDB;

/**
 * Servlet implementation class DataIEX
 */
@WebServlet("/DataIEX")
public class DataIEX_Symbols extends HttpServlet implements IEX_API {
	private static final long serialVersionUID = 1L;
	private String url;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DataIEX_Symbols() {
		super();
		url = IEX_API.getURL();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ArrayList<String> urls = Util.generateSymbolStockQuoteURL(request); 
		ArrayList<String> jsonResp = new ArrayList<String>(); 
		
		try {
			for (String u : urls)
			{
				URL url = new URL(u);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");

				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed: HTTP error code: " + conn.getResponseCode());
				}
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

				String output;
				while ((output = br.readLine()) != null)
					jsonResp.add(output); 
				conn.disconnect();
			}
			
			for (String s : jsonResp) {
				UtilDB.insertSymbol(s);
			}
			
		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
