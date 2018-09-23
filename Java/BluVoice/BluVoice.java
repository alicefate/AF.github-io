
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
	
	MenuItem topItem = new MenuItem("取消置顶");
    MenuItem miniItem = new MenuItem("最小化");
	Menu audioItem = new Menu("选择语音集");
    MenuItem exitItem = new MenuItem("退出");

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
		//ImageIcon icon = new ImageIcon("img/a2.png");//指定图片对象
		int picWidth = icon.getIconWidth();
		int picHeight = icon.getIconHeight();
		
		this.setSize(picWidth, picHeight);
		this.getContentPane().setLayout(null);
		this.setAlwaysOnTop(true); //窗体最顶层显示
		this.setBackground(new Color(0,0,0,0));// 背景透明
		
		Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(screen.width - picWidth, (screen.height - picHeight) / 3);

		/*
		//JFrame用这些设置，JWindow不用
		this.setTitle("测试动画");this.getContentPane().setBackground(Color.red);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setUndecorated(true); // 取消窗口标题栏	
		this.setIconImage(img);   //新图标
		*/
		
		JLabel jLabel = new JLabel(icon);//将背景图放在标签里。
		jLabel.setBounds(0,0,picWidth,picHeight);
		/*
		  ImageIcon icon = new ImageIcon("a2.png"); 创建一个图标（ImageIcon）对象
		  JLabel jLabel = new JLabel(icon); 创建一个标签,并将上面创建的图标对象给该标签,意思是该标签的背景就是 a2 这张图片了
		  jLabel.setBounds(0,0,img.getIconWidth(),img.getIconHeight()); 这里是调整label标签在容器的位置,并调整该标签的大小
		  这里有四个参数,前面两个是确定在容器中的位置的,后面两个是调整标签的大小的,这里它定义的是在容器的 左上角位置,标签的长和宽跟 a2 这张图片的长和宽相等
		 */
		
		this.getLayeredPane().add(jLabel, new Integer(Integer.MIN_VALUE));//将背景标签添加到jf窗口对象的LayeredPane面板里。
		/*this.getLayeredPane() 得到一个JLayeredPane 对象,这个类为容器添加了深度,允许组件在需要时互相重叠.
		  this.getLayeredPane().add(jLabel,new Integer(Integer.MIN_VALUE)); 这句话的意思是,为前面创建的标签 设置层的深度.
		  										这里将其设置为了最底层,因为Integer.MIN_VALUE是int类型的最小值了
		*/
		
		((JPanel) this.getContentPane()).setOpaque(false);
		/*
		 //上面的一行代码，是以下三行的简写形式
		  Container cp=this.getContentPane();
          cp.setLayout(new BorderLayout());
          ((JPanel)cp).setOpaque(false);

          this.getContentPane(); 得到的是一个容器 (Container)对象
		  (JFrame)this.getContentPane() ; 将该容器 (Container) 强转为面板对象(JFrame)
		  ((JPanel) this.getContentPane()).setOpaque(false); 将该面板设置为透明；
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
		
				
		//设置软件在托盘上显示的图标
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image img = tk.getImage(getClass().getResource("img/blu.png"));//图片与该类文件同一目录
        SystemTray systemTray = SystemTray.getSystemTray(); //获得系统托盘的实例
        TrayIcon trayIcon = new TrayIcon(img, "blu的游戏语音");
        
		PopupMenu popupMenu = new PopupMenu();
        popupMenu.add(new MenuItem("退出"));
        popupMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String cmd = e.getActionCommand();
                if (cmd.equals("退出")) {
                	systemTray.remove(trayIcon);
                    System.exit(0);
                }
            }   
        });       
        // 为托盘图标加弹出菜弹
        trayIcon.setPopupMenu(popupMenu);
        
        try {
            systemTray.add(trayIcon); //设置托盘的图标，图片与该类文件同一目录
            trayIcon.setImageAutoSize(true);
        } catch (AWTException e2) {
            e2.printStackTrace();
        }
       
        /*JWindow 不能使用下面的 setExtendedState ，无法直接最小化
         *直接把dispose放进右键菜单里，当做最小化的反映了
         *所以此段代码就注销掉了
         
         //窗口最小化时软件dispose
        this.addWindowListener(new WindowAdapter() {
            //图标化窗口时调用事件
            public void windowIconified(WindowEvent e) {
                dispose(); //窗口最小化时dispose该窗口
            }
        });*/
    
        //点击托盘图标，软件正常显示
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
                /*一般string该使用这种写法，但菜单内容较多，换成了switch
                
                if (cmd.equals("退出")) {
                    System.exit(0);
                }
                if (cmd.equals("取消置顶")) {
                    setAlwaysOnTop(false);
                    topItem.setLabel("置顶");
                }
                */
                switch(cmd){
                	case "退出":
                		playMusic(7);
                		systemTray.remove(trayIcon);
                		System.exit(0);
                		break;
                	case "取消置顶":
                		setAlwaysOnTop(false);
                		topItem.setLabel("置顶");
                		break;
                	case "置顶":
                		setAlwaysOnTop(true);
                		topItem.setLabel("取消置顶");
                		break;
                	case "最小化":
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
			/* 双击播放音乐 */
			public void mouseClicked(MouseEvent e)
            {
               if (e.getClickCount() == 2)// 判断是否双击了鼠标
               {
                  playMusic(choice);
               }
            }
			/* 右键菜单 */
			public void mouseReleased(MouseEvent e){
				if(e.getButton() == MouseEvent.BUTTON3){
					popup.show(e.getComponent(), e.getX(),e.getY());
				}
			}
			
			/* 窗体鼠标移动事件 */
			public void mousePressed(MouseEvent e) { //鼠标按下事件
				pressedPoint = e.getPoint(); //记录鼠标坐标
			}
			
		});
		
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) { // 鼠标拖拽事件
				Point point = e.getPoint();// 获取当前坐标
				Point locationPoint = getLocation();// 获取窗体坐标
				int x = locationPoint.x + point.x - pressedPoint.x;// 计算移动后的新坐标
				int y = locationPoint.y + point.y - pressedPoint.y;
				setLocation(x, y);// 改变窗体位置
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
