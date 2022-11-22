package com.lfw.purse;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


/**
 * @author 龙发文
 * @ClassName PurseWebApplication
 * @Description TODO
 * @date 2022/11/21 0021 18:32
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.lfw.purse"})
@EnableAsync
@MapperScan("com.lfw.purse.*.mapper")
public class PurseWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PurseWebApplication.class, args);
    }

}
