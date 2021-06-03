package com.sid.avro;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;
import org.eclipse.aether.internal.impl.slf4j.Slf4jLoggerFactory;

/**
 * Hello world!
 *
 */
public class AvroTest
{
    public byte[] serealizeAvroHttpRequestJSON(AvroHttpRequest request) {
        Slf4jLoggerFactory loggerFactory = new Slf4jLoggerFactory();
        loggerFactory.getLogger("");
        DatumWriter<AvroHttpRequest> writer = new SpecificDatumWriter<>(
          AvroHttpRequest.class);
        byte[] data = new byte[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Encoder jsonEncoder = null;
        try {
            jsonEncoder = EncoderFactory.get().jsonEncoder(
              AvroHttpRequest.getClassSchema(), stream);
            writer.write(request, jsonEncoder);
            jsonEncoder.flush();
            data = stream.toByteArray();
        } catch (IOException e) {
            System.err.println("Serialization error:" + e.getMessage());
        }
        return data;
    }

    public AvroHttpRequest deSerealizeAvroHttpRequestJSON(byte[] data) {
      DatumReader<AvroHttpRequest> reader
       = new SpecificDatumReader<>(AvroHttpRequest.class);
      Decoder decoder = null;
      try {
          decoder = DecoderFactory.get().jsonDecoder(AvroHttpRequest.getClassSchema(), new String(data));
          return reader.read(null, decoder);
      } catch (IOException e) {
          System.err.println("Deserialization error:" + e.getMessage());
      }
      return null;
    }

    public static void main( String[] args )
    {
      System.out.println( "Hello Java World!" );

    }
    
    //Create avro schema
    private static String createAvroSchema() {
        Schema clientIdentifier = SchemaBuilder.record("ClientIdentifier").namespace("com.sid.avro")
        .fields().requiredString("hostName").requiredString("ipAddress").endRecord();
        List<String> arrayList = new ArrayList<String>();
        Schema avroHttpRequest = SchemaBuilder.record("AvroHttpRequest")
            .namespace("com.sid.avro")
            .fields().requiredLong("requestTime")
            .name("clientIdentifier")
              .type(clientIdentifier)
              .noDefault()
            .name("employeeNames")
              .type()
              .array()
              .items()
              .stringType()
              .arrayDefault(arrayList)
            .name("active")
              .type()
              .enumeration("Active")
              .symbols("YES","NO")
              .noDefault()
            .endRecord();
        System.out.println(avroHttpRequest.toString());
        return avroHttpRequest.toString();
    }
}
