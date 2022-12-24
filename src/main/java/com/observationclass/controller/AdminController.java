package com.observationclass.controller;

import com.observationclass.common.Constants;
import com.observationclass.model.ApiResponse;
import com.observationclass.model.request.AccountRequest;
import com.observationclass.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @GetMapping("/download-data")
    public ResponseEntity<?> downloadCampus() throws IOException {
        String fileName = "class_observation.xlsx";
        InputStreamResource file = new InputStreamResource(adminService.exportCampus());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/upload-campus")
    public ResponseEntity<ApiResponse> uploadCampus(@RequestParam("file") MultipartFile file) throws IOException {
        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if(!file.getContentType().equals(excelContentType)){
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, "Content type is invalid", null));
        }
        return ResponseEntity.ok().body(adminService.uploadCampus(file));
    }

    @PostMapping("/upload-semester")
    public ResponseEntity<ApiResponse> uploadSemester(@RequestParam("file") MultipartFile file) throws IOException {
        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if(!file.getContentType().equals(excelContentType)){
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, "Content type is invalid", null));
        }
        return ResponseEntity.ok().body(adminService.uploadSemester(file));
    }

    @PostMapping("/upload-subject")
    public ResponseEntity<ApiResponse> uploadSubject(@RequestParam("file") MultipartFile file) throws IOException {
        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if(!file.getContentType().equals(excelContentType)){
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, "Content type is invalid", null));
        }
        return ResponseEntity.ok().body(adminService.uploadSubject(file));
    }

    @PostMapping("/upload-room")
    public ResponseEntity<ApiResponse> uploadRoom(@RequestParam("file") MultipartFile file) throws IOException {
        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if(!file.getContentType().equals(excelContentType)){
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, "Content type is invalid", null));
        }
        return ResponseEntity.ok().body(adminService.uploadRoom(file));
    }

    @PostMapping("/upload-slot")
    public ResponseEntity<ApiResponse> uploadSlot(@RequestParam("file") MultipartFile file) throws IOException {
        String excelContentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        if(!file.getContentType().equals(excelContentType)){
            return ResponseEntity.ok().body(new ApiResponse(Constants.HTTP_CODE_400, "Content type is invalid", null));
        }
        return ResponseEntity.ok().body(adminService.uploadSlot(file));
    }

    @GetMapping("/list-all")
    public ResponseEntity<ApiResponse> getListAdmin() {
        return ResponseEntity.ok().body(adminService.getListAccount());
    }

    @PostMapping("/new-account")
    public ResponseEntity<ApiResponse> addNewAccount(@RequestBody @Valid AccountRequest accountRequest) {
        return ResponseEntity.ok().body(adminService.addNewAccount(accountRequest));
    }

    @PostMapping("/edit-account")
    public ResponseEntity<ApiResponse> editAccount(@RequestBody @Valid AccountRequest accountRequest) {
        return ResponseEntity.ok().body(adminService.updateAccount(accountRequest));
    }

    @PostMapping("/delete-account")
    public ResponseEntity<ApiResponse> deleteAccount(@RequestParam(name = "id") Integer id) {
        return ResponseEntity.ok().body(adminService.deleteAccountById(id));
    }

    @GetMapping("/list-account-role")
    public ResponseEntity<ApiResponse> listAccountByRole(@RequestParam(name = "roleId") Integer roleId,
                                                         @RequestParam(name = "email") String emailSearch) {
        return ResponseEntity.ok().body(adminService.getAccountByRole(roleId, emailSearch));
    }
}
