package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/all")
    @ResponseBody
    public List<Question> getAll() {
        Iterable<Question> questions = questionRepository.findAllByStatusEquals(1);
        List<Question> questionList = (List<Question>) questions;

        return questionList;
    }

    @ResponseBody
    @RequestMapping("/add")
    public String addQuestion(@RequestParam(name = "questiontitle") String questiontitle,
                              @RequestParam(name = "questionurl") String questionurl,
                              @RequestParam(name = "questiontype") String questiontype,
                              @RequestParam(name = "username") String username,
                              @RequestParam(name = "tasktype") String tasktype,
                              @RequestParam(name = "taskid") String taskid,
                              @RequestParam(name = "problemid") String problemid) {
        String anotheruser;
        if (username == "fr1")
            anotheruser = "fr2";
        else
            anotheruser = "fr1";


        if (false)
            return "already exist";

        if (false)
            return "already disabled";

        Question q = new Question();
        q.setTitle(questiontitle);
        q.setUrl(questionurl);
        q.setUsername(username);
        q.setTaskType(Integer.parseInt(tasktype));
        q.setTaskId(Integer.parseInt(taskid));
        q.setProblemId(Integer.parseInt(problemid));

        //将Question的状态设为1，表示可以做的题。
        q.setStatus(1);
        questionRepository.save(q);
        return "ok";
    }
    @ResponseBody
    @GetMapping("/disable")
    public String disableQuestion(@RequestParam(name="problemId") int prolblemId){
        Optional<Question> byId = questionRepository.findById(prolblemId);
        if(byId.isPresent()){
            Question question = byId.get();
            question.setStatus(0);
            questionRepository.save(question);

        }

        return "end";
    }


}
