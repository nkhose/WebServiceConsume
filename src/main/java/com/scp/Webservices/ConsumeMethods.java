package com.scp.Webservices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ConsumeMethods {

	public static void main(String[] args) throws Exception {
		// String uri ="http://jsonplaceholder.typicode.com/users";

		System.out.println("************RestFul client Using RestAssured************ ");
		getResponse();

		System.out.println("\n\n************RestFul client Using HttpClient************ ");
		getHttpClient();

		System.out.println("\n\n************RestFul client Using java.net.URL************ ");
		getNetURL();

		System.out.println("\n\n************RestFul client Using RESTEasy************ ");
		getRESTEasy();

	}

	private static void getResponse() {
		// TODO Auto-generated method stub
		Response response = RestAssured.get("http://jsonplaceholder.typicode.com/users");
		JSONArray jsonArray = new JSONArray(response.asString());
		formatData(jsonArray);
	}

	private static void getHttpClient() throws ClientProtocolException, IOException {
		// TODO Auto-generated method stub
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet("http://jsonplaceholder.typicode.com/users");
		HttpResponse response = httpClient.execute(getRequest);
		JSONArray jsonArray = new JSONArray(EntityUtils.toString(response.getEntity()));
		formatData(jsonArray);
	}

	private static void getNetURL() throws IOException {
		// TODO Auto-generated method stub
		URL url = new URL("http://jsonplaceholder.typicode.com/users");
		HttpURLConnection connetion = (HttpURLConnection) url.openConnection();
		connetion.setRequestMethod("GET");
		JSONArray jsonArray = new JSONArray(IOUtils.toString(connetion.getInputStream()));
		formatData(jsonArray);

	}

	private static void getRESTEasy() throws Exception {
		// TODO Auto-generated method stub
		ClientRequest request = new ClientRequest("http://jsonplaceholder.typicode.com/users");
	//	request.accept("application/json");
		ClientResponse<String> response = request.get(String.class);
		JSONArray jsonArray = new JSONArray(response.getEntity());
		formatData(jsonArray);
	}

	private static void formatData(JSONArray jsonArray) {
		// TODO Auto-generated method stub

		UserInfo userInfo = null;
		Address address = null;
		Geo geo = null;
		Company company = null;

		for (Object object : jsonArray) {
			JSONObject jsonUserObj = (JSONObject) object;
			userInfo = new UserInfo();
			address = new Address();
			geo = new Geo();
			company = new Company();

			userInfo.setId(jsonUserObj.getInt("id"));
			userInfo.setName(jsonUserObj.getString("name"));
			userInfo.setUsername(jsonUserObj.getString("username"));
			userInfo.setEmail(jsonUserObj.getString("email"));
			userInfo.setPhone(jsonUserObj.getString("phone"));
			userInfo.setWebsite(jsonUserObj.getString("website"));

			JSONObject jsonAddressObj = jsonUserObj.getJSONObject("address");
			address.setStreet(jsonAddressObj.getString("street"));
			address.setSuite(jsonAddressObj.getString("suite"));
			address.setCity(jsonAddressObj.getString("city"));
			address.setZipcode(jsonAddressObj.getString("zipcode"));

			JSONObject jsonGeoObj = jsonAddressObj.getJSONObject("geo");
			geo.setLat(jsonGeoObj.getDouble("lat"));
			geo.setLng(jsonGeoObj.getDouble("lng"));

			JSONObject jsonCompanyObj = jsonUserObj.getJSONObject("company");
			company.setName(jsonCompanyObj.getString("name"));
			company.setCatchPhrase(jsonCompanyObj.getString("catchPhrase"));
			company.setBs(jsonCompanyObj.getString("bs"));

			address.setGeo(geo);
			userInfo.setAddress(address);
			userInfo.setCompany(company);

			// System.out.println("hello");

			System.out.println(userInfo);

		}
	}

}
