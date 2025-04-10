package cec.backend.user.dto.equipment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentFavoriteToggleResponse {
    private String equipmentId;
    private boolean isBookmarked;
    private String message;
} 