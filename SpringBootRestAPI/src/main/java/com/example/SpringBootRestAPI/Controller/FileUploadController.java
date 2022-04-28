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
	@Value("${mongoImportPath}")
	String mongoImportPath;			
	@Value("${tableName}")
	String tableName;		
	@Value("${dbName}")
	String dbName;		
	@Value("${Host}")
	String Host;		
	@Value("${port}")
	String Port;		
	@Value("${warningMessageWait}")
	String warningMessageWait;	
	@Value("${warningMessageUploadedDb}")
	String warningMessageUploadedDb;	
	@Value("${warningMessageUploaded}")
	String warningMessageUploaded;	
	
	@PostMapping("/uploadFile")
	public ResponseEntity<Object> fileUpload(@RequestParam("File") MultipartFile file) throws IOException, InterruptedException{
		File newFile = new File(FILE_DIRECTORY);
		newFile.mkdir();
		File myFile = new File(FILE_DIRECTORY+file.getOriginalFilename());
		myFile.createNewFile();
		FileOutputStream fos =new FileOutputStream(myFile);
		fos.write(file.getBytes());
		fos.close();
		
		System.out.println(warningMessageWait);
		TimeUnit.SECONDS.sleep(1);
				  		  
		  // Without Credential 
		  String command = mongoImportPath + "mongoimport.exe --host " + Host + " --port " + Port + " --db " + dbName + " --collection " + tableName + "  --headerline --type=csv --file "+ FILE_DIRECTORY+file.getOriginalFilename();
		 
		  try { Process process = Runtime.getRuntime().exec(command);
		  System.out.println(warningMessageUploadedDb); 
		  } 
		  catch (Exception e) {
		  System.out.println(command + e.toString());
		  }	
		return new ResponseEntity<Object>(warningMessageUploaded, HttpStatus.OK);		
	}		
}
