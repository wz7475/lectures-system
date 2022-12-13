package swizle.utils.dtoConverters;

import swizle.models.User;
import swizle.models.dto.UserDto;

public class UserDtoConverter {
    public static User toModel(UserDto userDto) {
        return new User(userDto.getName(), userDto.getPassword());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getName(), user.getPassword());
    }
}
