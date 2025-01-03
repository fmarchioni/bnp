import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
 
import java.net.http.HttpClient;
import java.security.SecureRandom;

import java.util.List;
import java.util.ArrayList;

public class ISINDetails {
 

	  

    public static void main(String[] args) throws Exception {
		 var sslContext = SSLContext.getInstance("TLS");
			var trustManager = new X509TrustManager() {
			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[]{};
			}

			@Override
			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
			};
			sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());

			var client = HttpClient.newBuilder()
				.sslContext(sslContext)
				.build();
			 
		
		List<String> list = new ArrayList();
        list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/product/header/NLBNPIT1LSY0");
	    list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/product/description/NLBNPIT1LSY0");
	    list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/product/keyvaluerow/NLBNPIT1LSY0");
        list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/product/underlyingbasket/NLBNPIT1LSY0");
        list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/product/calendar/NLBNPIT1LSY0");
        list.add("https://investimenti.bnpparibas.it/apiv2/api/v1/proxy/scenario_analysis/NLBNPIT1LSY0");
		
		for (String url: list) {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.GET()
					.setHeader("accept", "*/*")
					.setHeader("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
					.setHeader("clientid", "1")
					.setHeader("content-type", "application/json")
					.setHeader("cookie", "... (replace with your actual cookie string) ...")
					.setHeader("countryid", "")
					.setHeader("languageid", "it")
					.setHeader("priority", "u=1, i")
					.setHeader("referer", "https://investimenti.bnpparibas.it/product-details/NLBNPIT1LSY0/")
					.setHeader("sec-ch-ua", "\"Google Chrome\"v=\"131\", \"Chromium\"v=\"131\", \"Not_A Brand\"v=\"24\"")
					.setHeader("sec-ch-ua-mobile", "?0")
					.setHeader("sec-ch-ua-platform", "Windows")
					.setHeader("sec-fetch-dest", "empty")
					.setHeader("sec-fetch-mode", "cors")
					.setHeader("sec-fetch-site", "same-origin")
					.setHeader("tracesessionid", "f1a8241e-f209-4383-80ef-712b83c3bc17")
					.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0 Win64 x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")               .build();

			HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
			System.out.println(response.body());
		}
    }
}
 
 