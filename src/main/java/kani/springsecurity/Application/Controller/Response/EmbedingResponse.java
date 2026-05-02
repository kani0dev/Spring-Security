package kani.springsecurity.Application.Controller.Response;

import kani.springsecurity.Domain.Embeding.Embeding;

public record EmbedingResponse(Long user_id, float[] embedding) {
    public static Embeding ToEntity(EmbedingResponse response){
        return Embeding.builder()
                .id(response.user_id())
                .user_id(response.user_id())
                .embedding(response.embedding())
                .build();
    }
}
