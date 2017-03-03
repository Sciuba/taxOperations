package br.com.saboia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.saboia.entity.TbSysUser;

public class LoginFilter implements Filter {  
  
    private final static String FILTER_APPLIED = "_security_filter_applied";  
  
    public LoginFilter() {  
    }  
  
    @Override  
    public void init(FilterConfig arg0) throws ServletException {  
    }  
  
    @Override  
    public void destroy() {  
    }  
  
    @Override  
    public void doFilter(ServletRequest request, ServletResponse response,  
        FilterChain chain) throws IOException, ServletException {  
        HttpServletRequest hreq = (HttpServletRequest) request;  
        HttpServletResponse hresp = (HttpServletResponse) response;  
        HttpSession session = hreq.getSession();  
  
        hreq.getPathInfo();  
        String paginaAtual = new String(hreq.getRequestURL());  
          
        if ((request.getAttribute(FILTER_APPLIED) == null)   
                && paginaAtual != null   
                && (!paginaAtual.endsWith("login.xhtml") && !paginaAtual.contains("recovery.xhtml"))   
                && (paginaAtual.endsWith(".xhtml"))) {  
            request.setAttribute(FILTER_APPLIED, Boolean.TRUE);  
  
            String user = null;  
            if (((TbSysUser) session.getAttribute("usuarioLogado")) != null) {  
                user = ((TbSysUser) session.getAttribute("usuarioLogado")).getsFullName();  
            }  
  
            if ((user == null) || (user.equals(""))) {  
                hresp.sendRedirect(hreq.getContextPath() + "/faces/pages/public/login.xhtml");  
                return;  
            }  
              
        }  
        // entrega a requisição para o proximo filtro    
        chain.doFilter(request, response);
    }

}
