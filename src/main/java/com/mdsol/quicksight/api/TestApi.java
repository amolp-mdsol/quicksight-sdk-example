package com.mdsol.quicksight.api;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.quicksight.AmazonQuickSight;
import com.amazonaws.services.quicksight.AmazonQuickSightClientBuilder;
import com.amazonaws.services.quicksight.model.ListUsersRequest;

public class TestApi {
    private static AmazonQuickSight quickSightClient ;
    public TestApi(){
        quickSightClient = getClient();
        System.out.println("client initialised");
    }

    public static void main(String[] args) {
        TestApi test = new TestApi();
        System.out.println("getting user list from quicksight");
        test.getUserList();
        System.out.println("test done");
    }

    public void getUserList(){
        quickSightClient.listUsers(new ListUsersRequest()
                .withAwsAccountId("767xxxxxx")
                .withNamespace("default"))
                .getUserList().forEach(user -> {
            System.out.println(user.getArn());
        });
    }

    // Basic credentials chenning client
    private static AmazonQuickSight getClient(){
        final AWSCredentialsProvider credsProvider = new AWSCredentialsProvider() {
            @Override
            public AWSCredentials getCredentials() {
                // provide actual IAM access key and secret key here
                return new BasicAWSCredentials("{accessKey}", "{secretKey}");
            }

            @Override
            public void refresh() {}
        };

        return AmazonQuickSightClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1.getName())
                .withCredentials(credsProvider)
                .build();
    }

    // TODO use while installing on EC2
    // Instance (IAM) Client
    private static AmazonQuickSight getIamClient() {
        return AmazonQuickSightClientBuilder
                .standard()
                .withRegion(Regions.US_EAST_1.getName())
                .withCredentials(new InstanceProfileCredentialsProvider(false))
                .build();
    }
}
