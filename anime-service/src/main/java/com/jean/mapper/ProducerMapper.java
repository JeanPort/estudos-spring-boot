package com.jean.mapper;

import com.jean.domain.Producer;
import com.jean.request.ProducerPostRequest;
import com.jean.request.ProducerPutRequest;
import com.jean.response.ProducerGetResponse;
import com.jean.response.ProducerPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProducerMapper {

    ProducerMapper INSTANCE = Mappers.getMapper(ProducerMapper.class);

    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(4,10))")
    Producer toProducer(ProducerPostRequest request);


    Producer toProducer(ProducerPutRequest request);
    ProducerGetResponse toProducerGetResponse(Producer producer);
    List<ProducerGetResponse> toListProducerGetResponse(List<Producer> producers);
    ProducerPostResponse toProducerPostResponse(Producer producer);

}
