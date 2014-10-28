package arrow.framework.faces.messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import arrow.framework.logging.ArrowLogger;
import arrow.framework.logging.ArrowLoggerProducer;
import arrow.framework.util.Instance;

@ViewScoped
@Named
public class MessageCodeClientIdMappingManager implements Serializable {
  private static final ArrowLogger log = ArrowLoggerProducer.getLogger();

  private Map<String, Set<Pair<String, Object>>> messageCodeClientIdsMap = new HashMap<String, Set<Pair<String, Object>>>();;

  public MessageCodeClientIdMappingManager() {
    MessageCodeClientIdMappingManager.log
        .debug("============================================ MessageCodeClientIdMappingManager constructor ==========================================");
  }


  @PostConstruct
  public void postConstruct() {
    MessageCodeClientIdMappingManager.log.debug("MessageCodeClientIdMappingManager @PostConstruct");
  }


  // even if we don't want to use this method directly (we use the static method instead)
  // we still must declare it public, or it wouldn't be proxied correctly.
  public void put(final String messageCodes, final String clientId, final Object rowId) {
    if (StringUtils.isNotEmpty(messageCodes)) {
      final String[] codes = messageCodes.split("[, ]");

      for (final String messageCode : codes) {
        if (StringUtils.isNotEmpty(messageCode)) {
          Set<Pair<String, Object>> clientIds = this.messageCodeClientIdsMap.get(messageCode);
          if (clientIds == null) {
            clientIds = new HashSet<Pair<String, Object>>();
            this.messageCodeClientIdsMap.put(messageCode, clientIds);
          }

          final Pair<String, Object> newPair = new ImmutablePair<String, Object>(clientId, rowId);
          if (!clientIds.contains(newPair)) {
            clientIds.add(newPair);
          }
        }
      }
    }
  }

  public Set<Pair<String, Object>> get(final String messageCode) {
    return this.messageCodeClientIdsMap.get(messageCode);
  }


  /**
   * Input value messageCodes is string contains list of error codes separated by comma or
   * whitespace
   * Eg: "messageCode1, messageCode2, ..."
   */
  public static void map(final String messageCodes, final String clientId, final Object rowId) {
    Instance.get(MessageCodeClientIdMappingManager.class).put(messageCodes, clientId, rowId);
  }

  public static Set<Pair<String, Object>> getClientIdsForMessageCode(final String messageCode) {
    return Instance.get(MessageCodeClientIdMappingManager.class).get(messageCode);
  }
}
