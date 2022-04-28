package com.example.SpringBootRestAPI.Controller;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bson.BasicBSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@RestController
public class FileDownloadController {

	@Value("${mongoDb}")
	String mongoDb;	
	
	@Value("${dbName}")
	String dbName;	
	
	@Value("${tableName}")
	String tableName;	
	
	@GetMapping("/get")
	public BasicBSONObject mongoDbToJson() throws IOException{
		
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);

		try (var mongoClient = MongoClients.create(mongoDb)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			BasicBSONObject json = new BasicBSONObject();
			// Creating a collection object
			MongoCollection<org.bson.Document> collection = database.getCollection(tableName);
			// Retrieving the documents
			FindIterable<org.bson.Document> iterDoc = collection.find();
			Iterator it = iterDoc.iterator();
			int temp = 1;
			while (it.hasNext()) {
				json.put(Integer.toString(temp), it.next());
				temp++;
			}
			return json;
		}		
	}	
}
