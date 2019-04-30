package com.example.finddifference;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Lib {
    public static java.lang.String getContent(final java.lang.String json) {
        if (json == null) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonObject result = parser.parse(json).getAsJsonObject();
        JsonArray articles = result.get("articles").getAsJsonArray();
        if (articles == null) {
            throw new NullPointerException();
        }
        double I = Math.random();
        int i = (int)(I * 10) ;
        if (articles.get(i) == null) {
            getContent(json);
        }
        JsonObject firstArticle = articles.get(i).getAsJsonObject();
        if (firstArticle.get("content") == null || firstArticle.get("content").getAsString() == null) {
            getContent(json);
        }
        String getContent = firstArticle.get("content").getAsString();
        //String getContent = content.getAsString();
        for (int j = 0; j < getContent.length(); j++) {
            if (getContent.charAt(j) != ' ') {
                getContent = getContent.substring(j, getContent.length());
                break;
            }
        }
        return getContent;
    }
}