package schultz.thomas.discord.bot.business.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import schultz.thomas.discord.bot.business.service.command.Command;
import schultz.thomas.discord.bot.business.service.mapper.UserEntityMapper;
import schultz.thomas.discord.bot.model.entity.ServerEntity;
import schultz.thomas.discord.bot.model.entity.UserEntity;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;
import schultz.thomas.discord.bot.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private List<UserEntity> userStateCache;

    private final UserRepository userRepository;

    private final UserEntityMapper userEntityMapper;

    @PostConstruct
    public void init() {
        userStateCache = userRepository.findAll();
    }


    public void createUser(UserEntity ue) {
        if(userStateCache.stream().anyMatch(user -> user.getId().equals(ue.getId()))) {
            throw new IllegalArgumentException("User already exists");
        }
        userRepository.save(ue);
        userStateCache.add(ue);
    }

    public void updateUser(UserEntity ue) {
        Optional<UserEntity> toUpdate  =  userStateCache.stream().filter(u -> u.getId().equals(ue.getId())).findFirst();
        if(!toUpdate.isPresent()){
            throw new IllegalArgumentException("User does not exist");
        }
        userEntityMapper.updateServeurEntityFromSource(ue, toUpdate.get());
        userRepository.save(toUpdate.get());
        //
    }

    public UserPrivilegeEnum getRole(String discordId){
        Optional<UserEntity> candidat  =  userStateCache.stream().filter(u -> u.getDiscordId().equals(discordId)).findFirst();
        return candidat.map(UserEntity::getPrivilege).orElse(null);
    }

    public UserEntity get(String discordId) {
        Optional<UserEntity> candidat  =  userStateCache.stream().filter(u -> u.getDiscordId().equals(discordId)).findFirst();
        return candidat.orElse(null);
    }
}
