package project;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Lotto extends Frame implements ActionListener {
	WinListener wl; //WinListemer 객체 선언
	MessageBox mb; //MessageBox 객체 선언
	Choice cho[] []; // 게임의 번호 선택 콤보박스
	Button bt, rb[]; // 추첨버튼, 랜덤 선택 버튼
	Panel p[], main; //게임을 붙일 패널
	Label lb[], title;
	int lotto[]; // 추첨 번호를 저장할 배열
	int point;
	 public Lotto() {
		 setLayout(new BorderLayout() ); // 기본 레이아웃 설정
		 setTitle("가상 로또 프로그램");
		 wl = new WinListener(); // WinListener class 객체 생성
		 addWindowListener(wl); //이벤트 연결
		 
		 lb = new Label[5]; //게임명
		 p = new Panel[5]; //게임 판(5개)
		 cho = new Choice[5][]; //5번의 로또를 할 수 있는 콤보박스
		 main = new Panel(new GridLayout(1,5)); //다섯번의 게임을 붙일 패널
		 title = new Label("Lotto Game"); //메인 타이틀
		 
		 
		 rb = new Button[5]; //랜덤 선택 버튼 생성(5개)
		 bt = new Button("추첨하기"); //추첨하기 버튼
		 bt.addActionListener(this); //추첨하기 버튼 이벤트 추가
		 
		 for(int k = 0; k<cho.length; k++) { //게임의 개수 만큼 반복
			 cho[k] = new Choice[7]; //한게임에 숫자 7개를 선택
			 rb[k] = new Button((k+1) + "번 게임 자동선택"); //자동선택 버튼 생성
			 rb[k].addActionListener(this); //버튼 이벤트 추가
			 
			 p[k] = new Panel(new GridLayout(9,1)); //한게임을 저장할 패널 생성
			 lb[k] = new Label ("Game" + (k+1));
			 p[k].add(lb[k]); //게임명 추가
		 p[k].add(lb[k]); //게임명 추가
		 for(int i = 0; i<cho[k].length; i++) {
			 cho[k][i] = new Choice(); //각각의 숫자를 선택할 콤보박스 생성
			 for(int j = 1; j<=45; j++)
				 cho[k][i].add(String.valueOf(j)); //생성한 콤보박스에 1~45까지 숫자 입력
			 p[k].add(cho[k][i]); //생성된 콤보박스 추가
			 
		 }
		 if(k%2==0)
			 p[k].setBackground(new Color(255,228,196)); //짝수번 게임의 배경색
		 else
			 p[k].setBackground(new Color(176,224,230)); //홀수번 게임의 배경색
		 p[k].add(rb[k]); //랜덤 선택 버튼 붙이기
		 main.add(p[k]); //완성된 게임패널 붙이기
	}
	add("North", title); //타이틀 추가
	add("Center",main); //게임 붙이기
	add("South", bt); //추첨버튼 붙이기				 
  }
  public void actionPerformed(ActionEvent ae) {
	  if(ae.getActionCommand() == "추첨하기") { //추첨하기 버튼 클릭 시
		  lotto = lottery(); //번호 추첨
		  boolean check = true;
		  
		  for(int i =0; i<cho.length; i++)
			  for(int j=0; j<cho[i].length; j++)
				  cho[i][j].setBackground(new Color(255,255,255)); //콤보박스 배경색 초기화
		  
		  for(int i = 0; i<cho.length; i++) {
			  for(int j = 0; j<cho[i].length; j++) {
				  for(int k = j + 1; k<cho[i].length; k++) {
					  if(cho[i][j].getSelectedIndex() == cho[i][k].getSelectedIndex()) { //번호가 중복될시  
						  cho[i][j].setBackground(new Color(255,00,00)); //중복색 적용
						  cho[i][k].setBackground(new Color(255,00,00));
						  check = false; //중복 체크
					  }
				  }
			  }
		  }
		  
		  if(check) {
			  point = 255;//포인트 초기화
			  
			  String temp = "";
			  for(int i = 0; i<lotto.length-1; i++)
				  temp = temp + lotto[i] + ", ";
			  temp = temp + "보너스 : " + lotto[lotto.length-1];//당첨번호 String 생성
			  title.setText("Lotto Game");//타이틀 초기화
			  title.setText(title.getText() + "-당첨번호 : " + temp);//당첨 번호 출력
			  
			  for(int i = 0; i<cho.length; i++) {
				  temp = result(i, cho[i]);//매칭 메소드 호출
				  lb[i].setText("Game " + (i+1)+ "-결과 : " + temp);
				  if((int)temp.charAt(0)-49 == 1 || (int)temp.charAt(0)-49 == 2
						  ||(int)temp.charAt(0) - 49 == 3 || (int)temp.charAt(0) -49 == 4 || (int)temp.charAt(0)-49==5){
					  if((int)temp.charAt(0) - 49 < point)
						  point = (int)temp.charAt(0)-49;//높은 등수 등록
				  }			  
			  }
			  if(point < 6)//결과 메시지 박스
				  mb = new MessageBox(this,"당첨", (point + 1)+"등에 당첨되었습니다.");
			  else
				  mb = new MessageBox(this, "미당첨", "꽝입니다.");
		  }
		  else
			  mb = new MessageBox(this, "Error", "번호가 중복되었습니다");//번호 중복시 박스
	  }
	  else {//랜덤선택 버튼 클릭 시
		  int num = (int)ae.getActionCommand().charAt(0)-49;//선택한 게임번호
		  int rn[] = lottery();//랜덤 번호 6개 생성
		  
		  for(int i=0; i<cho[num].length;i++)
			  cho[num][i].select(rn[i]-1);//생성한 랜덤 번호 콤보박스에 대입
	  }
  }

  
  public String result(int index, Choice cho[]) {
	 int count = 0;//일치하는 숫자의 개수 저장할 변수
	  boolean count2=false;//보너스 숫자 확인할 변수
	  String re="";
	  for(int i = 0; i<cho.length;i++) {
		  for(int j = 0; j<lotto.length-1;j++) {//보너스 숫자를 제외한 나머지 숫자들 비교
			  if(lotto[j]==Integer.parseInt(cho[i].getSelectedItem())) {//일치하면
				  count++;//일치 카운트 증가
				  this.cho[index][i].setBackground(new Color(135,206,250));//일치한 콤보박스의 배경색 변경
			  }
		  }
		  if(lotto[lotto.length-1]==Integer.parseInt(cho[i].getSelectedItem())) {//보너스 숫자와 비교
			  count2=true;//일치
			  this.cho[index][i].setBackground(new Color(240,230,140));//배경색 변경
		  }
	  }
	  switch(count) {
	  case 6:
		  re = "1등";
		  break;
	  case 5:
		  if(count2) {
			  re="2등";
			  break;
		  }
		  re = "3등";
		  break;
	  case 4:
		  re = "4등";
		  break;
	  case 3:
		  re = "5등";
		  break;
	  default:
		  re="꽝";
	  }
	  return re;
  }
  public int[] lottery() {//번호를 추첨하는 메소드
	  Random r = new Random();//랜덤 클래스 객체 생성
	  int num[] = new int[7];//랜덤 수 저장할 배열 생성
	  int temp;//랜덤수를 임시로 저장할 변수
	  boolean check;//중복 수인지 결과를 저장할 변수
	  int index=0;//생성한 랜덤수의 개수를 저장할 변수
	  
	  for(int i = 0;i<num.length;i++)
		  num[i]=0;//번호 0으로 초기화
	  
	  do {
		  check = true;
		  temp=r.nextInt(45)+1;//랜덤수 생성 1~45까지
		  
		  for(int j=0;j<num.length;j++)
			  if(num[j]==temp)
				  check=false;//번호 중복체크
		  if(check) {
			  num[index++]=temp;//추첨번호 추가
		  }
	  }
	  while(index<num.length);//6번 추첨할 때까지
	  return num;
  }
  public static void main(String args[]) {
	  Lotto l = new Lotto();
	  l.setVisible(true);//화면출력
	  l.setBounds(100,100,1000,300);
  }
  class WinListener extends WindowAdapter{//class생성, WindowAdapter class 상속
	  public void windowClosing(WindowEvent we) {//windowClosing() method 재정의
		  System.exit(0);//종료
	  }
  }
  public class MessageBox extends Dialog implements ActionListener{//메시지 박스
	  String msg;//메시지
	  Button bt;//확인 버튼
	  public MessageBox(Frame f, String title,String str) {//생성자
		  super(f,title,true);
		  msg=str;
		  
		  add("North",new Label(msg, Label.CENTER));//메시지 추가하기
		  
		  bt = new Button("확인");//버튼 생성
		  bt.addActionListener(this);//버튼 이벤트 연결
		  
		  Panel p = new Panel();//패널 생성
		  p.add(bt);//패널에 버튼 붙이기
		  add("South",p);//패널 붙이기
		  
		  setBounds(150,150,300,150);
		  setVisible(true);//창 보이기
	  }
	  public void actionPerformed(ActionEvent e) {
		  dispose();//닫기
	  }
	  
  }
}