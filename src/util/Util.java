package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import secret.IEX_API;

public class Util implements IEX_API {
	
	public static List<String> parse(String in)
	{
		if (in == null) return null;
		Scanner read = new Scanner(in);
		List<String> sym = new ArrayList<>(); 
		read.useDelimiter(",");
		try {
			while (read.hasNext())
				sym.add(read.next().toUpperCase());

			read.close();
			
			return sym;
			
		} catch (Exception e) {
			read.close();
			e.printStackTrace();
			return null; 
		}
	}
	
	public static ArrayList<String> generateSymbolStockQuoteURL(HttpServletRequest request)
	{
		ArrayList<String> symbolURL = new ArrayList<String>(); 
		String baseURL = "https://sandbox.iexapis.com/stable/stock/";
		
		Scanner read = new Scanner(request.getParameter("pSymbols"));
		read.useDelimiter(","); 
		
		while (read.hasNext())
			symbolURL.add(baseURL + read.next() /* Symbol */ + "/quote?token=" + IEX_API.getPK()); 
		
		read.close();
		return symbolURL; 
	}
	
	public static String generateCSV(String jsonIn) {

		try {
			JSONParser parse = new JSONParser(); 
			JSONObject obj = (JSONObject) parse.parse(jsonIn);
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		} 
		
		
		return null; 
	}
}
