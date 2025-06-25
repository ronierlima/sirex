package br.gov.pe.ses.starter.service.implementacoes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UsuarioSistema;
import br.gov.pe.ses.starter.service.interfaces.UsuarioLogadoService;

@Service
public class UsuarioLogadoServiceImpl implements UsuarioLogadoService,Serializable {
    
	private static final long serialVersionUID = 1L;
	
	@Autowired
    private SessionRegistry sessionRegistry;
	protected List<Usuario> usuariosLogados;
	protected List<Object> principals;
	
	@Override
	public List<Usuario> getUsuariosLogados() {
        
		usuariosLogados = new ArrayList<>();
        principals = sessionRegistry.getAllPrincipals();
        
        //removendo sessões finalizadas
        principals.stream().filter(p->!sessionRegistry.getAllSessions(p, false).isEmpty()).map(Object::toString).collect(Collectors.toList());
        
        for (Object principal : principals) {        	
        	if (principal instanceof UsuarioSistema userDetails) {

                if (userDetails.getUsuario().getId() != null) {
                    usuariosLogados.add(userDetails.getUsuario());
                }
            }        
        }
        return usuariosLogados;
    }
	
	@Override
	public void invalidarSessaoDoUsuario(Usuario u) {
		for (Object principal : principals) {
			UserDetails userDetails = (UserDetails) principal;
			if (userDetails.getUsername().equals(u.getLogin())) {
				for (SessionInformation sessionInformation : sessionRegistry.getAllSessions(principal, false)) {
					sessionInformation.expireNow();
				}
			}
		}
	}
}

