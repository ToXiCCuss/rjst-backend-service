package de.rjst.rjstbackendservice.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
public class RoleDto implements GrantedAuthority {

    private String authority;
    @Override
    public final String getAuthority() {
        return authority;
    }
}
