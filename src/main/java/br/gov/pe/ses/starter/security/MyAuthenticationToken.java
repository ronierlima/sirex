package br.gov.pe.ses.starter.security;

import java.util.Collection;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MyAuthenticationToken extends UsernamePasswordAuthenticationToken{

	private static final long serialVersionUID = 1L;

	public MyAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities){
        super(principal, credentials, authorities);        
    }            
}