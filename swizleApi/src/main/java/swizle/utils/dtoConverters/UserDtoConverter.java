package swizle.utils.dtoConverters;

import swizle.models.User;
import swizle.models.dto.UserDto;

public class UserDtoConverter {
    public static User toModel(UserDto userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getPassword(), userDto.isAdmin());
    }

    public static UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getPassword(), user.isAdmin());
    }
}
