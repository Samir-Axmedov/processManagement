package lk.custom_process_management.asset.vezzal.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.custom_process_management.asset.vezzal_arrival_history.entity.VezzalArrivalHistory;
import lk.custom_process_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Vezzal" )
public class Vezzal extends AuditEntity {

    @Size( min = 5, message = "Your name cannot be accepted" )
    private String name;

    // ex. {yearLastTwo}{less than ten thousand}
    @Column( unique = true )
    private String code;

    //International Maritime Organization NUmber
    @Column( unique = true )
    private String imoNumber;

    //Maritime Mobile Service Identities
    @Column( unique = true )
    private String mmsiNumber;

    private String country;

    @OneToMany(mappedBy = "vezzal")
    private List< VezzalArrivalHistory > vezzalArrivalHistories;
}