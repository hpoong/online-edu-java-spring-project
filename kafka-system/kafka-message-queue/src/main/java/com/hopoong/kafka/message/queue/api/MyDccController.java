package com.hopoong.kafka.message.queue.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hopoong.kafka.message.queue.model.MyModel;
import com.hopoong.kafka.message.queue.service.MyService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyDccController {

    private final MyService myService;

    /**
     * CRUD에서 HttpStatus를 위한 상세한 핸들링은 생략
     */

    @PostMapping("/greetings")
    MyModel create(
            @RequestBody Request request
    ) throws JsonProcessingException {
        if (
                request == null ||
                        request.userId == null ||
                        request.userName == null ||
                        request.userAge == null ||
                        request.content == null
        ) return null;

        MyModel myModel = MyModel.create(
                request.userId,
                request.userAge,
                request.userName,
                request.content
        );
        return myService.save(myModel);
    }

    @GetMapping("/greetings")
    List<MyModel> list() {
        return myService.findAll();
    }

    @GetMapping("/greetings/{id}")
    MyModel get(
            @PathVariable Integer id
    ) {
        return myService.findById(id);
    }

    @PatchMapping("/greetings/{id}")
    MyModel update(
            @PathVariable Integer id,
            @RequestBody String content
    ) throws JsonProcessingException {
        if (id == null || content == null || content.isBlank()) return null;
        MyModel myModel = myService.findById(id);
        myModel.setContent(content);
        return myService.save(myModel);
    }

    @DeleteMapping("/greetings/{id}")
    void delete(
            @PathVariable Integer id
    ) {
        myService.delete(id);
    }

    @Data
    private static class Request {
        Integer userId;
        String userName;
        Integer userAge;
        String content;
    }
}
