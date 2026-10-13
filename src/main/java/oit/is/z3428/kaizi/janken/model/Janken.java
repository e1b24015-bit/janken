package oit.is.z3428.kaizi.janken.model;

public class Janken {

  private String myHand;
  private String cpuHand;
  private String result;

  public Janken(String myHand) {

    this.myHand = myHand;

    // CPUはグー固定
    this.cpuHand = "グー";

    if (myHand.equals("グー")) {
      this.result = "あいこ";
    } else if (myHand.equals("チョキ")) {
      this.result = "負け";
    } else if (myHand.equals("パー")) {
      this.result = "勝ち";
    }
  }

  public String getMyHand() {
    return myHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }
}
