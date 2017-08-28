package de.fraunhofer.polycare.controllers.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by aw3s0 on 8/6/2017.
 */
public class JSONConverter {
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
