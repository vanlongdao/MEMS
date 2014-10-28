package arrow.mems.rest;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import arrow.framework.faces.messages.Message;
import arrow.framework.helper.AuthenticationData;
import arrow.framework.helper.ServiceResult;
import arrow.framework.logging.ArrowLogger;
import arrow.mems.config.AppConfig;
import arrow.mems.rest.entities.User;
import arrow.mems.service.AuthenticationService;


@Path("/auth")
public class LoginService extends AbstractRestService {
  private static final String DEFAULT_ENCRYPT_ALGOTH = "HmacSHA256";
  private static final String DEFAULT_ENCODING = "UTF8";
  @Inject
  RestAuthStorage authStorage;
  @Inject
  ArrowLogger logger;
  @Inject
  AuthenticationService service;

  private String generateToken(String userKey) throws InvalidKeyException, NoSuchAlgorithmException {
    final Mac sha256_HMAC = Mac.getInstance(LoginService.DEFAULT_ENCRYPT_ALGOTH);
    final SecretKeySpec secret_key =
        new javax.crypto.spec.SecretKeySpec(Charset.forName(LoginService.DEFAULT_ENCODING).encode(AppConfig.API_KEY).array(),
            LoginService.DEFAULT_ENCRYPT_ALGOTH);
    sha256_HMAC.init(secret_key);
    return Hex.encodeHexString(Charset.forName(LoginService.DEFAULT_ENCODING).encode(userKey).array());
  }

  private String embedTokenWithExpiration(String token) {
    final LocalDateTime now = LocalDateTime.now();
    final String expiration = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss").format(now.plusMinutes(30));
    return String.format("%s#%s", token, expiration);
  }

  @Path("/login")
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public Response checkLogin(User user) {
    final RestResult<User> result = this.doAuth(user);
    if (result.isSuccess()) {
      try {
        final String token = this.generateToken(user.getUserAppKey());
        user.setToken(token);

        // userid -> token#expiration.
        this.authStorage.put(user.getUserID(), this.embedTokenWithExpiration(token));

      } catch (final NoSuchAlgorithmException | InvalidKeyException e) {
        this.logger.debug("Error when create token", e);
      }
    } else {

      // Login failed, invalidate user id.
      this.authStorage.remove(user.getUserID());
    }

    return Response.status(Status.OK).entity(result).build();
  }

  @Path("/logout")
  @Consumes(MediaType.APPLICATION_JSON)
  @POST
  public Response logout(User user) {
    this.authStorage.remove(user.getUserID());
    return this.buildResponse(null);
  }

  private RestResult<User> doAuth(User credential) {
    final List<Message> errors = this.validator.validate(credential);
    if (CollectionUtils.isEmpty(errors)) {
      final ServiceResult<AuthenticationData> authResult = this.service.login(credential.getUsername(), credential.getPassword());
      if (authResult.isSuccess()) {
        credential.setAccounttype(authResult.getData().getUserInfo().getAccountType());
        credential.setUserID(authResult.getData().getUserId());
        credential.setOfficeCode(authResult.getData().getUserInfo().getOfficeCode());
        credential.setPsnCode(authResult.getData().getUserInfo().getPsnCode());
        credential.setUserAppKey(authResult.getData().getUserInfo().getUserAppKey());
        credential.setPassword(StringUtils.EMPTY);
        return new RestResult<User>(true, credential);
      } else
        return new RestResult<User>(false, null, authResult.getErrors());
    }
    return new RestResult<User>(false, null, errors);
  }
}
