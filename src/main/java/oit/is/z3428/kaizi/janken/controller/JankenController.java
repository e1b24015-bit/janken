package oit.is.z3428.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3428.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String hand,
      Model model) {

    if (name != null) {
      model.addAttribute("username", name);
    }

    if (hand != null) {
      Janken janken = new Janken(hand);

      model.addAttribute("myHand", janken.getMyHand());
      model.addAttribute("cpuHand", janken.getCpuHand());
      model.addAttribute("result", janken.getResult());
    }

    return "janken";
  }

  @PostMapping("/janken")
  public String join(
      @RequestParam String username,
      Model model) {

    model.addAttribute("username", username);

    return "janken";
  }
}
