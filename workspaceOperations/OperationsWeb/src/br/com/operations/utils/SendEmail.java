package br.com.operations.utils;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.mail.PasswordAuthentication;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import br.com.operations.controller.LoginBBean;
import br.com.operations.entity.TbSysLogEmailSend;
import br.com.operations.entity.TbSysUser;
import br.com.operations.entity.TbTaxQuote;
import br.com.operations.entity.TbTaxQuoteItem;
import br.com.operations.facade.impl.LogEmailSendFacadeImpl;

@Stateless
public class SendEmail {
	
	private final String EMAIL_REMETENTE = "no-reply@oracle.com"; //"teste.operations@gmail.com";
	private final String EMAIL_AUTENTICATION = "taxnfe_br@oracle.com";
	//private final String PROTOCOLO_SMTP = "smtp.gmail.com";
	private final String SERVIDOR_IMAP = "stbeehive.oracle.com";
	private final String PASSWORD = "TAXsp2011"; //"t3st3.5ab0!a";
	private final String PORTA_SSL = "465";
	private final String EMAIL_LOG = "log.operations@gmail.com";
	
	Locale locale;
	
	ResourceBundle bundle;
	
	LoginBBean login;
	
	private TbSysUser userLogged;
	
	@EJB
	private LogEmailSendFacadeImpl emailSendFacade;
	

