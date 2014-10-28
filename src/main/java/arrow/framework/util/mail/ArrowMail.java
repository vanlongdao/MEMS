package arrow.framework.util.mail;

public class ArrowMail {
  /**
  *
  */

 public ArrowMail() {

 }

 public ArrowMail(final String to, final String subject, final String content) {
   this.to = to;
   this.subject = subject;
   this.content = content;
 }

 private String subject;
 private String to;
 // private String cc;
 // private String bcc;
 private String content;

 public String getSubject() {
   return this.subject;
 }

 public void setSubject(final String subject) {
   this.subject = subject;
 }

 public String getTo() {
   return this.to;
 }

 public void setTo(final String to) {
   this.to = to;
 }

 // public String getCc()
 // {
 // return this.cc;
 // }
 //
 // public void setCc(final String cc)
 // {
 // this.cc = cc;
 // }
 //
 // public String getBcc()
 // {
 // return this.bcc;
 // }
 //
 // public void setBcc(final String bcc)
 // {
 // this.bcc = bcc;
 // }

 public String getContent() {
   return this.content;
 }

 public void setContent(final String content) {
   this.content = content;
 }
}
