package project;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Lotto extends Frame implements ActionListener {
	WinListener wl; //WinListemer ��ü ����
	MessageBox mb; //MessageBox ��ü ����
	Choice cho[] []; // ������ ��ȣ ���� �޺��ڽ�
	Button bt, rb[]; // ��÷��ư, ���� ���� ��ư
	Panel p[], main; //������ ���� �г�
	Label lb[], title;
	int lotto[]; // ��÷ ��ȣ�� ������ �迭
	int point;
	 public Lotto() {
		 setLayout(new BorderLayout() ); // �⺻ ���̾ƿ� ����
		 setTitle("���� �ζ� ���α׷�");
		 wl = new WinListener(); // WinListener class ��ü ����
		 addWindowListener(wl); //�̺�Ʈ ����
		 
		 lb = new Label[5]; //���Ӹ�
		 p = new Panel[5]; //���� ��(5��)
		 cho = new Choice[5][]; //5���� �ζǸ� �� �� �ִ� �޺��ڽ�
		 main = new Panel(new GridLayout(1,5)); //�ټ����� ������ ���� �г�
		 title = new Label("Lotto Game"); //���� Ÿ��Ʋ
		 
		 
		 rb = new Button[5]; //���� ���� ��ư ����(5��)
		 bt = new Button("��÷�ϱ�"); //��÷�ϱ� ��ư
		 bt.addActionListener(this); //��÷�ϱ� ��ư �̺�Ʈ �߰�
		 
		 for(int k = 0; k<cho.length; k++) { //������ ���� ��ŭ �ݺ�
			 cho[k] = new Choice[7]; //�Ѱ��ӿ� ���� 7���� ����
			 rb[k] = new Button((k+1) + "�� ���� �ڵ�����"); //�ڵ����� ��ư ����
			 rb[k].addActionListener(this); //��ư �̺�Ʈ �߰�
			 
			 p[k] = new Panel(new GridLayout(9,1)); //�Ѱ����� ������ �г� ����
			 lb[k] = new Label ("Game" + (k+1));
			 p[k].add(lb[k]); //���Ӹ� �߰�
		 p[k].add(lb[k]); //���Ӹ� �߰�
		 for(int i = 0; i<cho[k].length; i++) {
			 cho[k][i] = new Choice(); //������ ���ڸ� ������ �޺��ڽ� ����
			 for(int j = 1; j<=45; j++)
				 cho[k][i].add(String.valueOf(j)); //������ �޺��ڽ��� 1~45���� ���� �Է�
			 p[k].add(cho[k][i]); //������ �޺��ڽ� �߰�
			 
		 }
		 if(k%2==0)
			 p[k].setBackground(new Color(255,228,196)); //¦���� ������ ����
		 else
			 p[k].setBackground(new Color(176,224,230)); //Ȧ���� ������ ����
		 p[k].add(rb[k]); //���� ���� ��ư ���̱�
		 main.add(p[k]); //�ϼ��� �����г� ���̱�
	}
	add("North", title); //Ÿ��Ʋ �߰�
	add("Center",main); //���� ���̱�
	add("South", bt); //��÷��ư ���̱�				 
  }
  public void actionPerformed(ActionEvent ae) {
	  if(ae.getActionCommand() == "��÷�ϱ�") { //��÷�ϱ� ��ư Ŭ�� ��
		  lotto = lottery(); //��ȣ ��÷
		  boolean check = true;
		  
		  for(int i =0; i<cho.length; i++)
			  for(int j=0; j<cho[i].length; j++)
				  cho[i][j].setBackground(new Color(255,255,255)); //�޺��ڽ� ���� �ʱ�ȭ
		  
		  for(int i = 0; i<cho.length; i++) {
			  for(int j = 0; j<cho[i].length; j++) {
				  for(int k = j + 1; k<cho[i].length; k++) {
					  if(cho[i][j].getSelectedIndex() == cho[i][k].getSelectedIndex()) { //��ȣ�� �ߺ��ɽ�  
						  cho[i][j].setBackground(new Color(255,00,00)); //�ߺ��� ����
						  cho[i][k].setBackground(new Color(255,00,00));
						  check = false; //�ߺ� üũ
					  }
				  }
			  }
		  }
		  
		  if(check) {
			  point = 255;//����Ʈ �ʱ�ȭ
			  
			  String temp = "";
			  for(int i = 0; i<lotto.length-1; i++)
				  temp = temp + lotto[i] + ", ";
			  temp = temp + "���ʽ� : " + lotto[lotto.length-1];//��÷��ȣ String ����
			  title.setText("Lotto Game");//Ÿ��Ʋ �ʱ�ȭ
			  title.setText(title.getText() + "-��÷��ȣ : " + temp);//��÷ ��ȣ ���
			  
			  for(int i = 0; i<cho.length; i++) {
				  temp = result(i, cho[i]);//��Ī �޼ҵ� ȣ��
				  lb[i].setText("Game " + (i+1)+ "-��� : " + temp);
				  if((int)temp.charAt(0)-49 == 1 || (int)temp.charAt(0)-49 == 2
						  ||(int)temp.charAt(0) - 49 == 3 || (int)temp.charAt(0) -49 == 4 || (int)temp.charAt(0)-49==5){
					  if((int)temp.charAt(0) - 49 < point)
						  point = (int)temp.charAt(0)-49;//���� ��� ���
				  }			  
			  }
			  if(point < 6)//��� �޽��� �ڽ�
				  mb = new MessageBox(this,"��÷", (point + 1)+"� ��÷�Ǿ����ϴ�.");
			  else
				  mb = new MessageBox(this, "�̴�÷", "���Դϴ�.");
		  }
		  else
			  mb = new MessageBox(this, "Error", "��ȣ�� �ߺ��Ǿ����ϴ�");//��ȣ �ߺ��� �ڽ�
	  }
	  else {//�������� ��ư Ŭ�� ��
		  int num = (int)ae.getActionCommand().charAt(0)-49;//������ ���ӹ�ȣ
		  int rn[] = lottery();//���� ��ȣ 6�� ����
		  
		  for(int i=0; i<cho[num].length;i++)
			  cho[num][i].select(rn[i]-1);//������ ���� ��ȣ �޺��ڽ��� ����
	  }
  }

  
  public String result(int index, Choice cho[]) {
	 int count = 0;//��ġ�ϴ� ������ ���� ������ ����
	  boolean count2=false;//���ʽ� ���� Ȯ���� ����
	  String re="";
	  for(int i = 0; i<cho.length;i++) {
		  for(int j = 0; j<lotto.length-1;j++) {//���ʽ� ���ڸ� ������ ������ ���ڵ� ��
			  if(lotto[j]==Integer.parseInt(cho[i].getSelectedItem())) {//��ġ�ϸ�
				  count++;//��ġ ī��Ʈ ����
				  this.cho[index][i].setBackground(new Color(135,206,250));//��ġ�� �޺��ڽ��� ���� ����
			  }
		  }
		  if(lotto[lotto.length-1]==Integer.parseInt(cho[i].getSelectedItem())) {//���ʽ� ���ڿ� ��
			  count2=true;//��ġ
			  this.cho[index][i].setBackground(new Color(240,230,140));//���� ����
		  }
	  }
	  switch(count) {
	  case 6:
		  re = "1��";
		  break;
	  case 5:
		  if(count2) {
			  re="2��";
			  break;
		  }
		  re = "3��";
		  break;
	  case 4:
		  re = "4��";
		  break;
	  case 3:
		  re = "5��";
		  break;
	  default:
		  re="��";
	  }
	  return re;
  }
  public int[] lottery() {//��ȣ�� ��÷�ϴ� �޼ҵ�
	  Random r = new Random();//���� Ŭ���� ��ü ����
	  int num[] = new int[7];//���� �� ������ �迭 ����
	  int temp;//�������� �ӽ÷� ������ ����
	  boolean check;//�ߺ� ������ ����� ������ ����
	  int index=0;//������ �������� ������ ������ ����
	  
	  for(int i = 0;i<num.length;i++)
		  num[i]=0;//��ȣ 0���� �ʱ�ȭ
	  
	  do {
		  check = true;
		  temp=r.nextInt(45)+1;//������ ���� 1~45����
		  
		  for(int j=0;j<num.length;j++)
			  if(num[j]==temp)
				  check=false;//��ȣ �ߺ�üũ
		  if(check) {
			  num[index++]=temp;//��÷��ȣ �߰�
		  }
	  }
	  while(index<num.length);//6�� ��÷�� ������
	  return num;
  }
  public static void main(String args[]) {
	  Lotto l = new Lotto();
	  l.setVisible(true);//ȭ�����
	  l.setBounds(100,100,1000,300);
  }
  class WinListener extends WindowAdapter{//class����, WindowAdapter class ���
	  public void windowClosing(WindowEvent we) {//windowClosing() method ������
		  System.exit(0);//����
	  }
  }
  public class MessageBox extends Dialog implements ActionListener{//�޽��� �ڽ�
	  String msg;//�޽���
	  Button bt;//Ȯ�� ��ư
	  public MessageBox(Frame f, String title,String str) {//������
		  super(f,title,true);
		  msg=str;
		  
		  add("North",new Label(msg, Label.CENTER));//�޽��� �߰��ϱ�
		  
		  bt = new Button("Ȯ��");//��ư ����
		  bt.addActionListener(this);//��ư �̺�Ʈ ����
		  
		  Panel p = new Panel();//�г� ����
		  p.add(bt);//�гο� ��ư ���̱�
		  add("South",p);//�г� ���̱�
		  
		  setBounds(150,150,300,150);
		  setVisible(true);//â ���̱�
	  }
	  public void actionPerformed(ActionEvent e) {
		  dispose();//�ݱ�
	  }
	  
  }
}