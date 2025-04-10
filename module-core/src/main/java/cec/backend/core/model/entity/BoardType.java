package cec.backend.core.model.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    NOTICE("공지사항"),
    COMMUNITY("커뮤니티"),
    INQUIRY("문의사항");

    private final String description;
} 