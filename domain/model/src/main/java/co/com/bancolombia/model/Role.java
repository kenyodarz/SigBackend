package co.com.bancolombia.model;

import co.com.bancolombia.model.integrations.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {
    private String id;
    private ERole name;
}
