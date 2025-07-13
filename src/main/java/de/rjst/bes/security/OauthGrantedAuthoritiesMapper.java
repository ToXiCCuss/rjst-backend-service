package de.rjst.bes.security;

import static de.rjst.bes.security.SpringSecurityConfig.CLAIM;
import static de.rjst.bes.security.SpringSecurityConfig.ROLE_PREFIX;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.stereotype.Component;

@Component
public class OauthGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(final Collection<? extends GrantedAuthority> authorities) {
        final Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
        authorities.forEach(authority -> {
            if (authority instanceof final OidcUserAuthority oidcUserAuthority) {
                final OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();
                final Map<String, Object> claims = userInfo.getClaims();
                if (claims.containsKey(CLAIM)) {
                    final Iterable<String> groups = (List<String>) claims.get(CLAIM);
                    groups.forEach(group -> mappedAuthorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + group.toUpperCase(Locale.ROOT))));
                }
            }
        });

        return mappedAuthorities;
    }
}
