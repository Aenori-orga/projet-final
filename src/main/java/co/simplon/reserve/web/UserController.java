package co.simplon.reserve.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import co.simplon.reserve.model.Subscriber;
import co.simplon.reserve.model.User;
import co.simplon.reserve.service.UserService;

@Controller
@RequestMapping
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public ModelAndView getAll(ModelMap model) {
	List<User> userList = userService.getAll();
	model.addAttribute("userList", userList);
	boolean isUsersPage = true;
	model.addAttribute("isUsersPage", isUsersPage);
	return new ModelAndView("users", model);
    }

    @RequestMapping("/addUser")
    public ModelAndView addUser(@RequestParam("name") String name, @RequestParam("surname") String surname,
	    @RequestParam("email") String email, @RequestParam("password") String password, ModelMap model,
	    RedirectAttributes redir) {
	boolean nameMatches = false;
	boolean surnameMatches = false;
	boolean emailMatches = false;
	boolean passwordMatches = false;
	Subscriber subscriber = new Subscriber(name, surname, email, password);

	// RequestParmaters input checks
	if (name.matches(
		"^[a-zA-Z0-9Ã¡Ã Ã¢Ã¤Ã£Ã¥Ã§Ã©Ã¨ÃªÃ«Ã­Ã¬Ã®Ã¯Ã±Ã³Ã²Ã´Ã¶ÃµÃºÃ¹Ã»Ã¼Ã½Ã¿Ã¦Å“Ã�Ã€Ã‚Ã„ÃƒÃ…Ã‡Ã‰ÃˆÃŠÃ‹Ã�ÃŒÃŽÃ�Ã‘Ã“Ã’Ã”Ã–Ã•ÃšÃ™Ã›ÃœÃ�Å¸Ã†Å’.\\s-]{1,35}$")) {
	    nameMatches = true;
	    redir.addFlashAttribute("name", name);
	} else {
	    redir.addFlashAttribute("nameError", "Beetween 1 and 35 characters required");
	}
	if (surname.matches(
		"^[a-zA-Z0-9Ã¡Ã Ã¢Ã¤Ã£Ã¥Ã§Ã©Ã¨ÃªÃ«Ã­Ã¬Ã®Ã¯Ã±Ã³Ã²Ã´Ã¶ÃµÃºÃ¹Ã»Ã¼Ã½Ã¿Ã¦Å“Ã�Ã€Ã‚Ã„ÃƒÃ…Ã‡Ã‰ÃˆÃŠÃ‹Ã�ÃŒÃŽÃ�Ã‘Ã“Ã’Ã”Ã–Ã•ÃšÃ™Ã›ÃœÃ�Å¸Ã†Å’.\\s-]{1,35}$")) {
	    surnameMatches = true;
	    redir.addFlashAttribute("surname", surname);
	} else {
	    redir.addFlashAttribute("surnameError", "Beetween 1 and 35 characters required");
	}
	if (email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
	    emailMatches = true;
	    redir.addFlashAttribute("email", email);
	} else {
	    redir.addFlashAttribute("emailError", "Invalid email adress format");
	}
	if (pwdPolicy(password).isEmpty()) {
	    passwordMatches = true;
	} else {
	    redir.addFlashAttribute("pwdErrors", pwdPolicy(password));
	}

	// Creating user when checks are ok
	if (nameMatches && surnameMatches && emailMatches && passwordMatches) {
	    User user = new User(name, surname, email, password, User.Role.USER, true);
	    userService.add(user);
	}
	return new ModelAndView("redirect:/users");
    }

    public List<String> pwdPolicy(String password) {
	List<String> errors = new ArrayList<String>();
	if (password.length() < 8) {
	    errors.add("8 or more characters needed");
	}
	if (!password.matches(".*[0-9].*")) {
	    errors.add("At least 1 digit needed");
	}
	if (!password.matches(".*[a-z].*")) {
	    errors.add("At least 1 lowercase charcater needed");
	}
	if (!password.matches(".*[A-Z].*")) {
	    errors.add("At least 1 uppercase character needed");
	}
	if (password.matches(".*\\s.*")) {
	    errors.add("No whitespace allowed");
	}
	return errors;
    }
    /*
     * @RequestMapping(value="/addUser", method=RequestMethod.POST) public
     * ModelAndView addUser(@Valid Subscriber subscriber, BindingResult result,
     * ModelMap model, RedirectAttributes redir) {
     * System.out.println(subscriber.toString()); if(result.hasErrors()){
     * System.out.println("Result has errors");
     * redir.addAttribute(result.getModel()); //ModelAndView mav = new
     * ModelAndView("redirect:/users", model);
     * //mav.addAllObjects(result.getModel()); //return mav; return new
     * ModelAndView("redirect:/users"); } else { User user = new
     * User(subscriber.getName(), subscriber.getSurname(),
     * subscriber.getEmail(), subscriber.getPassword(), User.Role.USER, true);
     * userService.add(user); return new ModelAndView("redirect:/users"); } }
     */
    /*
     * @RequestMapping(value="addUser", method=RequestMethod.POST) public String
     * addUser(@Valid Subscriber subscriber, BindingResult result, ModelMap
     * model) { System.out.println(subscriber.toString());
     * if(result.hasErrors()){ System.out.println("Result has errors"); return
     * "users"; } else { User user = new User(subscriber.getName(),
     * subscriber.getSurname(), subscriber.getEmail(), subscriber.getPassword(),
     * User.Role.USER, true); userService.add(user);
     * model.addAttribute("message", "Successfully saved user: " +
     * subscriber.toString()); return "users"; } }
     */

    @RequestMapping("/addAnyUser")
    public ModelAndView addAnyUser(@RequestParam("name") String name, @RequestParam("surname") String surname,
	    @RequestParam("email") String email, @RequestParam("password") String password,
	    @RequestParam("role") User.Role role, ModelMap model) {
	User user = new User(name, surname, email, password, role, true);
	userService.add(user);
	return new ModelAndView("redirect:/users");
    }

    @RequestMapping("/changeRole")
    public ModelAndView changeRole(@RequestParam("userRole") User.Role userRole, @RequestParam("userId") Integer userId,
	    ModelMap model) {
	User user = userService.getById(userId);
	user.changeUserRole(userRole);
	userService.add(user);
	return new ModelAndView("redirect:/users");
    }

    @RequestMapping("/updateUserStatus")
    public ModelAndView updateUserStatus(@RequestParam("enabled") boolean enabled, @RequestParam("id") Integer id,
	    ModelMap model) {
	User user = userService.getById(id);
	user.updateUserStatus(enabled);
	userService.add(user);
	return new ModelAndView("redirect:/users");
    }

    @RequestMapping("/password")
    public ModelAndView password(ModelMap model) {
	boolean isPasswordPage = true;
	model.addAttribute("isPasswordPage", isPasswordPage);
	return new ModelAndView("password", model);
    }

    @RequestMapping("/changePassword")
    public ModelAndView changePassword(@RequestParam("currentPasswordInput") String currentPasswordInput,
	    @RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword,
	    RedirectAttributes redirectAttrs) {
	// get current User
	String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
	User user = userService.getByEmail(currentEmail);
	String currentPassword = (SecurityContextHolder.getContext().getAuthentication().getCredentials().toString());
	if (Objects.equals(currentPassword, currentPasswordInput) && Objects.equals(newPassword, confirmPassword)) {
	    user.setPassword(newPassword);
	    userService.add(user);
	    redirectAttrs.addFlashAttribute("success", "Success! Your password has been changed.");
	    return new ModelAndView("redirect:/password");
	} else {
	    redirectAttrs.addFlashAttribute("error", "Error Password Confirmation: Please re-enter your password.");
	    return new ModelAndView("redirect:/password");
	}
    }

    // @RequestMapping("/userById")
    // public ModelAndView getById(@RequestParam("id") Integer id, ModelMap
    // model) {
    // User user = userService.getById(id);
    // model.addAttribute("user", user);
    // }

}
