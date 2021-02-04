package club.gongsir.linux.controller;

import club.gongsir.linux.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 描述：通过web请求执行指定的shell脚本
 * @author gongsir
 * @date 2020/3/30 20:49
 * 编码不要畏惧变化，要拥抱变化
 */
@RestController
public class DemoController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("hello")
    public String sayHello() {
        return "hello";
    }

    @PostMapping(path = "/linux/exec")
    public ResponseEntity<Result> exec(String secret, String cmd) {
        Result result = new Result();
        // 自定义密钥验证，暂不使用github密钥
        String mySecret = "bzm666";
        if (mySecret.equalsIgnoreCase(secret)) {
            cmd = "sh " + cmd;
            logger.info("将要执行的脚本名称：{}",cmd);
            try{
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(cmd,null,null);
                InputStream stderr =  proc.getInputStream();
                InputStreamReader isr = new InputStreamReader(stderr, StandardCharsets.UTF_8);
                BufferedReader br = new BufferedReader(isr);
                String line="";
                int i=1;
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                    result.putData(String.valueOf(i++),line);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            result.setCode(100);
            result.setMsg("网站更新成功");
            return ResponseEntity.ok(result);
        }
        return ResponseEntity.ok(new Result(101,"密钥错误"));
    }
}
