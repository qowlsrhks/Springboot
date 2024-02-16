package com.example.app.task;

import com.example.app.domain.vo.FileVO;
import com.example.app.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileTask {
    private final FileService fileService;

    @Scheduled(cron = "0 * * * * *")
    private void checkFiles() throws IOException {
        log.info("File Checking Task Run....................");
        log.info("==========================================");

//        DB에서 어제 날짜에 등록된 파일들 조회
        List<FileVO> filesOfYesterDayInDB = fileService.getFilesOfYesterDay();
//        파일 객체를 Path객체로 변경
        List<Path> pathsOfYesterday = filesOfYesterDayInDB.stream()
                .map(file -> Paths.get("C:/upload", file.getFileUploadPath(), file.getFileUuid() + "_" + file.getFileName()))
                .collect(Collectors.toList());

//        이미지 파일을 추가로 담아주기
        filesOfYesterDayInDB.stream()
//               이미지 파일이라면
                .filter(FileVO::isFileIsImage)
//                기존 경로 앞에 t_를 붙여 썸네일 경로까지
                .map(file -> Paths.get("C:/upload", file.getFileUploadPath(), file.getFileUuid() + "_" + file.getFileName()))
//                Path List에 추가해준다
                .forEach(pathsOfYesterday::add);

        log.info("=================DB경로===================");
        pathsOfYesterday.stream().map(Path::toAbsolutePath).map(Path::toString).forEach(log::info);

//        실제 서버 경로를 File 객체에 담아준다
        log.info("=================서버경로====================");
        log.info(getUploadPathOfYesterDay());
        File filesInDirectory = Paths.get("C:/upload", getUploadPathOfYesterDay()).toFile();
//        File객체는 해당 경로의 파일들을 List로 가져올 수 있으며 조건식이 true일 경우만 가져온다.
//        DB에 없는 파일은 삭제한다
        if(filesInDirectory.listFiles() != null){
            log.info("======================삭제 경로===============");
            Arrays.asList(filesInDirectory.listFiles(file -> !pathsOfYesterday.contains(file.toPath()))).forEach(File::delete);
        }

    }

    private String getUploadPathOfYesterDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Calendar yesterDay = Calendar.getInstance();
        yesterDay.add(Calendar.DATE, -1);
        return simpleDateFormat.format(yesterDay.getTime());
    }
}
