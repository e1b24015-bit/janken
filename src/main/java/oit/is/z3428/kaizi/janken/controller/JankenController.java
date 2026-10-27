package oit.is.z3428.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z3428.kaizi.janken.model.Entry;
import oit.is.z3428.kaizi.janken.model.Janken;
import oit.is.z3428.kaizi.janken.model.Match;
import oit.is.z3428.kaizi.janken.model.MatchMapper;
import oit.is.z3428.kaizi.janken.model.User;
import oit.is.z3428.kaizi.janken.model.UserMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @GetMapping("/janken")
  public String janken(
      @RequestParam(required = false) String hand,
      Principal prin,
      ModelMap model) {

    String loginUser = prin.getName();
    this.entry.addUser(loginUser);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("entry", this.entry);

    ArrayList<User> users = this.userMapper.selectAllUsers();
    model.addAttribute("users", users);

    ArrayList<Match> matches = this.matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

    if (hand != null) {
      Janken janken = new Janken(hand);
      model.addAttribute("myHand", janken.getMyHand());
      model.addAttribute("cpuHand", janken.getCpuHand());
      model.addAttribute("result", janken.getResult());
    }

    return "janken";
  }

  @GetMapping("/match")
  public String match(@RequestParam int id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User opponent = this.userMapper.selectById(id);
    model.addAttribute("loginUser", loginUser);
    model.addAttribute("opponent", opponent);
    return "match";
  }

  @GetMapping("/fight")
  public String fight(
      @RequestParam int id,
      @RequestParam String hand,
      Principal prin,
      ModelMap model) {

    String loginUserName = prin.getName();
    User loginUser = this.userMapper.selectByName(loginUserName);
    User opponent = this.userMapper.selectById(id);

    // CPUの手（固定でGu）
    String cpuHand = "Gu";

    // 勝敗判定
    String result = "Draw";
    if ("Choki".equalsIgnoreCase(hand) || "チョキ".equals(hand)) {
      result = "You Lose";
    } else if ("Pa".equalsIgnoreCase(hand) || "パー".equals(hand)) {
      result = "You Win!";
    }

    // 試合結果をMatchオブジェクトに格納してDBへ登録
    Match match = new Match();
    match.setUser1(loginUser.getId());
    match.setUser2(opponent.getId());
    match.setUser1Hand(hand);
    match.setUser2Hand(cpuHand);
    this.matchMapper.insertMatch(match);

    // 画面渡し用の属性を設定
    model.addAttribute("loginUser", loginUserName);
    model.addAttribute("opponent", opponent);
    model.addAttribute("myHand", hand);
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);

    return "match";
  }
}
