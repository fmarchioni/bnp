 import java.net.URI;
 import java.net.http.HttpClient;
 import java.net.http.HttpRequest;
 import java.net.http.HttpResponse;
 import java.nio.charset.StandardCharsets;

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

 import com.google.gson.JsonArray;
 import com.google.gson.JsonElement;
 import com.google.gson.JsonObject;
 import com.google.gson.JsonParser;

 public class ISINFetch {
   private static final String url = "https://investimenti.bnpparibas.it/apiv2/api/v1/productlist/";
   static boolean DEBUG = false;

   public static void main(String[] args) throws Exception {

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

     String jsonPayload = "{\"clientId\":1,\"languageId\":\"it\",\"countryId\":\"\"," +
       "\"sortPreference\":[],\"filterSelections\":[],\"deeplinkParameters\":null," +
       "\"oldDeepLinkString\":null,\"firstUnderlyingIsin\":null,\"isBNL\":null," +
       "\"isDB\":null,\"responsetype\":null,\"productFlagFilter\":5," +
       "\"isDirectionFilterCanBeDisabled\":true,\"derivativeTypeIds\":[]," +
       "\"productSetIds\":[22,23],\"productSubGroupIds\":[],\"productGroupIds\":[6]," +
       "\"offset\":0,\"limit\":5,\"resolveSubPreset\":true," +
       "\"resolveOnlySelectedPresets\":false}";

     HttpRequest request = HttpRequest.newBuilder()
       .uri(URI.create(url))
       .header("accept", "*/*")
       .header("accept-language", "it-IT,it;q=0.9,en-US;q=0.8,en;q=0.7")
       .header("clientid", "1")
       .header("content-type", "application/json")
       .header("cookie", "ASP.NET_SessionId=c3tcefeaeeajdrz4pdm3ejum; .EPiForm_BID=d088fc4f-33d9-4aa1-a062-7f36ce8f378a; .EPiForm_VisitorIdentifier=d088fc4f-33d9-4aa1-a062-7f36ce8f378a:; __RequestVerificationToken=0AUZRF8uNMPsP-wDpYA4SvYnkIklaUDEwm2n8x4t4NHZK7Y-m9HJQDkURuakOJw6IPDzmY8ef-u5aUPLj3wX64B-9k1; TermOfUseCountry=; TermOfUseDisclaimerAccepted=true; OptanonAlertBoxClosed=2025-01-03T06:51:29.175Z; OptanonConsent=isIABGlobal=false&datestamp=Fri+Jan+03+2025+07%3A54:34+GMT+20100+(Ora+standard+dell%E2%80%99Europa+centrale)&version=202209.2.0&hosts=&consentId=5157a82a-cdd2-4380-b5b0-39abb990b876&interactionCount=3&landingPath=NotLandingPage&groups=IT001%3A1%2CIT002%A0%3A0%2CIT003%3A0&geolocation=%3B&AwaitingReconsent=false")
       .header("countryid", "")
       .header("languageid", "it")
       .header("origin", "https://investimenti.bnpparibas.it")
       .header("priority", "u=1, i")
       .header("referer", "https://investimenti.bnpparibas.it/prodotti-di-investimento/cash-collect/")
       .header("sec-ch-ua", "\"Google Chrome\";v=\"131\", \"Chromium\";v=\"131\", \"Not_A Brand\";v=\"24\"")
       .header("sec-ch-ua-mobile", "?0")
       .header("sec-ch-ua-platform", "Windows")
       .header("sec-fetch-dest", "empty")
       .header("sec-fetch-mode", "cors")
       .header("sec-fetch-site", "same-origin")
       .header("tracesessionid", "c099c99c-6a13-473b-997a-108adee86131")
       .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
       .POST(HttpRequest.BodyPublishers.ofString(jsonPayload, StandardCharsets.UTF_8))
       .build();

     HttpResponse < String > response = client.send(request, HttpResponse.BodyHandlers.ofString());

     if (response.statusCode() == 200) {
	   if (DEBUG) {	 
            System.out.println(response.body());
	   }
       parseJSON(response.body());
     } else {
       System.err.println("Errore: " + response.statusCode());
       System.err.println(response.body());
     }
   }

   public static void parseJSON(String jsonString) {
     // Parsing the JSON string
     JsonElement jsonElement = JsonParser.parseString(jsonString);
     JsonObject jsonObject = jsonElement.getAsJsonObject();

     // Getting the 'result' array
     JsonArray resultArray = jsonObject.getAsJsonArray("result");

     // Iterating through the array and printing each object
     for (JsonElement element: resultArray) {
       JsonObject resultObject = element.getAsJsonObject();
       System.out.println("isin: " + resultObject.get("isin").getAsString());
       System.out.println("Name: " + resultObject.get("name").getAsString());
       System.out.println("Derivative Type: " + resultObject.get("derivativeTypeName").getAsString());
       System.out.println("Product Name: " + resultObject.get("productName").getAsString());
       System.out.println("First Underlying Name: " + resultObject.get("firstUnderlyingName").getAsString());
       System.out.println("Localized Properties: " + resultObject.get("localizedProperties").toString());
       System.out.println("Roll History: " + resultObject.get("rollHistory").toString());
       System.out.println("-----");
     }
   }
 }