	public void saveLogEmail(String BodyEmail, String mailSalesRep, List<String> mails, String subjec, String type){
		
		TbSysLogEmailSend logEmailSend = new TbSysLogEmailSend();
		
		if(userLogged != null && userLogged.getId() > 0){
			logEmailSend.setTbSysUser(userLogged);
		}
		
		logEmailSend.setSBodyEmail(BodyEmail);
		logEmailSend.setSSubject(subjec);
		
		
		if(type != null && !type.equals("PASSWORD")){
			logEmailSend.setSEmailAccountSend(mailSalesRep);
		
			String emails = "";
			
			for(int i = 0; i < mails.size(); i++){
				emails += mails.get(i)+"; ";
			}
			
			logEmailSend.setSEmailAccountCc(emails);
		
		}else{
		
			logEmailSend.setSEmailAccountSend(mails.get(0));
		}
		
		logEmailSend.setDtSend(new Date());
		
		try{
			
			emailSendFacade.save(logEmailSend);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void sendSimpleEmail(String txtMsg, String mail, String name, String subject) throws EmailException{
		
		locale = (new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());;
		
		login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged  = login.getUser();
		
		Email email = new SimpleEmail();
//		email.setSmtpPort(Integer.parseInt(PORTA_SSL));
		email.setAuthenticator( new javax.mail.Authenticator() {
          protected PasswordAuthentication getPasswordAuthentication() {
        	  return new PasswordAuthentication(EMAIL_AUTENTICATION,PASSWORD);
		  }
		});
		
		email.setDebug(true);
		email.setHostName(SERVIDOR_IMAP);
		
		email.getMailSession().getProperties().put("mail.debug", "true");
		email.getMailSession().getProperties().put("mail.smtp.host", "stbeehive.oracle.com");
		email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
		email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		email.getMailSession().getProperties().put("mail.smtp.auth", "true");
		email.getMailSession().getProperties().put("mail.smtp.port", "465");
				
		email.setFrom(EMAIL_REMETENTE, "Operations");
		email.setSubject(subject);
		email.setMsg(txtMsg);
		email.addTo(mail, name);
		email.send();
		
	}
	
	
	public void sendHtmlEmail(String txtMsg, String mailSalesRep, List<String> mails, String subject, TbTaxQuote quote, String type) throws EmailException, MalformedURLException{
		
		locale = (new Locale((String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("language"),
				(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("country")));
		
		bundle = ResourceBundle.getBundle("br.com.operations.bundle.messages", Locale.getDefault());;
		
		login = (LoginBBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("loginBBean");
		
		userLogged  = login.getUser();
		
		HtmlEmail email = new HtmlEmail();
		
//		email.setSmtpPort(587);
		email.setAuthenticator( new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	        	  return new PasswordAuthentication(EMAIL_AUTENTICATION,PASSWORD);
			  }
		});
		email.setDebug(true);
		email.setHostName(SERVIDOR_IMAP);
		
		email.getMailSession().getProperties().put("mail.debug", "true");
		email.getMailSession().getProperties().put("mail.smtp.host", "stbeehive.oracle.com");
		email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", "465");
		email.getMailSession().getProperties().put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		email.getMailSession().getProperties().put("mail.smtp.auth", "true");
		email.getMailSession().getProperties().put("mail.smtp.port", "465");
		
		email.setFrom(EMAIL_REMETENTE, "Operations");
		email.setSubject(subject);
		//email.setMsg(txtMsg);
		
		for(int i = 0; i < mails.size(); i++){
			if(i == 0){
				email.addTo(mails.get(i));
			}else{
				email.addCc(mails.get(i));
			}
		}
		
		email.addBcc(EMAIL_LOG);
		
		if(mailSalesRep != null){
			email.addCc(mailSalesRep);
		}
		
		String bodyMail = "";
		
		if(quote != null){
			bodyMail = montaHtmlEmail(quote);
		}else if(type.equals("PASSWORD")){
			bodyMail = montaHtmlEmailRemenberPass(txtMsg);
		}
		
		email.setHtmlMsg(bodyMail);
		
		email.send();
		
		saveLogEmail(bodyMail, mailSalesRep, mails, subject, type);
		
	}
	
	
	public String montaHtmlEmailRemenberPass(String msg){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		StringBuilder html = new StringBuilder();
				
		
		html.append("<html>");
				
		html.append("<html>");
		html.append("<head>");
		html.append("</head>");
		html.append("<body>");
		html.append("<div align='center'>");
	    html.append("<table border='1' cellspacing='0' cellpadding='0'");
	    html.append("style='width: 1000px; border: solid #cccccc 1.0pt'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td width='100%'");
		html.append("style='width: 100%; border: none; background: #cad7da; padding: 0in 0in 0in 0in'>");
		html.append("<div align='center'>");
		html.append("<table border='0' cellspacing='0' cellpadding='0' width='100%'");
		html.append("style='width: 100.0%'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td width='83%' style='width: 83.0%; padding: 0in 0in 0in 0in'>");
		html.append("<p style='line-height: 19.5pt'>");
		html.append("<span style='margin-left: 5px; font-size: 16.0pt; font-family: &amp; amp; quot; Arial &amp;amp; quot; ,&amp; amp; quot; sans-serif &amp;amp; quot;; color: #3f4e51'>");
		html.append("&nbsp;");
		html.append("</span>"); 											
		html.append("</p>");
		html.append("</td>");
		html.append("<td width='17%' style='width: 17.0%; padding: 0in 22.5pt 7.5pt 0in'>");
		html.append("<p >");
		//TODO: caminho imagem
		html.append("<img style='margin-right: -18px; float: right;' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/logo.png?ln=images' alt='Oracle'>"); 
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</div>");
		html.append("</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td style='width: 100%; height=30px; width: 100%; border: none; border-top: solid white 1.0pt; padding: 0in 0in 0in 0in'>");
		html.append("<p >");
		html.append("<img width='100%' style='width: 100%' height='52' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/emailBordaTop.jpg?ln=images'>");
		html.append("<span style='font-size: 12.0pt; color: black'> <u></u> <u></u>");
		html.append("</span>");
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td valign='top' style='border: none; background: white; padding: 0in 0in 0in 0in'>");
		html.append("<table	border='0' cellspacing='0' cellpadding='0' width='100%' style='width: 100.0%'>");
		html.append("<tbody>");
		html.append("<tr style='height: 96.25pt'>");
		html.append("<td style='padding: 0in 22.5pt 22.5pt 22.5pt; height: 96.25pt'>");
		html.append("<p>");
		html.append("<span style='font-size: 20.0pt; font-family: &amp; amp; quot; Arial &amp;amp; quot; ,&amp; amp; quot; sans-serif &amp;amp; quot;; color: #0d0d0d'>");
		html.append("Operations");
		html.append("</span>");										
		html.append("</p>");
		html.append("<p>");
		//TODO: aqui acrescenta o texto
		html.append("<span style='font-size: 12.0pt; color: windowtext'>"+bundle.getString("mailMessage")+"<br><br>");
		html.append(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/pages/public/recovery.xhtml?idUser="+msg+"</span><br><br>");
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");
				
		html.append("<tr>");
		html.append("<td valign='top' style='width: 100%; border: none; border-bottom: solid #b3b3b3 1.0pt; background: #ebebeb; padding: 0in 0in 15.0pt 0in'>");
		html.append("<p style='width: 100%'>");
		html.append("<img border='0' style='width: 100%' width='100%' height='30' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/emailBordaBottom.jpg?ln=images'>");
		html.append("<span style='color: black'><u></u><u></u></span>");
		html.append("</p>");
		html.append("<table border='0' cellspacing='0' cellpadding='0' style='width: 100%'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td valign='right' style='width: 83%; padding: 0in 22.5pt 22.5pt 22.5pt'>");
		html.append("<p style='float: right;'>");
		html.append("</p>");
		html.append("</td>");
		html.append("<td valign='right' style='width: 17%; padding: 0in 22.5pt 22.5pt 22.5pt'>");
		html.append("<p style='float: right;'>");
		//TODO: caminho da imagem
		html.append("<img border='0' width='80' height='18' style='float: right;' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/operations.png?ln=images'>");
		html.append("<u></u><u></u>");
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");							
		html.append("</tbody>");
		html.append("</table>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</div>");
		html.append("</body>");
		
		html.append("</html>");	
		
		
		
		return html.toString();
	}
	
	
	public String montaHtmlEmail(TbTaxQuote quote){
		
		SimpleDateFormat sdf = new SimpleDateFormat(bundle.getString("pattern_date"));
		
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		
		StringBuilder html = new StringBuilder();		
		
		html.append("<html>");
		html.append("<head>");
		html.append("</head>");
		html.append("<body>");
		html.append("<div align='center'>");
	    html.append("<table border='1' cellspacing='0' cellpadding='0'");
	    html.append("style='width: 1000px; border: solid #cccccc 1.0pt'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td width='100%'");
		html.append("style='width: 100%; border: none; background: #cad7da; padding: 0in 0in 0in 0in'>");
		html.append("<div align='center'>");
		html.append("<table border='0' cellspacing='0' cellpadding='0' width='100%'");
		html.append("style='width: 100.0%'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td width='73%' style='width: 83.0%; padding: 0in 0in 0in 0in'>");
		html.append("<p style='line-height: 19.5pt'>");
		html.append("<span style='margin-left: 5px; font-size: 16.0pt; font-family: &amp; amp; quot; Arial &amp;amp; quot; ,&amp; amp; quot; sans-serif &amp;amp; quot;; color: #3f4e51'>");
		html.append("&nbsp;");
		html.append("</span>"); 											
		html.append("</p>");
		html.append("</td>");
		html.append("<td width='27%' style='width: 17.0%; padding: 0in 22.5pt 7.5pt 0in'>");
		html.append("<p >");
		//TODO: caminho imagem
		html.append("<img style='margin-top: 3px; margin-right: -18px; float: right;' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/logo.png?ln=images' alt='Oracle'>"); 
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</div>");
		html.append("</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td style='width: 100%; height=30px; width: 100%; border: none; border-top: solid white 1.0pt; padding: 0in 0in 0in 0in'>");
		html.append("<p >");
		html.append("<img width='100%' style='width: 100%' height='52' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/emailBordaTop.jpg?ln=images'>");
		html.append("<span style='font-size: 12.0pt; color: black'> <u></u> <u></u>");
		html.append("</span>");
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td valign='top' style='border: none; background: white; padding: 0in 0in 0in 0in'>");
		html.append("&nbsp;");
		html.append("</td>");
		html.append("</tr>");
		
		html.append("<tr>");
		html.append("<td>");
		
		html.append("<table border='1' celpadding='0' celspancing='0' style='width: 100%;'>");
		html.append("<thead>");
		html.append("<tr>");
		html.append("<th colspan='4' style='font-size: 20px; background-color: #F6F6F6; font-weight: bold; color: black;'>");
		html.append("Notification: Material without tax classification");
		html.append("</th>");
		html.append("</tr>");
		html.append("</thead>");
		html.append("<tbody>");
		html.append("<td style='font-weight: bold; color: black; font-size: 16px;'>Quote #</td>");
		html.append("<td>"+ quote.getsQuoteNumber() +"</td>");
		html.append("<td style='font-weight: bold; color: black; font-size: 16px;'> Quote Name </td>");
		html.append("<td>" + quote.getsQuoteName() + "</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td style='font-weight: bold; color: black; font-size: 16px;'> DT Creation </td>");
		html.append("<td colspan='3'> "+ sdf.format(quote.getDtCreation()) +" </td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td style='font-weight: bold; color: black; font-size: 16px;'> Customer </td>");
		html.append("<td colspan='3'>" + quote.getsCustomer()  + " </td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		
		html.append("<table border='1' celpadding='0' celspancing='0' style='width: 100%;'>");
		html.append("<thead>");
		html.append("<tr>");
		html.append("<th>#</th>");
		html.append("<th>"+ bundle.getString("label_ncm") +"</th>");
		html.append("<th>"+ bundle.getString("label_type") +"</th>");
		html.append("<th>"+ bundle.getString("label_model_code") +"</th>");
		html.append("<th>"+ bundle.getString("label_description") +"</th>");
		html.append("<th>"+ bundle.getString("label_qty") +"</th>");
		html.append("</thead>");
		html.append("<tbody>");
		
		for(TbTaxQuoteItem taxQuoteItem: quote.getTbTaxQuoteItems()){
			
			html.append("<tr>");
			html.append("<td style='"+ taxQuoteItem.getStyle() +"'>"+ taxQuoteItem.getsOrdem() +"</td>");
			
			if(taxQuoteItem.getsNcm() == null){
				html.append("<td style='"+ taxQuoteItem.getStyle() +"'>  </td>");
			}else{
				html.append("<td style='"+ taxQuoteItem.getStyle() +"'>" + taxQuoteItem.getsNcm() + "</td>");
			}
			
			if(taxQuoteItem.getTbAdmMaterial().getTbAdmMaterialClass() == null){
				html.append("<td style='"+ taxQuoteItem.getStyle() +"'> </td>");
			}else if(taxQuoteItem.getTbAdmMaterial().getTbAdmMaterialClass().getTbAdmProductType() != null){
				try{
					html.append("<td style='"+ taxQuoteItem.getStyle() +"'> "+ taxQuoteItem.getTbAdmMaterial().getTbAdmMaterialClass().getTbAdmProductType().getsLabel() +" </td>");
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				html.append("<td style='"+ taxQuoteItem.getStyle() +"'> </td>");
			}
			
			html.append("<td style='"+ taxQuoteItem.getStyle() +"'>"+ taxQuoteItem.getsModel() +"</td>");
			html.append("<td style='"+ taxQuoteItem.getStyle() +"'>"+ taxQuoteItem.getsImpDescription() +"</td>");
			html.append("<td style='"+ taxQuoteItem.getStyle() +"'>"+ taxQuoteItem.getrQty().intValue() +"</td>");
						
			
			html.append("</tr>");
		}
		
		html.append("</tbody>");
		html.append("</table>");

		html.append("</td>");
		html.append("</tr>");
		html.append("<tr>");
		html.append("<td valign='top' style='width: 100%; border: none; border-bottom: solid #b3b3b3 1.0pt; background: #ebebeb; padding: 0in 0in 15.0pt 0in'>");
		html.append("<p style='width: 100%'>");
		html.append("<img border='0' style='width: 100%' width='100%' height='30' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/emailBordaBottom.jpg?ln=images'>");
		html.append("<span style='color: black'><u></u><u></u></span>");
		html.append("</p>");
		html.append("<table border='0' cellspacing='0' cellpadding='0' style='width: 100%'>");
		html.append("<tbody>");
		html.append("<tr>");
		html.append("<td valign='right' style='width: 83%; padding: 0in 22.5pt 22.5pt 22.5pt'>");
		html.append("<p style='float: right;'>");
		html.append("</p>");
		html.append("</td>");
		html.append("<td valign='right' style='width: 17%; padding: 0in 22.5pt 22.5pt 22.5pt'>");
		html.append("<p style='float: right;'>");
		//TODO: caminho da imagem
		html.append("<img border='0' width='80' height='18' style='float: right;' src='"+request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/faces/javax.faces.resource/operations.png?ln=images'>");
		html.append("<u></u><u></u>");
		html.append("</p>");
		html.append("</td>");
		html.append("</tr>");							
		html.append("</tbody>");
		html.append("</table>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</td>");
		html.append("</tr>");
		html.append("</tbody>");
		html.append("</table>");
		html.append("</div>");
		html.append("</body>");
		
		html.append("</html>");		
		
		return html.toString();
	}
	
	
	public static void main(String[] args) {
		
		SendEmail sendEmail = new SendEmail();
		
		TbTaxQuote quote = new TbTaxQuote();
		quote.setDtCreation(new Date());
		quote.setsCustomer("LALALALA");
		quote.setsQuoteNumber("lalalalal");
		quote.setsQuoteName("LALALALA");
		quote.setTbTaxQuoteItems(new ArrayList<TbTaxQuoteItem>());
		
		List<String> list = new ArrayList<>();
		list.add("fernando@operations.com.br");
		
		try {
			sendEmail.sendHtmlEmail("TESTE DE EMAIL ENVIADO VIA CODIGO JAVA "+ new Date(),"fernando@operations.com.br", list, "Material Not Classified", quote , "QUOTE");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
/**
* Using gmail
*/
//email.getMailSession().getProperties().put("mail.smtps.auth", true);
//email.getMailSession().getProperties().put("mail.debug", "true");
//email.getMailSession().getProperties().put("mail.smtps.port", "587");
//email.getMailSession().getProperties().put("mail.smtps.socketFactory.port", "587");
//email.getMailSession().getProperties().put("mail.smtps.socketFactory.class",   "javax.net.ssl.SSLSocketFactory");
//email.getMailSession().getProperties().put("mail.smtps.socketFactory.fallback", "false");
//email.getMailSession().getProperties().put("mail.smtp.starttls.enable", true);
//email.getMailSession().getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");
/**
* 
*/
		
/**
 * Using account oracle		
 */
//		Properties props = new Properties();
//      props.put("mail.debug", "true");
//      props.put("mail.smtp.host", "stbeehive.oracle.com");
//      props.put("mail.smtp.socketFactory.port", "465");
//      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//      props.put("mail.smtp.auth", "true");
//      props.put("mail.smtp.port", "465");
//
//      Session session = Session.getDefaultInstance(props,
//              new javax.mail.Authenticator() {
//                      protected PasswordAuthentication getPasswordAuthentication() {
//                              return new PasswordAuthentication("taxnfe_br@oracle.com","TAXsp2011");
//                      }
//              });
//
//      try {
//
//              Message message = new MimeMessage(session);
//              message.setFrom(new InternetAddress("taxnfe_br@oracle.com"));
//              message.setRecipients(Message.RecipientType.TO,
//                              InternetAddress.parse("fernando@operations.com.br"));
//              message.setSubject("Testing Subject");
//              message.setText("Dear Mail Crawler," +
//                              "\n\n No spam to my email, please!");
//
//              Transport.send(message);
//              System.out.println("Done Mail sent"); 
//      } catch (MessagingException e) {
//              throw new RuntimeException(e);
//      }
/**
 * 		 
 */
//	}
	
}
