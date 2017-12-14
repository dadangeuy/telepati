package me.rizaldijs.telepati.server;

import me.rizaldijs.telepati.common.TFQuiz;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TFQuizRepository extends MongoRepository<TFQuiz, String> {
}
