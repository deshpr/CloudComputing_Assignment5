/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lambda;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context; 
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import faasinspector.register;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

/**
 * uwt.lambda_test::handleRequest
 * @author wlloyd
 */
public class ProcessCSV implements RequestHandler<Request, Response>
{
    static String CONTAINER_ID = "/tmp/container-id";
    static Charset CHARSET = Charset.forName("US-ASCII");
    
    
    // Lambda Function Handler
    public Response handleRequest(Request request, Context context) {
        // Create logger
        LambdaLogger logger = context.getLogger();
        
        // Register function
        register reg = new register(logger);

        int row = request.getRow();
        int col = request.getCol();
        
        String bucketname = request.getBucketname();
        String filename = request.getFilename();
           String srcKey = "test.csv"; 
        
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().build();         
//get object file using source bucket and srcKey name
S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketname, srcKey));
//get content of the file
InputStream objectData = s3Object.getObjectContent();
//scanning data line by line
String textToUpload = "";
Scanner scanner = new Scanner(objectData);
long total = 0;
double avg = 0;
int count  = 0;
while (scanner.hasNext()) {
    String line = scanner.nextLine();
    String[] parts = line.split(",");
   long sum = 0;
    for(int i = 0; i < parts.length; i++){
        sum += Integer.parseInt(parts[i]);
        count += 1;
    }
    total += sum;
 
textToUpload += line;
}
scanner.close();
        
                  //stamp container with uuid
        Response r = reg.StampContainer();
        
avg = total/count;
        logger.log("ProcessCSV bucketname:" +  bucketname + "  filename:" + filename +  " avg­element:" + avg + " total:"+ total);
r.setValue("Bucket: " + bucketname + " filename:" + filename + " processed.");
        return r;
      
    }
    
    // int main enables testing function from cmd line
    public static void main (String[] args)
    {
        Context c = new Context() {
            @Override
            public String getAwsRequestId() {
                return "";
            }

            @Override
            public String getLogGroupName() {
                return "";
            }

            @Override
            public String getLogStreamName() {
                return "";
            }

            @Override
            public String getFunctionName() {
                return "";
            }

            @Override
            public String getFunctionVersion() {
                return "";
            }

            @Override
            public String getInvokedFunctionArn() {
                return "";
            }

            @Override
            public CognitoIdentity getIdentity() {
                return null;
            }

            @Override
            public ClientContext getClientContext() {
                return null;
            }

            @Override
            public int getRemainingTimeInMillis() {
                return 0;
            }

            @Override
            public int getMemoryLimitInMB() {
                return 0;
            }

            @Override
            public LambdaLogger getLogger() {
                return new LambdaLogger() {
                    @Override
                    public void log(String string) {
                        System.out.println("LOG:" + string);
                    }
                };
            }
        };
        
        // Create an instance of the class
        ProcessCSV lt = new ProcessCSV();
       
        
        // Load the name into the request object
        // Report name to stdout
        Request req = new Request();
        req.setBucketname(args[0]);
        req.setBucketname(args[1]);
        req.setBucketname(args[2]);
        req.setBucketname(args[3]);
        
        // Run the function
        Response resp = lt.handleRequest(req, c);
        
        // Print out function result
        System.out.println("function result:" + resp.toString());
    }
}
