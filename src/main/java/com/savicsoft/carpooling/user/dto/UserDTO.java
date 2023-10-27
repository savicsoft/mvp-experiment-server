package com.savicsoft.carpooling.user.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import com.savicsoft.carpooling.user.model.entity.User;
import lombok.experimental.FieldDefaults;

@Value
@Builder
public class UserDTO {
    Long id;

    public static UserDTO fromUser(User user){
        return UserDTO.builder()
                .id(user.getId())
                .build();
    }

}
