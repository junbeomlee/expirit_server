package com.tanx.expirit.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomJsonDateDeserializer extends JsonSerializer<Date>
{
//    @Override
//    public Date deserialize(JsonParser jsonparser,
//            DeserializationContext deserializationcontext) throws IOException, JsonProcessingException {
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String date = jsonparser.getText();
//        try {
//            return format.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//    }

	@Override
	public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2)
			throws IOException, JsonProcessingException {
		// TODO Auto-generated method stub
		
	}

}
