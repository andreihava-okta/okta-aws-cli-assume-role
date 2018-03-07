package com.okta.tools.helpers;

import com.okta.tools.aws.settings.Credentials;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public final class CredentialsHelper {
    /**
     * Gets a reader for the credentials file. If the file doesn't exist, it creates it
     *
     * @return The file reader for the credentials file
     * @throws FileNotFoundException
     * @author Andrei Hava
     * @since 02/14/2018
     */
    public static Reader getCredsReader() throws FileNotFoundException {
        return FileHelper.getReader(FileHelper.getAwsDirectory().toString() + "/credentials");
    }

    /**
     * Gets a FileWriter for the credentials file
     *
     * @return The FileWriter for the credentials file
     * @throws IOException
     * @author Andrei Hava
     * @since 02/14/2018
     */
    public static FileWriter getCredsWriter() throws IOException {
        return FileHelper.getWriter(FileHelper.getAwsDirectory().toString() + "/credentials");
    }

    /**
     * Updates the credentials file
     *
     * @param profileName     The profile to use
     * @param awsAccessKey    The access key to use
     * @param awsSecretKey    The secret key to use
     * @param awsSessionToken The session token to use
     * @throws IOException
     */
    public static void updateCredentialsFile(String profileName, String awsAccessKey, String awsSecretKey, String awsSessionToken)
            throws IOException {

        try (Reader reader = CredentialsHelper.getCredsReader()) {
            // Create the credentials object with the data read from the credentials file
            Credentials credentials = new Credentials(reader);

            // Write the given profile data
            credentials.addOrUpdateProfile(profileName, awsAccessKey, awsSecretKey, awsSessionToken);

            // Write the updated profile
            try (FileWriter fileWriter = CredentialsHelper.getCredsWriter()) {
                credentials.save(fileWriter);
            }
        }
    }
}