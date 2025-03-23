package com.stream.app.payload;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomMessage {

    private String message;
    private boolean success = Boolean.FALSE;
}
