package com.example.emailsendservice.Mappers;

import com.example.emailsendservice.Models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

public class JsonMapper {

    public static JSONObject userModelToJson(User user) {
        JSONObject jo = new JSONObject();
        jo.put("username", user.getUsername());
        jo.put("email", user.getEmail());
        return jo;
    }

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
