package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Controller
@RequestMapping("/sharecar")
public class ShareCarController {
    @Autowired
    ShareCarRepository shareCarRepository;
    @GetMapping("/all")
    @ResponseBody
    public List<ShareCar> getAll() {
        Iterable<ShareCar> items = shareCarRepository.findAll();
        List<ShareCar> lists = (List<ShareCar>) items;

        return lists;
    }

    @ResponseBody
    @RequestMapping("/add")
    public String add(
                      @RequestParam(name = "description") String description,
                      @RequestParam(name = "src") String src,
                      @RequestParam(name = "des") String des,
                      @RequestParam(name = "sdate") Date sdate,
                      @RequestParam(name = "stime") Time stime)
    {
       ShareCar shareCar=new ShareCar();
       shareCar.setDescription(description);
       shareCar.setSrc(src);
       shareCar.setDes(des);
       shareCar.setSdate(sdate);
       shareCar.setStime(stime);
       shareCarRepository.save(shareCar);

        return "ok";
    }


}
