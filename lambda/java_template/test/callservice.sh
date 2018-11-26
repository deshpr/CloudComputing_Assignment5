#!/bin/bash

# JSON object to pass to Lambda Function
json={"\"row\"":50,"\"col\"":10,"\"bucketname\"":\"test.bucket.562.rah1\"","\"filename\"":\"test.csv\""}

echo "Invoking Lambda function using API Gateway"
time output=`curl -s -H "Content-Type: application/json" -X POST -d  $json https://n7okxkd7ll.execute-api.us-west-2.amazonaws.com/hello_dev_assignment5/`

echo ""
echo "CURL RESULT:"
echo $output
echo ""
echo ""

#echo "Invoking Lambda function using AWS CLI"
#time output=`aws lambda invoke --invocation-type RequestResponse --function-name #hello_assignment5 --region us-west-2 --payload $json /dev/stdout | head -n 1 | head -c -2 ; echo`
#echo ""
#echo "AWS CLI RESULT:"
#echo $output
#echo ""







