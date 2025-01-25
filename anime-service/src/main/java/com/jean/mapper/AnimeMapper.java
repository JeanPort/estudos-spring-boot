package com.jean.mapper;

import com.jean.domain.Anime;
import com.jean.request.AnimePostRequest;
import com.jean.request.AnimePutRequest;
import com.jean.response.AnimeGetResponse;
import com.jean.response.AnimePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimeMapper {

    AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);


    @Mapping(target = "id", expression = "java(java.util.concurrent.ThreadLocalRandom.current().nextLong(4,10))")
    Anime toAnime(AnimePostRequest request);

    Anime toAnime(AnimePutRequest request);

    AnimeGetResponse toAnimeGetResponse(Anime Anime);

    AnimePostResponse toAnimePostResponse(Anime anime);

    List<AnimeGetResponse> toListAnimeGetResponse(List<Anime> animes);


}
