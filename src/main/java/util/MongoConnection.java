package util;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConnection {

	private static final String URI =
		    "mongodb+srv://wmllima25_db_user:81583631%40Wml@cluster0.ydwbjwr.mongodb.net/cadastroDB?retryWrites=true&w=majority";

    private static MongoClient client = MongoClients.create(URI);

    public static MongoDatabase getDatabase() {
        return client.getDatabase("cadastroDB");
    }
}