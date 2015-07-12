package com.viktor235.vkaccesstokengetter;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Виктор
 */
public class Handler {

	private String connectionUrl;
	private String inputUrl;
	private String accessToken;

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public void setInputUrl(String url) {
		this.inputUrl = url;
	}

	public void parseUrl() {
		String errorMessage = "URL not contains access token";
		if (inputUrl == null) {
			accessToken = errorMessage;
			return;
		}

		accessToken = inputUrl;
		String startKey = "#access_token=",
				endKey = "&";

		int atStart = accessToken.indexOf(startKey) + startKey.length();
		if (atStart < startKey.length()) {
			accessToken = errorMessage;
			return;
		}

		accessToken = accessToken.substring(atStart);

		int atEnd = accessToken.indexOf(endKey);
		if (atEnd < 0) {
			return;
		}

		accessToken = accessToken.substring(0, atEnd);
	}

	public String getAccessToken() {
		return accessToken;
	}

	private void writeToFile(String fileName, String Content) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			bw.write(Content);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void writeAccesTokenToFile(String fileName) {
		writeToFile(fileName, accessToken);
	}
}
