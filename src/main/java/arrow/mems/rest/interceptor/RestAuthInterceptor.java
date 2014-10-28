package arrow.mems.rest.interceptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;

import arrow.framework.logging.ArrowLogger;
import arrow.mems.rest.RestAuthStorage;

@Provider
public class RestAuthInterceptor implements javax.ws.rs.container.ContainerRequestFilter {

  private static final String OFFICE_CODE = "officeCode";
  private static final String USER_ID_KEY = "userId";
  private static final String TOKEN_KEY = "token";

  public Response buildAccessDenied() {
    return Response.status(Status.UNAUTHORIZED).build();
  }

  @Inject
  ArrowLogger logger;

  @Inject
  RestAuthStorage authStorage;

  private boolean isValidRequest(ContainerRequestContext pRequestContext) {
    return pRequestContext.getHeaders().containsKey(RestAuthInterceptor.TOKEN_KEY) && pRequestContext.getHeaders().containsKey(
        RestAuthInterceptor.USER_ID_KEY) && StringUtils.isNotEmpty(pRequestContext.getHeaderString(RestAuthInterceptor.USER_ID_KEY)) && StringUtils
        .isNotEmpty(pRequestContext.getHeaderString(RestAuthInterceptor.TOKEN_KEY)) && pRequestContext.getHeaders().containsKey(
        RestAuthInterceptor.OFFICE_CODE) && StringUtils.isNotEmpty(pRequestContext.getHeaderString(RestAuthInterceptor.OFFICE_CODE));
  }

  @Override
  public void filter(ContainerRequestContext pRequestContext) throws IOException {
    this.logger.debug("Calling method: " + pRequestContext.getMethod());
    this.logger.debug("Access to Path:" + pRequestContext.getUriInfo().getPath());
    if (pRequestContext.getUriInfo().getPath().endsWith("/login"))
      return;

    // Not login, required Token and UserId
    if (!this.isValidRequest(pRequestContext)) {
      // missing authentication info, redirect to login
      pRequestContext.abortWith(this.buildAccessDenied());
      return;
    }

    final String token = pRequestContext.getHeaderString(RestAuthInterceptor.TOKEN_KEY);
    final Integer userId = Integer.parseInt(pRequestContext.getHeaderString(RestAuthInterceptor.USER_ID_KEY));

    if (!this.authStorage.contains(userId)) {
      pRequestContext.abortWith(this.buildAccessDenied());
      return;
    }

    final String storedToken = this.authStorage.get(userId);
    final String[] parts = storedToken.split("#");
    if (!StringUtils.equals(token, parts[0])) {

      // invalidate userId too.
      this.authStorage.remove(userId);
      pRequestContext.abortWith(this.buildAccessDenied());
      return;
    }

    final DateTimeFormatter formater = DateTimeFormatter.ofPattern("ddMMyyyyHHmmss");
    final LocalDateTime expiredDate = formater.parse(parts[1]).query(LocalDateTime::from);
    if (expiredDate.isBefore(LocalDateTime.now())) {
      // invalidate userId too.
      this.authStorage.remove(userId);
      pRequestContext.abortWith(this.buildAccessDenied());
      return;
    }

    // success, allowed to access
  }
}
