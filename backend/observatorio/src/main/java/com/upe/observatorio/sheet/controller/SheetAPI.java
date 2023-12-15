package com.upe.observatorio.sheet.controller;

import com.upe.observatorio.sheet.controller.response.StatusExecutionResponse;
import com.upe.observatorio.sheet.service.SheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/sheets")
@CrossOrigin
@RequiredArgsConstructor
public class SheetAPI {
    private final SheetService sheetService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<StatusExecutionResponse>> batchCreateProjects(@RequestPart("sheet") MultipartFile sheet) {
        List<StatusExecutionResponse> statusExec = sheetService.batchCreateProjects(sheet);

        return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(statusExec);
    }
}
