package me.rizaldijs.telepati.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TFQuizAnswer {
    private String user;
    private Boolean answer;
}
