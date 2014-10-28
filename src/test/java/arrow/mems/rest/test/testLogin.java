package arrow.mems.rest.test;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import arrow.mems.messages.MAUMessages;
import arrow.mems.rest.LoginService;
import arrow.mems.rest.entities.User;

public class testLogin {
  @Inject
  LoginService loginservice;
  User user;
  HttpServletRequest request;
  HttpHeaders header;
  Response result;
  Response response;


  @Test
  public void testUsenameEmpty() {
    this.loginservice = new LoginService();
    this.user = new User();
    this.user.setUsername("");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00002("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }

  @Test
  public void testLengthUsername() {
    this.loginservice = new LoginService();
    this.user = new User();
    // username is too short
    this.user.setUsername("test");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00006("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());

    // username is too long
    this.user.setUsername("ThisIsAJUnitTestFunction");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00006("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }

  @Test
  public void testPasswordEmpty() {
    this.loginservice = new LoginService();
    this.user = new User();
    this.user.setUsername("arrow1");
    this.user.setPassword("");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00007("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }

  @Test
  public void testLengthPassword() {
    this.loginservice = new LoginService();
    this.user = new User();
    this.user.setUsername("arrow1");
    // password is too short (pass < 4 characters)
    this.user.setPassword("123");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00006("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());

    // password is too long (pass > 32 characters)
    this.user.setPassword("111111111111111111111111111111111");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00006("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }

  @Test
  public void testUserNull() {
    this.loginservice = new LoginService();
    this.user = new User();
    this.user.setUsername("InvalidUsername");
    this.user.setPassword("InvalidPassword");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(401).entity(MAUMessages.MAU00010("")).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }

  @Test
  public void testValidUser() {
    this.loginservice = new LoginService();
    this.user = new User();
    this.user.setUsername("arrow1");
    this.user.setPassword("123456");
    this.request = Mockito.mock(HttpServletRequest.class);
    this.header = Mockito.mock(HttpHeaders.class);
    this.result = this.loginservice.checkLogin(this.user);
    this.response = Response.status(200).entity(this.user).build();
    Assert.assertEquals(this.response.getStatus(), this.result.getStatus());
  }
}
