package com.quantox.api.v1.dto;

import com.quantox.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MaschineDto {

    private String uid;
    private String createdBy;
    private Status status;
    private LocalDateTime createAt;
    private Boolean active;


}
