package erp.uniaura.infra.security;

import erp.uniaura.modules.usuario.model.Usuario;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UsuarioDetails implements UserDetails {

    private final Usuario usuario;

    // --- RETORNA AS AUTORIDADES CONCEDIDAS AO USUÁRIO COM BASE EM SEU PERFIL ---
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + usuario.getRole().name()));
    }

    // --- RETORNA A SENHA ARMAZENADA DO USUÁRIO PARA O PROCESSO DE AUTENTICAÇÃO ---
    @Override
    public String getPassword() {
        return usuario.getSenha();
    }

    // --- UTILIZA O E-MAIL DO USUÁRIO COMO IDENTIFICADOR DE AUTENTICAÇÃO ---
    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    // --- INFORMA QUE A CONTA DO USUÁRIO NÃO POSSUI PRAZO DE EXPIRAÇÃO ---
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // --- INFORMA QUE A CONTA DO USUÁRIO NÃO ESTÁ BLOQUEADA ---
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // --- INFORMA QUE AS CREDENCIAIS DO USUÁRIO NÃO ESTÃO EXPIRADAS ---
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // --- INFORMA SE O USUÁRIO ESTÁ ATIVO E HABILITADO PARA AUTENTICAÇÃO ---
    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(usuario.getAtivo());
    }
}

