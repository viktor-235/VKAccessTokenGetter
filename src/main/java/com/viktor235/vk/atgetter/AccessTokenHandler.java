package com.viktor235.vk.atgetter;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Victor
 */
public class AccessTokenHandler {
    private String connectionUrlFormat = "http://oauth.vk.com/authorize?client_id=%s&scope=%s&redirect_uri=http://oauth.vk.com/blank.html&display=popup&response_type=token";
    private String userId = "";
    private Set<String> scope = new HashSet<>();
    private String accessToken;

    public String getConnectionUrl() {
        String joinedScope = StringUtils.join(scope, ",");
        return String.format(connectionUrlFormat, userId, joinedScope);
    }

    public String parseResponseUrl(String responseUrl) {
        accessToken = responseUrl;
        String startKey = "#access_token=",
                endKey = "&";

        int atStart = accessToken.indexOf(startKey) + startKey.length();
        if (atStart < startKey.length())
            return null;

        accessToken = accessToken.substring(atStart);

        int atEnd = accessToken.indexOf(endKey);
        if (atEnd < 0) {
            return accessToken;
        }

        return accessToken.substring(0, atEnd);
    }

    private void writeToFile(String fileName, String Content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(Content);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void writeAccesTokenToFile(String fileName) {
        writeToFile(fileName, accessToken);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getScope() {
        return scope;
    }
}
