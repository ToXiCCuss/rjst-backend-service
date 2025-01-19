package de.rjst.rjstbackendservice.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Data
public class Role implements GrantedAuthority {

    private String authority;

}
