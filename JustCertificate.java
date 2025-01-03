import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
 import java.security.cert.X509Certificate;

 import javax.net.ssl.HostnameVerifier;
 import javax.net.ssl.HttpsURLConnection;

 import javax.net.ssl.SSLSession;
 import javax.net.ssl.TrustManager;
 import javax.net.ssl.X509TrustManager;
 import javax.net.ssl.SSLContext;
 import javax.net.ssl.TrustManager;
  import java.security.SecureRandom;
public class JustCertificate {
    public static void main(String[] args) throws Exception {
        String url = "https://api.justcertificate.com/be/api/v1/certificate/info/DE000HB4MQQ8";
		
		     var sslContext = SSLContext.getInstance("TLS");
     var trustManager = new X509TrustManager() {
       @Override
       public X509Certificate[] getAcceptedIssuers() {
         return new X509Certificate[] {};
       }

       @Override
       public void checkClientTrusted(X509Certificate[] certs, String authType) {}

       @Override
       public void checkServerTrusted(X509Certificate[] certs, String authType) {}
     };
     sslContext.init(null, new TrustManager[] {
       trustManager
     }, new SecureRandom());

     var client = HttpClient.newBuilder()
       .sslContext(sslContext)
       .build();
	   

        
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "*/*")
                .header("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
                .header("origin", "https://justcertificate.com")
                .header("priority", "u=1, i")
                .header("referer", "https://justcertificate.com/")
                .header("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
                .header("sec-ch-ua-mobile", "?0")
                .header("sec-ch-ua-platform", "Windows")
                .header("sec-fetch-dest", "empty")
                .header("sec-fetch-mode", "cors")
                .header("sec-fetch-site", "same-site")
                .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .header("x-forward-sec", "evBruICZjJ7L1XMTczNTkxNTQ4MTA4MA==2jqpR3FwhYurc=0")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println(response.body());
        } else {
            System.err.println("Errore: " + response.statusCode());
            System.err.println(response.body());
        }
    }
}