package com.daowen.util;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomJsonDateConverter extends ObjectMapper {

     private static final long serialVersionUID = 1L;

     public  CustomJsonDateConverter(){

     }

     @PostConstruct
     public void afterPropertiesSet() throws Exception {
//          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//          setDateFormat(sdf);
          SimpleModule simpleModule=new SimpleModule();
          simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
               @Override
               public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    String value= simpleDateFormat.format(date);
                    jsonGenerator.writeString(value);
               }
          });
          simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
               @Override
               public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    try {
                         return simpleDateFormat.parse(jsonParser.getText());
                    }catch (ParseException e){

                    }
                    return null;
               }
          });

          registerModules(simpleModule);
     }

}
