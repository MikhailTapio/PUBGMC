package dev.toma.pubgmc.content;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class MenuAnnouncement {

    private String[] displayedStrings;

    public static class Deserializer implements JsonDeserializer<MenuAnnouncement> {

        @Override
        public MenuAnnouncement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return null;
        }
    }
}
