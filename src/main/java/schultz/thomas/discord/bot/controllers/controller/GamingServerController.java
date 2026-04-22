package schultz.thomas.discord.bot.controllers.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schultz.thomas.discord.bot.business.mapper.ServeurEntityMapper;
import schultz.thomas.discord.bot.business.services.GamingServerService;
import schultz.thomas.discord.bot.controllers.dto.GamingServerDto;
import schultz.thomas.discord.bot.model.entity.GamingServerEntity;

import java.util.List;


@RestController
@RequestMapping("/gaming-server")
@RequiredArgsConstructor
public class GamingServerController {

    private final GamingServerService gamingServerService;
    private final ServeurEntityMapper serveurEntityMapper;

    @GetMapping
    public List<GamingServerDto> getAllServers() {
        return gamingServerService.getAllGameServerEntities().stream()
                .map(serveurEntityMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GamingServerDto> getServerById(@PathVariable String id) {
        GamingServerEntity server = gamingServerService.getGameServerEntityByIdentifier(id);
        if (server == null) {
            server = gamingServerService.getAllGameServerEntities().stream()
                    .filter(s -> id.equals(s.getId()))
                    .findFirst()
                    .orElse(null);
        }
        return server != null
                ? ResponseEntity.ok(serveurEntityMapper.toDto(server))
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GamingServerDto> createServer(@RequestBody GamingServerDto dto) {
        GamingServerEntity entity = serveurEntityMapper.toEntity(dto);
        gamingServerService.createGamingServer(entity);
        return ResponseEntity.status(201).body(serveurEntityMapper.toDto(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GamingServerDto> updateServer(
            @PathVariable String id,
            @RequestBody GamingServerDto dto
    ) {
        GamingServerEntity entity = serveurEntityMapper.toEntity(dto);
        entity.setId(id);
        entity.setIdentifier(dto.identifier() != null ? dto.identifier() : id);
        gamingServerService.updateGamingServer(entity);
        return ResponseEntity.ok(serveurEntityMapper.toDto(entity));
    }
}


