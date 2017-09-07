package com.vote.api.service;

import org.springframework.stereotype.Service;

@Service
public interface ApiService {
    String getTitle();
    String getType();
    String getVoteKeyAndValue();
    String getSumVote();
}
