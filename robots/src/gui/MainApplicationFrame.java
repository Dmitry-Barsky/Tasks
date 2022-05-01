package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import log.Logger;

/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {
    	this.setTitle(RobotsProgram.messagesRU.getString("MainAppTitle"));
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //this.setTitle(Integer.toString(screenSize.width) + "x" + Integer.toString(screenSize.height));
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);
        
        
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
        	public void windowClosing(WindowEvent e) {
        		onCloseAction();
        	}
        });
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug(RobotsProgram.messagesRU.getString("LoggerStartMessage"));
        return logWindow;
    }
    
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
    
//    protected JMenuBar createMenuBar() {
//        JMenuBar menuBar = new JMenuBar();
// 
//        //Set up the lone menu.
//        JMenu menu = new JMenu("Document");
//        menu.setMnemonic(KeyEvent.VK_D);
//        menuBar.add(menu);
// 
//        //Set up the first menu item.
//        JMenuItem menuItem = new JMenuItem("New");
//        menuItem.setMnemonic(KeyEvent.VK_N);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_N, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("new");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        //Set up the second menu item.
//        menuItem = new JMenuItem("Quit");
//        menuItem.setMnemonic(KeyEvent.VK_Q);
//        menuItem.setAccelerator(KeyStroke.getKeyStroke(
//                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
//        menuItem.setActionCommand("quit");
////        menuItem.addActionListener(this);
//        menu.add(menuItem);
// 
//        return menuBar;
//    }
    
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(getLookAndFeelMenu());
        menuBar.add(getTestMenu());
        menuBar.add(getExit());
        return menuBar;
    }
    
    private void onCloseAction() {
    	Object[] params = {RobotsProgram.messagesRU.getString("YesBtn"), 
    					   RobotsProgram.messagesRU.getString("NoBtn")}; 
    	
    	var result = JOptionPane.showOptionDialog(this, 
    			RobotsProgram.messagesRU.getString("CloseDialogMessage"), 
    			RobotsProgram.messagesRU.getString("CloseDialogTitle"), 
    			JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, 
    			params, params[0]);
    	//Logger.debug(Integer.toString(result));
    	if (result == 0) {
    		this.setVisible(false);
    		this.dispose();
    		//System.exit(0);
    	}
    }
    
    private JMenu getExit() {
    	
    	JMenu exitMenu = new JMenu(RobotsProgram.messagesRU.getString("ExitText"));
    	exitMenu.setMnemonic(KeyEvent.VK_Q);
    	{
    		JMenuItem exitItem = new JMenuItem(
    				RobotsProgram.messagesRU.getString("ExitText"),
    				KeyEvent.VK_Q);
    		exitItem.addActionListener((event) -> {
    			onCloseAction();
    		});
    		exitMenu.add(exitItem);
    	}
    	return exitMenu;
    }
    
    private JMenu getTestMenu() {
    	JMenu testMenu = new JMenu(RobotsProgram.messagesRU.getString("TestsPanel"));
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription(
        		RobotsProgram.messagesRU.getString("TestsPanelDescription"));
        
        {
            JMenuItem addLogMessageItem = new JMenuItem(
            		RobotsProgram.messagesRU.getString("TestMessageToLogItem"),
            		KeyEvent.VK_S);
            addLogMessageItem.addActionListener((event) -> {
                Logger.debug(RobotsProgram.messagesRU.getString("TestLogMessage"));
            });
            testMenu.add(addLogMessageItem);
        }
        return testMenu;
    }
    
    
    private LookAndFeelOption[] lookAndFeelVariants = {
    		new LookAndFeelOption(
    				UIManager.getSystemLookAndFeelClassName(), 
    				RobotsProgram.messagesRU.getString("ViewModeSystemViewName")),
    		new LookAndFeelOption(
    				UIManager.getCrossPlatformLookAndFeelClassName(), 
    	    		RobotsProgram.messagesRU.getString("ViewModeUniversalViewName"))
    };
    
    private JMenu getLookAndFeelMenu() {
        JMenu lookAndFeelMenu = new JMenu(RobotsProgram.messagesRU.getString("ViewModePanel"));
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
        		RobotsProgram.messagesRU.getString("ViewModePanelDescription"));
        
        for (LookAndFeelOption option : lookAndFeelVariants)
        {
            JMenuItem systemLookAndFeel = new JMenuItem(
            		option.getViewName(),
            		KeyEvent.VK_S);
            systemLookAndFeel.addActionListener((event) -> {
                setLookAndFeel(option.getSystemName());
                this.invalidate();
            });
            lookAndFeelMenu.add(systemLookAndFeel);
        }
        return lookAndFeelMenu;
    }
    
    
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
            | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
