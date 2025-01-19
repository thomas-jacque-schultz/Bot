package schultz.thomas.discord.bot.business.service.tools;

import schultz.thomas.discord.bot.model.entity.ServerEntity;

import java.io.Console;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameServerParser {

    private static final Pattern regex = Pattern.compile("#(.*?)# \\[(.*?)\\]"); // Compilé une seule fois

    public static ServerEntity parseServer(String message) {
        ServerEntity serverEntity = new ServerEntity();
        Matcher matcher = regex.matcher(message);

        while (matcher.find()) {
            System.out.println(matcher.group(1) + " : " + matcher.group(2));
            if (matcher.group(1).contains("name")) {
                serverEntity.setName(matcher.group(2));
            } else if (matcher.group(1).contains("url")) {
                serverEntity.setConnexionString(matcher.group(2));
            } else if (matcher.group(1).contains("playerCount")) {
                serverEntity.setPlayersMax(Integer.parseInt(matcher.group(2)));
            } else if (matcher.group(1).contains("mods")) {
                serverEntity.setInstallationDetails(matcher.group(2));
            } else if (matcher.group(1).contains("version")) {
                serverEntity.setVersion(matcher.group(2));
            } else if (matcher.group(1).contains("regles")) {
                serverEntity.setDescription(matcher.group(2));
            } else if (matcher.group(1).contains("admin")) {
                serverEntity.setAllowedAuthors(Arrays.stream(matcher.group(2).split("\\|")).map(String::trim).toList());
            } else if (matcher.group(1).contains("identifiant")) {
                serverEntity.setIdentifier(matcher.group(2));
            }
        }

        return serverEntity;
    }
}
