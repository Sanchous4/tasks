package java;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.Date;

@Builder
@Getter
public class LogMessage {
    @NonNull
    private final String message;

    private final String timestamp = new Date().toString();
}
