package D6B.D_discover_alarm.controller;

import D6B.D_discover_alarm.controller.dto.AlarmDto;
import D6B.D_discover_alarm.controller.dto.IsAliveDto;

import D6B.D_discover_alarm.controller.dto.NotificationDto;
import D6B.D_discover_alarm.service.AlarmService;
import D6B.D_discover_alarm.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    private final NotificationService notificationService;
    @Autowired
    public AlarmController(AlarmService alarmService,NotificationService notificationService) {
        this.alarmService = alarmService;
        this.notificationService = notificationService;
    }
    @GetMapping("/list/{user_uid}")
    public ResponseEntity<List<AlarmDto>> getAlarmList(@PathVariable Long user_uid){

            List<AlarmDto> dtos = alarmService.getAlarmList(user_uid);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }


    @PostMapping("/create")
    public  ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto notificationdto){
        NotificationDto createDto = notificationService.createNotification(notificationdto);
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    @PutMapping("/{user_uid}/check")
    public ResponseEntity<String> Checked(@PathVariable Long user_uid){
        alarmService.checked(user_uid);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PutMapping("/isalive")
    public ResponseEntity<String> isAlive(@RequestBody IsAliveDto isalivedto){
        alarmService.isAlive(isalivedto);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PutMapping("/marked")
    public ResponseEntity<String> marked(@RequestBody IsAliveDto isalivedto){
        alarmService.marked(isalivedto);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @PutMapping("/remove/{user_uid}")
    public ResponseEntity<String> remove(@PathVariable Long user_uid){
        alarmService.remove(user_uid);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @DeleteMapping("/alarm/delete/{alarm_id}")
    public ResponseEntity<String> deleteAlarm(@PathVariable Long alarm_id){
        alarmService.deleteAlarm(alarm_id);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }
}
