package D6B.D_discover_picture.picture.controller;

import D6B.D_discover_picture.common.dto.AuthResponse;
import D6B.D_discover_picture.common.service.AuthorizeService;
import D6B.D_discover_picture.picture.controller.dto.DeleteUserRequest;
import D6B.D_discover_picture.picture.controller.dto.PictureAllDetailResponse;
import D6B.D_discover_picture.picture.controller.dto.PictureDetailResponse;
import D6B.D_discover_picture.picture.controller.dto.PictureSaveRequest;
import D6B.D_discover_picture.picture.domain.*;
import D6B.D_discover_picture.picture.service.PictureService;
import D6B.D_discover_picture.picture.service.exceptions.DeletePictureFailException;
import D6B.D_discover_picture.picture.service.exceptions.PictureNotSavedException;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/picture")
public class PictureController {
    private final PictureRepository pictureRepository;
    private final PictureTagRepository pictureTagRepository;
    private final TagRepository tagRepository;
    private final PictureService pictureService;
    private final AuthorizeService authorizeService;

    @Autowired
    public PictureController(PictureRepository pictureRepository, PictureTagRepository pictureTagRepository,
                             TagRepository tagRepository, PictureService pictureService, AuthorizeService authorizeService) {
        this.pictureRepository = pictureRepository;
        this.pictureTagRepository = pictureTagRepository;
        this.tagRepository = tagRepository;
        this.pictureService = pictureService;
        this.authorizeService = authorizeService;
    }

    /*
    * 이미지 결과물 저장
    * @param pictureSaveRequest 이미지 저장에 필요한 정보 요청
    * @return 성공 여부
    * */
    @PostMapping("")
    public ResponseEntity<Boolean> savePicture(
            @RequestHeader("Authorization") String idToken,
            @RequestBody PictureSaveRequest pictureSaveRequest) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, pictureSaveRequest.getUid());
        if (authResponse.getIsUser()) {
            try {
                pictureService.savePicture(pictureSaveRequest);
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } catch (PictureNotSavedException e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // 이미지 삭제
    @DeleteMapping("/{picture_id}/{uid}")
    public ResponseEntity<Object> deletePicture(
            @RequestHeader("Authorization") String idToken,
            @PathVariable String uid,
            @PathVariable Long picture_id) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if (authResponse.getIsUser()) {
            try {
                pictureService.deletePicture(picture_id, uid);
                return ResponseEntity.ok().build();
            } catch (DeletePictureFailException e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    // Today's pick (로그인 유저 버전)
    @GetMapping("/today_list/user/{uid}")
    public ResponseEntity<List<PictureAllDetailResponse>> getTodayPickWithLogin(
            @RequestHeader("Authorization") String idToken,
            @PathVariable String uid) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if (authResponse.getIsUser()) {
            try {
                List<PictureAllDetailResponse> list = pictureService.getTodayPickWithLogin(uid);
                return ResponseEntity.ok(list);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/today_list/no_user")
    public ResponseEntity<List<PictureAllDetailResponse>> getTodayPickWithoutLogin() {
        try {
            List<PictureAllDetailResponse> list = pictureService.getTodayPickWithoutLogin();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    // 본인이 좋아요 한 리스트 보내주기
    @PostMapping("/like_all_list")
    public ResponseEntity<List<PictureDetailResponse>> getLikeAllList(
            @RequestBody List<Long> pictureIds) {
        try {
            List<PictureDetailResponse> list = pictureService.getLikeAllList(pictureIds);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 에러
        }
    }

    // 다른 사람의 좋아요 리스트 보내주기
    @PostMapping("/like_public_list")
    public ResponseEntity<List<PictureDetailResponse>> getLikePublicList(
            @RequestBody List<Long> pictureIds) {
        try {
            List<PictureDetailResponse> list = pictureService.getLikePublicList(pictureIds);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 유저가 만든 이미지들 조회 (로그인 했을때)
    @GetMapping("/made/user/{uid}/{isMe}")
    public ResponseEntity<List<PictureDetailResponse>> getMadePicWithLogin(
            @PathVariable String uid,
            @PathVariable String isMe) {
        try {
            List<PictureDetailResponse> list;
            if (isMe.equals("1")) {
                list = pictureService.getPicMadeByMe(uid);
            } else {
                list = pictureService.getPicMadeByOther(uid);
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 다른 유저가 만든 이미지들 조회 (로그인 안했을때)
    @GetMapping("/made/no_user/{uid}")
    public ResponseEntity<List<PictureDetailResponse>> getMadePicWithoutLogin(
            @PathVariable String uid) {
        try {
            List<PictureDetailResponse> list = pictureService.getPicMadeByOther(uid);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 이미지 좋아요 카운트 올리기
    @PostMapping("/create/count/{pictureId}")
    public ResponseEntity<String> plusLoveCount(
            @PathVariable Long pictureId) {
        try {
            String imgUrl = pictureService.plusCount(pictureId);
            return ResponseEntity.ok(imgUrl);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 좋아요 취소, 카운트 내리기
    @PostMapping("/delete/count/{pictureId}")
    public ResponseEntity<Object> minusLoveCount(
            @PathVariable Long pictureId) {
        try {
            pictureService.minusCount(pictureId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 유저 탈퇴 시
    @PostMapping("/delete/user")
    public ResponseEntity<Object> deleteUser(
            @RequestBody DeleteUserRequest deleteUserRequest) {
        try {
            pictureService.deleteUser(deleteUserRequest);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // 유저가 검색한 검색어를 태그로 가지고 있는 이미지들 반환 (유저 검색 시)
    // 로그인 한 사용자
    @GetMapping("/search/user/{keyword}/{uid}")
    public ResponseEntity<List<PictureAllDetailResponse>> getSearchListWithLogin(
            @RequestHeader("Authorization") String idToken,
            @PathVariable String keyword,
            @PathVariable String uid) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if (authResponse.getIsUser()) {
            try {
                List<PictureAllDetailResponse> list = pictureService.getSearchListWithLogin(uid, keyword);
                return ResponseEntity.ok(list);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/search/no_user/{keyword}")
    public ResponseEntity<List<PictureAllDetailResponse>> getSearchListWithoutLogin(
            @PathVariable String keyword) {
        try {
            List<PictureAllDetailResponse> list = pictureService.getSearchListWithoutLogin(keyword);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
