package me.rizaldijs.telepati.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "quiz")
public class TFQuiz {
    @Id
    private String id;
    private String question;
    private Boolean answer;
}
