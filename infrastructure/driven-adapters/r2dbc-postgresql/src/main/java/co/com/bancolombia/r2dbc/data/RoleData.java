package co.com.bancolombia.r2dbc.data;

import co.com.bancolombia.model.integrations.ERole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Table("roles")
public class RoleData {
    @Id
    private String id;
    private ERole name;
}
