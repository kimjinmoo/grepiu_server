package com.grepiu.www.process.grepiu.dao;

import com.grepiu.www.process.grepiu.entity.Slink;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SlinkRepository extends MongoRepository<Slink, String> {
  Optional<Slink> findBySUrl(String sUrl);

  void deleteBySUrl(String sUrl);
}
