package arrow.mems.rest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestAuthStorage implements Serializable {
  Map<Integer, String> mapUserIdAndToken;

  @PostConstruct
  public void init() {
    this.mapUserIdAndToken = new HashMap<>();
  }

  public void put(int pUserID, String pFormat) {
    this.mapUserIdAndToken.put(pUserID, pFormat);
  }

  public void remove(int userId) {
    this.mapUserIdAndToken.remove(userId);
  }

  public boolean contains(Integer pUserId) {
    return this.mapUserIdAndToken.containsKey(pUserId);
  }

  public String get(Integer pUserId) {
    return this.mapUserIdAndToken.get(pUserId);
  }
}
