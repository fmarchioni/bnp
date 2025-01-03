import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class ISINFetchJava8 {
    private static final String url = "https://investimenti.bnpparibas.it/apiv2/api/v1/productlist/";
	
	
    private static void trustAllHosts() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // Trust all
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws java.security.cert.CertificateException {
                        // Trust all
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	

    public static void main(String[] args) throws Exception {
		  trustAllHosts();
        String jsonPayload = "{\"clientId\":1,\"languageId\":\"it\",\"countryId\":\"\","
                + "\"sortPreference\":[],\"filterSelections\":[],\"deeplinkParameters\":null,"
                + "\"oldDeepLinkString\":null,\"firstUnderlyingIsin\":null,\"isBNL\":null,"
                + "\"isDB\":null,\"responsetype\":null,\"productFlagFilter\":5,"
                + "\"isDirectionFilterCanBeDisabled\":true,\"derivativeTypeIds\":[],"
                + "\"productSetIds\":[22,23],\"productSubGroupIds\":[],\"productGroupIds\":[6],"
                + "\"offset\":0,\"limit\":5,\"resolveSubPreset\":true,"
                + "\"resolveOnlySelectedPresets\":false}";

        byte[] postData = jsonPayload.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;

        // Costruiamo la richiesta HTTP
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        // Impostiamo gli header
        con.setRequestProperty("accept", "*/*");
        con.setRequestProperty("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7");
        con.setRequestProperty("clientid", "1");
        con.setRequestProperty("content-type", "application/json");
        con.setRequestProperty("cookie", "ASP.NET_SessionId=c3tcefeaeeajdrz4pdm3ejum; .EPiForm_BID=d088fc4f-33d9-4aa1-a062-7f36ce8f378a; .EPiForm_VisitorIdentifier=d088fc4f-33d9-4aa1-a062-7f36ce8f378a:; __RequestVerificationToken=0AUZRF8uNMPsP-wDpYA4SvYnkIklaUDEwm2n8x4t4NHZK7Y-m9HJQDkURuakOJw6IPDzmY8ef-u5aUPLj3wX64B-9k1; TermOfUseCountry=; TermOfUseDisclaimerAccepted=true; OptanonAlertBoxClosed=2025-01-03T06:51:29.175Z; OptanonConsent=isIABGlobal=false&datestamp=Fri+Jan+03+2025+07%3A54:34+GMT+20100+(Ora+standard+dell%E2%80%99Europa+centrale)&version=202209.2.0&hosts=&consentId=5157a82a-cdd2-4380-b5b0-39abb990b876&interactionCount=3&landingPath=NotLandingPage&groups=IT001%3A1%2CIT002%A0%3A0%2CIT003%3A0&geolocation=%3B&AwaitingReconsent=false");
        con.setRequestProperty("countryid", "");
        con.setRequestProperty("languageid", "it");
        con.setRequestProperty("origin", "https://investimenti.bnpparibas.it");
        con.setRequestProperty("priority", "u=1, i");
        con.setRequestProperty("referer", "https://investimenti.bnpparibas.it/prodotti-di-investimento/cash-collect/");
        con.setRequestProperty("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"");
        con.setRequestProperty("sec-ch-ua-mobile", "?0");
        con.setRequestProperty("sec-ch-ua-platform", "Windows");
        con.setRequestProperty("sec-fetch-dest", "empty");
        con.setRequestProperty("sec-fetch-mode", "cors");
        con.setRequestProperty("sec-fetch-site", "same-origin");
        con.setRequestProperty("tracesessionid", "c099c99c-6a13-473b-997a-108adee86131");
        con.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");

        // Inviamo i dati nel corpo della richiesta
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.write(postData);
        wr.flush();
        wr.close();

        // Otteniamo la risposta
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.err.println("Errore: " + responseCode);
            BufferedReader error = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = error.readLine()) != null) {
                response.append(inputLine);
            }
            error.close();
            System.err.println(response.toString());
        }
    }
}