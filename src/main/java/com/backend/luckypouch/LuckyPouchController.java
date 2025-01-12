package com.backend.luckypouch;

import com.backend.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LuckyPouchController {

    private final LuckyPouchService luckyPouchService;


    /**
     * 복주머니 조회
     */

    @GetMapping("/luckyPouch")
    public ResponseEntity<ResponseDto<?>> getLuckyPouch(@PageableDefault(size = 8, page = 0) Pageable pageable) {
        // 복주머니 조회 로직 호출

        Slice<LuckyPouchResponseDTO> luckyPouches = luckyPouchService.getLuckyPouches(pageable);

        return new ResponseEntity<>(
                new ResponseDto<>("success", "user luckyPouches success", luckyPouches, null, 200),
                HttpStatus.OK);


    }





}
