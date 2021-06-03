package com.sid.avro;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class TestAvro 
{
    private byte[] serealizeAvroHttpRequestJSON;
    private AvroHttpRequest deSerealizeAvroHttpRequestJSON;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testAvroSerializationDeserialization() {
        AvroTest avroTest = new AvroTest();
        AvroHttpRequest request = new AvroHttpRequest();
        request.setRequestTime(System.currentTimeMillis());
        request.setActive(Active.YES);
        List<CharSequence> listOfEmployees = new ArrayList<>();
        listOfEmployees.add("Sid");
        listOfEmployees.add("Lolly");
        request.setEmployeeNames(listOfEmployees);
        ClientIdentifier clientIdentifier = new ClientIdentifier();
        clientIdentifier.setHostName("SidHostName");
        clientIdentifier.setIpAddress("SidIP");
        request.setClientIdentifier(clientIdentifier);
        byte[] serializedHttpRequest = avroTest.serealizeAvroHttpRequestJSON(request);
        AvroHttpRequest response = 
            avroTest.deSerealizeAvroHttpRequestJSON(serializedHttpRequest);
        //System.out.println(new String(serializedHttpRequest));
        assertSame("Is Active == YES ? ", request.getActive(), response.getActive());
        
    }
}
