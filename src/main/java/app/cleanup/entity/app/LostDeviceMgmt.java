package app.cleanup.entity.app;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "lost_device_mgmt")
@Data
public class LostDeviceMgmt implements Serializable {
    private static final long serialVersionUID = -9193873433253773828L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    LocalDateTime createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_on")
    LocalDateTime modifiedOn;

    @Column(name = "status")
    private String status;

    @Column(name = "request_id")
    private String requestId;
}
