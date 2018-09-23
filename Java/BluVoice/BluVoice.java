
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JComponent;
import java.net.URL;  


public class BluVoice extends JWindow{
	Point pressedPoint;
	static PopupMenu popup = new PopupMenu();
	static int choice = 0;
	static String dis = "01111111"; 
	static int last = 0;
	
	MenuItem topItem = new MenuItem("ȡ���ö�");
    MenuItem miniItem = new MenuItem("��С��");
	Menu audioItem = new Menu("ѡ��������");
    MenuItem exitItem = new MenuItem("�˳�");

	MenuItem pressItem = new MenuItem("press(43)");
    MenuItem cheerfulItem = new MenuItem("cheerful(15)");
	MenuItem idleItem = new MenuItem("idle(42)");
    MenuItem winItem = new MenuItem("win(27)");
    MenuItem loseItem = new MenuItem("lose(10)");
    MenuItem tutorialItem = new MenuItem("tutorial(2)");
	MenuItem loginItem = new MenuItem("login(25)");
    MenuItem quitItem = new MenuItem("quit(6)");
    
    ButtonGroup group = new ButtonGroup();

	public static void main(String[] args){
		new BluVoice();
	}
	
	public BluVoice(){
		ImageIcon icon = new ImageIcon(getClass().getResource("img/a2.png"));
		//ImageIcon icon = new ImageIcon("img/a2.png");//ָ��ͼƬ����
		int picWidth = icon.getIconWidth();
		int picHeight = icon.getIconHeight();
		
		this.setSize(picWidth, picHeight);
		this.getContentPane().setLayout(null);
		this.setAlwaysOnTop(true); //���������ʾ
		this.setBackground(new Color(0,0,0,0));// ����͸��
		
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screen.width - picWidth, (screen.height - picHeight) / 3);

		/*
		//JFrame����Щ���ã�JWindow����
		this.setTitle("���Զ���");this.getContentPane().setBackground(Color.red);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setUndecorated(true); // ȡ�����ڱ�����	
		this.setIconImage(img);   //��ͼ��
		*/
		
		JLabel jLabel = new JLabel(icon);//������ͼ���ڱ�ǩ�
		jLabel.setBounds(0,0,picWidth,picHeight);
		/*
		  ImageIcon icon = new ImageIcon("a2.png"); ����һ��ͼ�꣨ImageIcon������
		  JLabel jLabel = new JLabel(icon); ����һ����ǩ,�������洴����ͼ�������ñ�ǩ,��˼�Ǹñ�ǩ�ı������� a2 ����ͼƬ��
		  jLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); �����ǵ���label��ǩ��������λ��,�������ñ�ǩ�Ĵ�С
		  �������ĸ�����,ǰ��������ȷ���������е�λ�õ�,���������ǵ�����ǩ�Ĵ�С��,��������������������� ���Ͻ�λ��,��ǩ�ĳ��Ϳ�� a2 ����ͼƬ�ĳ��Ϳ����
		 */
		
