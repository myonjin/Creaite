package D6B.D_discover_user.user.controller;

import D6B.D_discover_user.common.dto.AuthResponse;
import D6B.D_discover_user.common.service.AuthorizeService;
import D6B.D_discover_user.user.controller.dto.*;
import D6B.D_discover_user.user.service.UserService;
import com.google.firebase.auth.FirebaseAuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthorizeService authorizeService;

    @Autowired
    public UserController(UserService userService, AuthorizeService authorizeService) {
        this.userService = userService;
        this.authorizeService = authorizeService;
    }

    /**
     * 구글로그인으로 처음 회원이 접속할 때, 회원정보를 우리 DB에 저장하기 위한 Controller
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param userReadRequestDto : (구글로그인 후) 프론트단에서 유저관련 정보를 받을 수 있다.
     */
    @PostMapping("")
    public void enrollUserInformation(@RequestHeader("Authorization") String idToken,
                                                        @RequestBody UserReadRequestDto userReadRequestDto) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, userReadRequestDto.getUid());
        if(authResponse.getIsUser()) {
            userService.enrollUser(authResponse.getDecodedToken());
        } else {
            log.info("없는 회원입니다.");
        }
    }

    /**
     * 유저 본인의 정보를 확인하는 Controller
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param uid : Firebase 통해 얻은 uid
     * @return : 유저의 세부정보를 반환
     * @throws IOException : 에러
     * @throws FirebaseAuthException : 에러
     */
    @GetMapping("/{uid}")
    public ResponseEntity<UserInfoResponseDto> readUserInfo(@RequestHeader("Authorization") String idToken,
                                                            @PathVariable String uid) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if(authResponse.getIsUser()) {
            return ResponseEntity.ok(UserInfoResponseDto
                    .from(userService.findUserByUid(authResponse.getDecodedToken().getUid())));
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    /**
     * 유저의 정보를 변경하는 Controller(구글에서 안오는 정보 : 나이, 성별)
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param userUpdateRequestDto : 변경 혹은 추가할 정보
     * @return : 추가된 정보가 추가된 유저 정보 반환
     * @throws IOException : 에러
     * @throws FirebaseAuthException : 에러
     */
    @PutMapping("")
    public ResponseEntity<UserInfoResponseDto> updateUserInfos(@RequestHeader("Authorization") String idToken,
                                                               @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, userUpdateRequestDto.getUid());
        if(authResponse.getIsUser()) {
            return ResponseEntity.ok(UserInfoResponseDto
                    .from(userService.updateUserInfos(authResponse.getDecodedToken(), userUpdateRequestDto)));
        } else return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // s3에 저장 후, img_url을 반환한다.
    @PutMapping("/image")
    public String uploadImage(@RequestParam("image") MultipartFile image) {
        // 업데이트
        return userService.updateUserImg(image);
    }


    /**
     * 회원탈퇴
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param uid : Firebase 통해 얻은 uid
     * @return : 삭제 여부를 판단해서 반환
     * @throws IOException : 에러
     * @throws FirebaseAuthException : 에러
     */
    @DeleteMapping("/{uid}")
    public ResponseEntity<Object> deleteUserInfo(@RequestHeader("Authorization") String idToken,
                                                 @PathVariable String uid) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if(authResponse.getIsUser()) {
            userService.deleteUserInfo(authResponse.getDecodedToken());
            return ResponseEntity.ok().build();
        } else return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    /**
     * 좋아요
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param loveToggleRequestDto : 좋아요
     * @throws IOException : 에러
     * @throws FirebaseAuthException : 에러
     */
    @PostMapping("/like")
    public void toggleLove(@RequestHeader("Authorization") String idToken,
                                              @RequestBody LoveToggleRequestDto loveToggleRequestDto) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, loveToggleRequestDto.getUid());
        if(authResponse.getIsUser()) userService.toggleLove(loveToggleRequestDto);
    }

    /**
     * 다른 사람의 유저정보 조회
     * @param idToken : Firebase 통해서 받은 해당 유저에 대한 idToken
     * @param uid : Firebase 통해 얻은 uid
     * @param other_uid : 찾고자하는 유저의 uid
     * @return : 다른 사람의 유저 정보
     * @throws IOException : 에러
     * @throws FirebaseAuthException : 에러
     */
    @GetMapping("/{uid}/{other_uid}")
    public ResponseEntity<UserInfoResponseDto> readOtherUserInfo(@RequestHeader("Authorization") String idToken,
                                                                      @PathVariable String uid,
                                                                      @PathVariable String other_uid) throws IOException, FirebaseAuthException {
        AuthResponse authResponse = authorizeService.isAuthorized(idToken, uid);
        if(authResponse.getIsUser()) {
            return ResponseEntity.ok(UserInfoResponseDto.from(userService.findUserByUid(other_uid)));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/find_id_by_uid/{uid}")
    public Long findIdByUid(@PathVariable String uid) {
        return userService.findIdByUid(uid);
    }
}
