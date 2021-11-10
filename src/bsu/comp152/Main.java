package bsu.comp152;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) {
        var dataGrabber = HttpClient.newHttpClient(); //
        var requestMaker = HttpRequest.newBuilder();//
        var webAddress = URI.create("http://universities.hipolabs.com/search?name=Young");
        var dataRequest= requestMaker.uri(webAddress).build();//request maker is object, uri is function
        HttpResponse<String> response=null; //initialized w/ null; if exception, responce is null, if not, we get something useful
        try{
            response = dataGrabber.send(dataRequest, HttpResponse.BodyHandlers.ofString());
        }
        catch (IOException exception){
            System.out.println("Error connecting to networked rss");
        }
        catch (InterruptedException e){
            System.out.println("An error occurred getting a response from the server");
        }
        if (response==null){ //if response is null then we quit
            System.out.println("something went wrong, we will close now");
            System.exit(-1);
        }
        var usefulData=response.body(); //responce is object, body() is method called on object
        System.out.println(usefulData);
        var dataParser=new Gson();
        UniversityDataType[] parsedData=dataParser.fromJson(usefulData, UniversityDataType[].class);
        for(var currentUniversity: parsedData){
            System.out.println(currentUniversity);
        }
    }
}
