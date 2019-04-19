package org.jdonee.cooking.bootmongodb4.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.bson.types.Decimal128;
import org.jdonee.cooking.bootmongodb4.Mongodb4Boot.Decimal128Deserializer;
import org.jdonee.cooking.bootmongodb4.Mongodb4Boot.Decimal128Serializer;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Person {

    String id;
    String name;
    BigDecimal decimal;
    @JsonDeserialize(using = Decimal128Deserializer.class)
    @JsonSerialize(using = Decimal128Serializer.class)
    Decimal128 decimal128;
}