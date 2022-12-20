package org.example;

import lombok.AllArgsConstructor;

import java.nio.charset.StandardCharsets;
interface Data {
    byte[] toBytes();
    Data fromBytes(byte[] bytes);
}

@lombok.Data
@AllArgsConstructor
public class DatatoSTR implements Data {
    private String data;

    @Override
    public byte[] toBytes() {
        return data.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public Data fromBytes(byte[] bytes) {
        return new DatatoSTR(new String(bytes, StandardCharsets.UTF_8));
    }
}
