package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.entity.RealtimeVote;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RealtimeVoteRepository extends MongoRepository<RealtimeVote, String> {
}
