package lk.customsProcessManagement.asset.warehouseBlock.entity;


import lk.customsProcessManagement.asset.vezzalOrder.entity.VezzalOrder;
import lk.customsProcessManagement.asset.warehouseBlock.entity.Enum.WarehouseBlockStatus;
import lk.customsProcessManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WarehouseBlock extends AuditEntity {

    @Size( min = 5, message = "Your name cannot be accepted" )
    private String name;

    @NotEmpty
    private String area;

    @Enumerated( EnumType.STRING)
    private WarehouseBlockStatus warehouseBlockStatus;

    @OneToMany(mappedBy = "warehouseBlock")
    private List< VezzalOrder > vezzalOrders;

}

