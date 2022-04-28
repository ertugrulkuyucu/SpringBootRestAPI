package com.example.SpringBootRestAPI.Controller;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	
	@Value("${file.upload-dir}")
	String FILE_DIRECTORY;	
		
	@PostMapping("/uploadFile")
	public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws IOException, InterruptedException{
		File newFile = new File("PostmanFiles");
		newFile.mkdir();
		File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
		myFile.createNewFile();
		FileOutputStream fos =new FileOutputStream(myFile);
		fos.write(file.getBytes());
		fos.close();
		
		System.out.println("Please wait a secont for data preparation...");
		TimeUnit.SECONDS.sleep(1);
		
		  String Host = "localhost"; 
		  String Port = "27017"; 
		  String DB = "Veribel";
		  String CollectionName = "xrtest"; 
		  String FileName = "PostmanFiles\\"+file.getOriginalFilename();
		  		  
		  // Without Credential 
		  String command = "C:\\Program Files\\MongoDB\\Tools\\100\\bin\\mongoimport.exe --host " + Host + " --port " + Port + " --db " + DB + " --collection " + CollectionName + "  --headerline --type=csv --file "+ FileName;
		 
		  try { Process process = Runtime.getRuntime().exec(command);
		  System.out.println("Imported csv into Database"); 
		  } 
		  catch (Exception e) {
		  System.out.println("Error executing " + command + e.toString());
		  }	
		return new ResponseEntity<Object>("The File Uploaded Successfully", HttpStatus.OK);		
	}		
}
