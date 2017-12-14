package me.rizaldijs.telepati.server;

import me.rizaldijs.telepati.common.TFQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CachedTFQuizRepository {
    @Autowired
    private TFQuizRepository repository;

    @Cacheable
    public List<TFQuiz> findAll() {
        return repository.findAll();
    }
}
