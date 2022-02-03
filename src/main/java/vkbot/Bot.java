package vkbot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;


public class Bot {
    public static void main(String[] args) throws ClientException, ApiException, InterruptedException, IOException
    {
        BufferedReader dataReader = new BufferedReader(new InputStreamReader(System.in));
        TransportClient transportClient = new HttpTransportClient();
        VkApiClient vk = new VkApiClient(transportClient);

        System.out.println("Enter your id:");
        int actorId = Integer.parseInt(dataReader.readLine());
        System.out.println("Enter your access token:");
        String token = dataReader.readLine();
        UserActor actor = new UserActor(actorId, token);

        System.out.println("Enter dialog id:");
        int peerId = Integer.parseInt(dataReader.readLine());

        System.out.println("Enter delay between sending messages (in seconds):");
        int delay = Integer.parseInt(dataReader.readLine()) * 1000;


        //adding phrases from the file to arraylist
        System.out.println("Enter name of your txt file with phrases (you can type \"quotes.txt\" for example or add your own file):");
        String fileName = dataReader.readLine();
        FileReader reader = new FileReader(fileName);
        BufferedReader fileReader = new BufferedReader(reader);
        List<String> phrases = new ArrayList<>();
        String phrase = fileReader.readLine();
        while (phrase != null) {
            phrases.add(phrase);
            phrase = fileReader.readLine();
        }
        fileReader.close();
        reader.close();
        dataReader.close();


        //sending messages
        Random random = new Random();
        while (true) {
            String str = phrases.get( random.nextInt(phrases.size()) );
            vk.messages().send(actor).peerId(peerId).message(str).randomId(random.nextInt(10000)).execute();
            Thread.sleep(delay);
        }

    }
}