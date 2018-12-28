package sec.project.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.repository.SignupRepository;

@Controller
public class SignupController implements ErrorController {

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private HttpSession sesh;

    @RequestMapping("*")
    public String defaultMapping() {
        sesh.setAttribute("admin", "admin");
        return "redirect:/form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String loadForm() {
        sesh.setAttribute("admin", "admin");
        return "form";
    }

    @RequestMapping(value = "/form", method = RequestMethod.POST)
    public String submitForm(Model model, @RequestParam String name, @RequestParam String address) {
        signupRepository.addSignup(name, address);
        model.addAttribute("signup", signupRepository.findAll());
        return "done";
    }

    @RequestMapping(value = "/adminStuff")
    public String goToAdminPage(Model model, @RequestParam String username, @RequestParam String password) {
        if (sesh.getAttribute(username) != null && sesh.getAttribute(username).equals(password)) {
            return "admin";
        }
        return "redirect:/form";
    }

    @RequestMapping(value = "/adminStuff/eventList")
    public String goToEventlistPage(Model model) {
        model.addAttribute("stuff", signupRepository.findAll());
        return "eventList";
    }

    @RequestMapping(value = "/error")
    public String errorHandlingPage(Model model) {
        return "error";
    }

    @RequestMapping(value = "/addAdmin")
    public String addAdminToSystem(@RequestParam String username, @RequestParam String password) {
        sesh.setAttribute(username, password);
        return "form";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
