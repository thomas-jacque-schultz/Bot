package schultz.thomas.discord.bot.business.service.parser;

import org.mapstruct.Context;
import org.mapstruct.Named;
import schultz.thomas.discord.bot.model.enums.UserPrivilegeEnum;

import java.util.List;
import java.util.Objects;

public interface AbstractMapper {

    @Named("extractStringValue")
    static String extractValue(List<String> lines, @Context String fieldName) {
        String pattern = fieldName + " : \\[(.*?)]";
        return lines.stream()
                .filter(line -> line.startsWith(fieldName))
                .map(line -> extractUsingRegex(line, pattern))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Named("extractIntValue")
    static Integer extractIntValue(List<String> lines, @Context String fieldName) {
        String pattern = fieldName + " : \\[(\\d+)]";
        return lines.stream()
                .filter(line -> line.startsWith(fieldName))
                .map(line -> extractUsingRegex(line, pattern))
                .filter(Objects::nonNull)
                .map(Integer::parseInt)
                .findFirst()
                .orElse(null);
    }

    @Named("extractStringListValue")
    static List<String> extractStringListValue(List<String> lines, @Context String fieldName) {
        String pattern = fieldName + " : \\[(.*?)]";
        return lines.stream()
                .filter(line -> line.startsWith(fieldName))
                .map(line -> extractUsingRegex(line, pattern))
                .filter(Objects::nonNull)
                .map(value -> value.split("\\|"))
                .map(List::of)
                .findFirst()
                .orElse(null);
    }

    @Named("extractUserPrivilegeEnumValue")
    static UserPrivilegeEnum extractUserPrivilegeEnumValue(List<String> lines, @Context String fieldName) {
        String pattern = fieldName + " : \\[(.*?)]";
        return lines.stream()
                .filter(line -> line.startsWith(fieldName))
                .map(line -> extractUsingRegex(line, pattern))
                .filter(Objects::nonNull)
                .map(String::toUpperCase)
                .map(UserPrivilegeEnum::valueOf)
                .findFirst()
                .orElse(null);
    }


    static String extractUsingRegex(String line, String pattern) {
        java.util.regex.Pattern regex = java.util.regex.Pattern.compile(pattern);
        java.util.regex.Matcher matcher = regex.matcher(line);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }
}
