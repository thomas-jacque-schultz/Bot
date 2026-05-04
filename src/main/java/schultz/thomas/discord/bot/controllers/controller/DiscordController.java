package schultz.thomas.discord.bot.controllers.controller;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.JDA;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import schultz.thomas.discord.bot.business.mapper.DiscordChannelMapper;
import schultz.thomas.discord.bot.business.services.DiscordMessageService;
import schultz.thomas.discord.bot.controllers.dto.DiscordGuildChannelsDto;
import schultz.thomas.discord.bot.model.entity.ChannelEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/discord")
@RequiredArgsConstructor
public class DiscordController {

    private final JDA jda;
    private final DiscordMessageService discordMessageService;
    private final DiscordChannelMapper discordChannelMapper;

    @GetMapping("/guilds/channels")
    public ResponseEntity<List<DiscordGuildChannelsDto>> getGuildsWithChannels() {
        Set<String> subscribedChannelIds = discordMessageService.getSubscribedChannels().stream()
                .map(ChannelEntity::getChannelId)
                .collect(Collectors.toSet());

        List<DiscordGuildChannelsDto> payload = jda.getGuilds().stream()
            .map(guild -> discordChannelMapper.toGuildDto(guild, subscribedChannelIds))
                .toList();

        return ResponseEntity.ok(payload);
    }
}
