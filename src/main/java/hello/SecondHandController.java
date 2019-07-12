package hello;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping(path="/second")
public class SecondHandController {
    @Autowired
    SecondHandRepository secondHandRepository;

    @GetMapping("/all")
    @ResponseBody
    public List<SecondHand> getAll() {
        Iterable<SecondHand> items = secondHandRepository.findAll();
        List<SecondHand> lists = (List<SecondHand>) items;

        return lists;
    }



    @ResponseBody
    @RequestMapping("/add")
    public String add(@RequestParam(name = "title") String title,
                              @RequestParam(name = "description") String description,
                              @RequestParam(name = "sdate") Date date,
                              @RequestParam(name = "stime") Time time,
                              @RequestParam(name = "uid") Integer uid,
                              @RequestParam(name = "price") Integer price)
    {
        SecondHand secondHand=new SecondHand();
        secondHand.setTitle(title);
        secondHand.setDescription(description);
        secondHand.setSdate(date);
        secondHand.setStime(time);
        secondHand.setUid(uid);
        secondHand.setPrice(price);
        secondHandRepository.save(secondHand);

        return "ok";
    }

}
