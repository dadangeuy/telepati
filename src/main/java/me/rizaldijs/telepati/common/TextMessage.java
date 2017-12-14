package me.rizaldijs.telepati.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TextMessage {
    private String sender;
    private String content;
}
