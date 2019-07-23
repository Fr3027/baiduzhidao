package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("/all")
    @ResponseBody
    public List<Question> getAll(@RequestParam(name="status") int status) {
        Iterable<Question> questions = questionRepository.findAllByStatusEquals(status);
        List<Question> questionList = (List<Question>) questions;
        Collections.reverse(questionList);

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
                              @RequestParam(name = "problemid") int problemid) {
        String anotheruser;
        List<Question> questionByProblemId = (List<Question>) questionRepository.findQuestionByProblemId(problemid);
        if(!questionByProblemId.isEmpty())
            return "already exist";
        if (username == "fr1")
            anotheruser = "fr2";
        else
            anotheruser = "fr1";


        Question q = new Question();
        q.setTitle(questiontitle);
        q.setUrl(questionurl);
        q.setUsername(username);
        q.setTaskType(Integer.parseInt(tasktype));
        q.setTaskId(Integer.parseInt(taskid));
        q.setProblemId(problemid);

        //将Question的状态设为0，表示正常状态的题。
        q.setStatus(0);
        questionRepository.save(q);
        return "ok";
    }
    @ResponseBody
    @GetMapping("/disable")
    public String disableQuestion(@RequestParam(name="problemId") int prolblemId){
        Optional<Question> byId = questionRepository.findById(prolblemId);
        if(byId.isPresent()){
            Question question = byId.get();
            question.setStatus(2);
            questionRepository.save(question);

        }

        return "end";
    }@ResponseBody
    @GetMapping("/heart")
    public String addHeart(@RequestParam(name="problemId") int prolblemId){
        Optional<Question> byId = questionRepository.findById(prolblemId);
        if(byId.isPresent()){
            Question question = byId.get();
            question.setStatus(1);
            questionRepository.save(question);

        }

        return "end";
    }


}
