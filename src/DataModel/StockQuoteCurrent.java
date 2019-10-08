package DataModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@Entity
@Table(name = "StockQuote")
public class StockQuoteCurrent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "symbol")
	private String symbol;

	@Column(name = "attributes")
	private String attributesJson;

	public StockQuoteCurrent() {
	}

	public StockQuoteCurrent(String symbol, String attributesJson) {
		this.setSymbol(symbol);
		this.setAttributes(attributesJson);
	}

	public StockQuoteCurrent(String attributesJson) {
		try {
			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(attributesJson);
			this.setSymbol(json.get("symbol").toString());
			this.setAttributes(attributesJson);
		} catch (ParseException e) {
			this.setSymbol("UNKNOWN");
			this.setAttributes(attributesJson);
			e.printStackTrace();
		}
	}

	public void setSymbol(String symbol) {
		if (symbol != null)
			this.symbol = symbol;
	}

	public String getSymbol() {
		return (this.symbol == null) ? "" : this.symbol;
	}

	public void setAttributes(String attributes) {
		if (attributes != null)
			this.attributesJson = attributes;
	}

	public String getAttributes() {
		return (this.attributesJson == null) ? "" : this.attributesJson;
	}
}