		this.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));//��������ǩ��ӵ�jf���ڶ����LayeredPane����
		/*this.getLayeredPane() �õ�һ��JLayeredPane ����,�����Ϊ������������,�����������Ҫʱ�����ص�.
		  this.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE)); ��仰����˼��,Ϊǰ�洴���ı�ǩ ���ò�����.
		  										���ｫ������Ϊ����ײ�,��ΪInteger.MIN_VALUE��int���͵���Сֵ��
		*/
		
		((JPanel) this.getContentPane()).setOpaque(false);
		/*
		 //�����һ�д��룬���������еļ�д��ʽ
		  Container cp=this.getContentPane();
          cp.setLayout(new BorderLayout());
          ((JPanel)cp).setOpaque(false);

          this.getContentPane(); �õ�����һ������ (Container)����
		  (JFrame)this.getContentPane() ; �������� (Container) ǿתΪ������(JFrame)
		  ((JPanel) this.getContentPane()).setOpaque(false); �����������Ϊ͸����
		*/
        
		this.setVisible(true);
		
		popup.setFont( new Font("Arial",Font.PLAIN,18));
		popup.add(topItem);
		popup.add(miniItem);
		popup.addSeparator();
		popup.add(audioItem);
		popup.addSeparator();
		popup.add(exitItem);
		
		audioItem.add(pressItem);
		audioItem.add(cheerfulItem);
		audioItem.add(idleItem);
		audioItem.add(winItem);
		audioItem.add(loseItem);
		audioItem.add(tutorialItem);
		audioItem.add(loginItem);
		audioItem.add(quitItem);
		
		pressItem.setEnabled(false);
		pressItem.setFont( new Font("Arial",Font.ITALIC,20));
		
				
		//�����������������ʾ��ͼ��
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.getImage(getClass().getResource("img/blu.png"));//ͼƬ������ļ�ͬһĿ¼
        SystemTray systemTray = SystemTray.getSystemTray(); //���ϵͳ���̵�ʵ��
        TrayIcon trayIcon = new TrayIcon(img, "blu����Ϸ����");
        
		PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(new MenuItem("�˳�"));
        popupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (cmd.equals("�˳�")) {
                	systemTray.remove(trayIcon);
                    System.exit(0);
                }
            }   
        });       
        // Ϊ����ͼ��ӵ����˵�
        trayIcon.setPopupMenu(popupMenu);
        
        try {
            systemTray.add(trayIcon); //�������̵�ͼ�꣬ͼƬ������ļ�ͬһĿ¼
            trayIcon.setImageAutoSize(true);
        } catch (AWTException e2) {
            e2.printStackTrace();
        }
       
        /*JWindow ����ʹ������� setExtendedState ���޷�ֱ����С��
         *ֱ�Ӱ�dispose�Ž��Ҽ��˵��������С���ķ�ӳ��
         *���Դ˶δ����ע������
         
         //������С��ʱ���dispose
        this.addWindowListener(new WindowAdapter() {
            //ͼ�껯����ʱ�����¼�
            public void windowIconified(WindowEvent e) {
                dispose(); //������С��ʱdispose�ô���
            }
        });*/
    
        //�������ͼ�꣬���������ʾ
        trayIcon.addMouseListener(new MouseAdapter() {            
            public void mouseReleased(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON1){
					//setExtendedState(JFrame.NORMAL);
					setVisible(true);
				}
			}
        });
        
        
		audioItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                switch(cmd){
                	case "press(43)":
                		Chosen(pressItem);
                		dis = "01111111";
                		choice = 0;
                		break;
					case "cheerful(15)":
                		Chosen(cheerfulItem);
                		dis = "10111111";
                		choice = 1;
                		break;
                	case "idle(42)":
                		Chosen(idleItem);
                		dis = "11011111";
                		choice = 2;
                		break;
                	case "win(27)":
                		Chosen(winItem);
                		dis = "11101111";
                		choice = 3;
                		break;
                	case "lose(10)":
                		Chosen(loseItem);
                		dis = "11110111";
                		choice = 4;
                		break;
                	case "tutorial(2)":
                		Chosen(tutorialItem);
                		dis = "11111011";
                		choice = 5;
                		break;
                	case "login(25)":
                		Chosen(loginItem);
                		dis = "11111101";
                		choice = 6;
                		break;
                	case "quit(6)":
                		Chosen(quitItem);
                		dis = "11111110";
                		choice = 7;
                		break;
                }
            }   
        });
		
		popup.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                /*һ��string��ʹ������д�������˵����ݽ϶࣬������switch
                
                if (cmd.equals("�˳�")) {
                    System.exit(0);
                }
                if (cmd.equals("ȡ���ö�")) {
                    setAlwaysOnTop(false);
                    topItem.setLabel("�ö�");
                }
                */
                switch(cmd){
                	case "�˳�":
                		playMusic(7);
                		systemTray.remove(trayIcon);
                		System.exit(0);
                		break;
                	case "ȡ���ö�":
                		setAlwaysOnTop(false);
                		topItem.setLabel("�ö�");
                		break;
                	case "�ö�":
                		setAlwaysOnTop(true);
                		topItem.setLabel("ȡ���ö�");
                		break;
                	case "��С��":
                		//setExtendedState(JFrame.ICONIFIED);
                		dispose();
                		break;	
                	default:
                		systemTray.remove(trayIcon);
                		System.exit(0);
                }
            }   
        });
		
		this.add(popup);

		this.addMouseListener(new MouseAdapter() {
			/* ˫���������� */
			public void mouseClicked(MouseEvent e)
            {
               if (e.getClickCount() == 2)// �ж��Ƿ�˫�������
               {
                  playMusic(choice);
               }
            }
			/* �Ҽ��˵� */
			public void mouseReleased(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					popup.show(e.getComponent(), e.getX(),e.getY());
				}
			}
			
			/* ��������ƶ��¼� */
			public void mousePressed(MouseEvent e) { //��갴���¼�
				pressedPoint = e.getPoint(); //��¼�������
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) { // �����ק�¼�
				Point point = e.getPoint();// ��ȡ��ǰ����
				Point locationPoint = getLocation();// ��ȡ��������
				int x = locationPoint.x + point.x - pressedPoint.x;// �����ƶ����������
				int y = locationPoint.y + point.y - pressedPoint.y;
				setLocation(x, y);// �ı䴰��λ��
			}
		});
		
		playMusic(6);
	}
	
	public void Chosen(MenuItem chosenOne){
		
		MenuItem exChosenOne = new MenuItem("");
		int number = dis.indexOf("0");
		
		switch(number){
			case 0:	exChosenOne = pressItem;	break;
			case 1:	exChosenOne = cheerfulItem;	break;
			case 2:	exChosenOne = idleItem;		break;
			case 3:	exChosenOne = winItem;		break;
			case 4:	exChosenOne = loseItem;		break;
			case 5:	exChosenOne = tutorialItem;	break;
			case 6:	exChosenOne = loginItem;	break;
			case 7:	exChosenOne = quitItem;		break;
			default: System.exit(0);	
			}
        exChosenOne.setEnabled(true);
        exChosenOne.setFont( new Font("Arial",Font.PLAIN,18));

        chosenOne.setEnabled(false);
        chosenOne.setFont( new Font("Arial",Font.ITALIC,20));
	}
	
	public static void playMusic(int cdNum){
		String musicName = "";
		int range = 0;
		int songNum = 0;
		
		switch(cdNum){
			case 0:
				musicName = "bluvoice/character_blu_press_";
				range = 43;
				break;
			case 1:
				musicName = "bluvoice/character_blu_cheerful_";
				range = 15;
				break;
			case 2:
				musicName = "bluvoice/character_blu_idle_";
				range = 42;
				break;
			case 3:
				musicName = "bluvoice/character_blu_win_";
				range = 27;
				break;
			case 4:
				musicName = "bluvoice/character_blu_lose_";
				range = 10;
				break;
			case 5:
				musicName = "bluvoice/character_blu_tutorial_";
				range = 2;
				break;
			case 6:
				musicName = "bluvoice/character_blu_login_";
				range = 25;
				break;
			case 7:
				musicName = "bluvoice/character_blu_quit_";
				range = 6;
				break;			
		}
		
		do{
		songNum = (int)(Math.random() * range + 1);	
		}while(last == songNum);
		
		last = songNum;
		musicName = musicName + songNum + ".wav";
		
		AudioPlayer ap = new AudioPlayer(musicName);
		ap.play();
	}
}
