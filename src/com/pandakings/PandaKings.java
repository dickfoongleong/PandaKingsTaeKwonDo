package com.pandakings;

import com.pandakings.utilities.Contact;
import com.pandakings.utilities.Payment;
import com.pandakings.utilities.Student;
import com.pandakings.utilities.enumclass.Belt;
import com.pandakings.utilities.enumclass.Club;
import com.pandakings.utilities.enumclass.ExpirationStatus;
import com.pandakings.utilities.enumclass.ParentRelationship;
import com.pandakings.utilities.enumclass.PaymentStatus;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class PandaKings extends JFrame implements Runnable, WindowListener {
  private static final Calendar CALENDAR = Calendar.getInstance();
  private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
  private static final long serialVersionUID = 20180813;
  private static final String APPLICATION_NAME = "Panda King's Tae Kwon Do";
  private static final String CONTACT_INFO_FILE = "csv/contact-list.csv";
  private static final String PAYMENT_INFO_FILE = "csv/payment-list.csv";
  private static final String PROMOTION_INFO_FILE = "csv/promotion-list.csv";
  private static final String STUDENT_INFO_FILE = "csv/student-list.csv";

  private static File contactListFile;
  private static File paymentListFile;
  private static File promotionListFile;
  private static File studentListFile;
  private static List<Contact> contactList;
  private static List<Payment> paymentList;
  private static List<Student> promotionList;
  private static List<Student> studentList;
  private static List<Integer> studentIdList;

  private JButton informationBtn;
  private JButton registrationBtn;
  private JButton paymentBtn;
  private JButton promotionBtn;
  private JLabel status;
  private JLabel companyNameLabel;
  private JLabel currentTabLabel;
  private JLabel dateLabel;
  private JMenu fileMenu;
  private JMenuItem fileSave;
  private JMenuItem fileExit;
  private JPanel mainPanel;
  private JPanel headerSide1Panel;
  private JPanel headerSide2Panel;
  private JPanel headerMidPanel;


  /**
   * Setup the application.
   */
  public PandaKings() {
    addWindowListener(this);
    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    setTitle(APPLICATION_NAME);
    setIcon();

    loadData();
    
    setupUserInterface();
    setupInformation();

    enableControls();
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    setVisible(true);
  }

  private void setupUserInterface() {
    setupControls();
    setupMenus();

    getContentPane().setLayout(new BorderLayout());

    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    getContentPane().add(setupHeaderPanel(), BorderLayout.NORTH);
    getContentPane().add(setupOperationPanel(), BorderLayout.WEST);
    getContentPane().add(mainPanel, BorderLayout.CENTER);

    colorInterface();
  }

  private void setupControls() {
    informationBtn = new JButton("Student");
    informationBtn.setFont(new Font(informationBtn.getFont().getName(), Font.PLAIN, 17));
    informationBtn.setToolTipText("Show every student's general information");
    informationBtn.addActionListener(new InformationListener());

    registrationBtn = new JButton("Registration");
    registrationBtn.setFont(new Font(registrationBtn.getFont().getName(), Font.PLAIN, 18));
    registrationBtn.setToolTipText("Register new student");
    registrationBtn.addActionListener(new RegistrationListener());

    paymentBtn = new JButton("Payment");
    paymentBtn.setFont(new Font(paymentBtn.getFont().getName(), Font.PLAIN, 18));
    paymentBtn.setToolTipText("Check payment records");
    paymentBtn.addActionListener(new PaymentListener());

    promotionBtn = new JButton("Promotion");
    promotionBtn.setFont(new Font(promotionBtn.getFont().getName(), Font.PLAIN, 18));
    promotionBtn.setToolTipText("Check students who are ready for promotion test");
    promotionBtn.addActionListener(new PromotionListener());

    companyNameLabel = new JLabel(" Panda King's Tae Kwon Do ");
    companyNameLabel.setFont(new Font(companyNameLabel.getFont().getName(), Font.BOLD, 20));
    companyNameLabel.setHorizontalAlignment(JLabel.LEFT);


    currentTabLabel = new JLabel();
    currentTabLabel.setFont(new Font(currentTabLabel.getFont().getName(), Font.PLAIN, 20));
    currentTabLabel.setHorizontalAlignment(JLabel.CENTER);

    dateLabel = new JLabel(DATE_FORMAT.format(CALENDAR.getTime()));
    dateLabel.setFont(new Font(dateLabel.getFont().getName(), Font.PLAIN, 15));
    dateLabel.setHorizontalAlignment(JLabel.RIGHT);

    status = new JLabel("Initializing");
  }

  private void enableControls() {
    fileSave.setEnabled(true);
    fileExit.setEnabled(true);

    informationBtn.setEnabled(true);
    registrationBtn.setEnabled(true);
    paymentBtn.setEnabled(true);
    promotionBtn.setEnabled(true);
  }

  private void setupMenus() {
    JMenuBar menuBar = new JMenuBar();
    setJMenuBar(menuBar);

    fileMenu = new JMenu("File");
    fileMenu.setMnemonic(KeyEvent.VK_F);
    fileMenu.setToolTipText("Menu items related to access files");
    menuBar.add(fileMenu);

    setupFileMenu();
  }

  private void setupFileMenu() {
    fileSave = new JMenuItem("Save updates");
    fileSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_MASK));
    fileSave.setMnemonic(KeyEvent.VK_S);
    fileSave.setToolTipText("Save the updates");
    fileSave.addActionListener(new FileSaveListener());
    fileMenu.add(fileSave);

    fileExit = new JMenuItem("Exit");
    fileExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.ALT_MASK));
    fileExit.setMnemonic(KeyEvent.VK_Q);
    fileExit.setToolTipText("Quit the application");
    fileExit.addActionListener(new FileExitListener());
    fileMenu.add(fileExit);
  }

  private void saveFile() {
//    saveInfomation();
//    savePayment();
//    saveContact();
//    savePromotion();
  }

  private void closeApplication() {
    saveFile();

    setVisible(false);
    System.exit(0);
  }

  private void setupInformation() {
    clearMainPanel();
    informationBtn.setBorderPainted(true);
    setTabTitle("Information");

    JScrollPane informationPane = new JScrollPane(makeInfoMainPanel());
    informationPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
    mainPanel.add(makeInfoHeaderPanel(), BorderLayout.NORTH);
    mainPanel.add(informationPane, BorderLayout.CENTER);

    setStatus("Showing all student' information");
  }

  private void setupRegistration() {
    clearMainPanel();
    registrationBtn.setBorderPainted(true);
    setTabTitle("Registration");

    setStatus("Register new student");
  }

  private void setupPayment() {
    clearMainPanel();
    paymentBtn.setBorderPainted(true);
    setTabTitle("Payment");

    JScrollPane paymentPane = new JScrollPane(makePaymentMainPanel());
    paymentPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
    mainPanel.add(makePaymentHeaderPanel(), BorderLayout.NORTH);
    mainPanel.add(paymentPane, BorderLayout.CENTER);

    setStatus("Payments record");
  }

  private void setupPromotion() {
    clearMainPanel();
    promotionBtn.setBorderPainted(true);
    setTabTitle("Promotion");

    JScrollPane promotionPane = new JScrollPane(makePromotionMainPanel());
    promotionPane.getVerticalScrollBar().setPreferredSize(new Dimension(0,0));
    mainPanel.add(makePromotionHeaderPanel(), BorderLayout.NORTH);
    mainPanel.add(promotionPane, BorderLayout.CENTER);
    setStatus("Students who are in promotion");
  }

  private JPanel setupHeaderPanel() {
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new GridLayout(3,1));

    headerSide1Panel = new JPanel();
    headerSide2Panel = new JPanel();

    headerMidPanel = new JPanel();
    headerMidPanel.setLayout(new GridLayout(1,3));
    headerMidPanel.add(companyNameLabel);
    headerMidPanel.add(currentTabLabel);
    headerMidPanel.add(dateLabel);

    headerPanel.add(headerSide1Panel);
    headerPanel.add(headerMidPanel);
    headerPanel.add(headerSide2Panel);

    return headerPanel;
  }

  private JPanel setupOperationPanel() {
    JPanel optionPanel = new JPanel();
    optionPanel.setLayout(new BoxLayout(optionPanel,BoxLayout.Y_AXIS));
    optionPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    optionPanel.add(informationBtn);
    optionPanel.add(paymentBtn);
    optionPanel.add(promotionBtn);
    optionPanel.add(registrationBtn);

    JPanel operationPanel = new JPanel();
    operationPanel.setLayout(new GridLayout(2,1));
    operationPanel.add(optionPanel);
    operationPanel.add(setupStatusPanel());

    return operationPanel;
  }

  private JPanel setupStatusPanel() {
    JPanel statusPanel = new JPanel();

    statusPanel.setLayout(new GridLayout(1,1));
    statusPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(Color.black), "Status"));
    statusPanel.add(makeFlowPanel(status, FlowLayout.LEFT));
    statusPanel.setPreferredSize(new Dimension(225,16));

    return statusPanel;
  }

  private JPanel makeFlowPanel(JComponent component, int alignment) {
    JPanel panel = new JPanel();
    panel.setLayout(new FlowLayout(alignment));
    panel.add(component);

    return panel;
  }

  private JPanel makeInfoMainPanel() {
    JPanel infoMainPanel = new JPanel();
    infoMainPanel.setLayout(new GridLayout(0, 1));

    JLabel studentNameLabel;
    JLabel studentBeltLabel;
    JLabel studentClubLabel;
    JLabel studentBirthLabel;
    JPanel studentNamePanel;
    JPanel studentBeltPanel;
    JPanel studentClubPanel;
    JPanel studentBirthPanel;

    for (Student student : studentList) {
      studentNameLabel = new JLabel(student.getName());
      studentNameLabel.setFont(new Font(studentNameLabel.getFont().getName(), Font.PLAIN, 14));
      studentNamePanel = makeFlowPanel(studentNameLabel, FlowLayout.CENTER);
      studentNamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

      studentBeltLabel = new JLabel(getStudentBeltString(student.getCurrentBelt()));
      studentBeltLabel.setFont(new Font(studentBeltLabel.getFont().getName(), Font.PLAIN, 14));
      studentBeltPanel = makeFlowPanel(studentBeltLabel, FlowLayout.CENTER);
      studentBeltPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      studentClubLabel = new JLabel(student.getClub().description());
      studentClubLabel.setFont(new Font(studentClubLabel.getFont().getName(), Font.PLAIN, 14));
      studentClubPanel = makeFlowPanel(studentClubLabel, FlowLayout.CENTER);
      studentClubPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      studentBirthLabel = new JLabel(DATE_FORMAT.format(student.getBirthday()));
      studentBirthLabel.setFont(new Font(studentBirthLabel.getFont().getName(), Font.PLAIN, 14));
      studentBirthPanel = makeFlowPanel(studentBirthLabel, FlowLayout.CENTER);
      studentBirthPanel.setBorder(BorderFactory.createLineBorder(Color.black));

      JPanel linePanel = new JPanel();
      linePanel.setLayout(new GridLayout(1,4));
      linePanel.add(studentNamePanel);
      linePanel.add(studentBeltPanel);
      linePanel.add(studentClubPanel);
      linePanel.add(studentBirthPanel);
      linePanel.addMouseListener(new InfoRowsMouseListener(student));

      infoMainPanel.add(linePanel);
    }

    return infoMainPanel;
  }

  private JPanel makeInfoHeaderPanel() {
    JPanel informationHeaderPanel = new JPanel();
    informationHeaderPanel.setLayout(new GridLayout(1,4));

    JLabel nameLabel = new JLabel("Name");
    nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel namePanel = makeFlowPanel(nameLabel, FlowLayout.CENTER);
    namePanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel beltLabel = new JLabel("Belt");
    beltLabel.setFont(new Font(beltLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel beltPanel = makeFlowPanel(beltLabel, FlowLayout.CENTER);
    beltPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel clubLabel = new JLabel("Club");
    clubLabel.setFont(new Font(clubLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel clubPanel = makeFlowPanel(clubLabel, FlowLayout.CENTER);
    clubPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel birthLabel = new JLabel("Birthday");
    birthLabel.setFont(new Font(birthLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel birthPanel = makeFlowPanel(birthLabel, FlowLayout.CENTER);
    birthPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    informationHeaderPanel.add(namePanel);
    informationHeaderPanel.add(beltPanel);
    informationHeaderPanel.add(clubPanel);
    informationHeaderPanel.add(birthPanel);

    return informationHeaderPanel;
  }

  private JPanel makePaymentMainPanel() {
    JPanel paymentMainPanel = new JPanel();
    paymentMainPanel.setLayout(new GridLayout(0, 1));

    List<JPanel> completedPaymentsPanel = new ArrayList<JPanel>();
    
    for (Payment payment : paymentList) {
      Student student = searchStudent(payment.getStudentId());
      if (payment.getPaymentStatus() == PaymentStatus.INCOMPLETE) {
        paymentMainPanel.add(makePaymentRowPanel(student, payment, false));
      } else {
        completedPaymentsPanel.add(makePaymentRowPanel(student, payment, true));
      }
    }
    
    for (JPanel panel: completedPaymentsPanel) {
      paymentMainPanel.add(panel);
    }

    return paymentMainPanel;
  }
  
  private JPanel makePaymentRowPanel(Student student, Payment payment, boolean completed) {
    JPanel linePanel = new JPanel();
    linePanel.setLayout(new GridLayout(1,5));
    
    JLabel studentNameLabel = new JLabel(student.getName());
    studentNameLabel.setFont(new Font(studentNameLabel.getFont().getName(), Font.PLAIN, 14));
    JPanel studentNamePanel = makeFlowPanel(studentNameLabel, FlowLayout.CENTER);
    studentNamePanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel amountTotalLabel = new JLabel("$" + payment.getAmountTotal());
    amountTotalLabel.setFont(new Font(amountTotalLabel.getFont().getName(), Font.PLAIN, 14));
    JPanel amountTotalPanel = makeFlowPanel(amountTotalLabel, FlowLayout.CENTER);
    amountTotalPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel amountLeftLabel = new JLabel("$" + payment.getAmountLeft());
    amountLeftLabel.setFont(new Font(amountLeftLabel.getFont().getName(), Font.PLAIN, 14));
    JPanel amountLeftPanel = makeFlowPanel(amountLeftLabel, FlowLayout.CENTER);
    amountLeftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel messageLabel = new JLabel(payment.getMessage());
    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 14));
    JPanel messagePanel = makeFlowPanel(messageLabel, FlowLayout.CENTER);
    messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel statusLabel = new JLabel(payment.getPaymentStatus().description());
    statusLabel.setFont(new Font(statusLabel.getFont().getName(), Font.PLAIN, 14));
    JPanel statusPanel = makeFlowPanel(statusLabel,FlowLayout.CENTER);
    statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    
    linePanel.add(studentNamePanel);
    linePanel.add(amountTotalPanel);
    linePanel.add(amountLeftPanel);
    linePanel.add(messagePanel);
    linePanel.add(statusPanel);
    linePanel.addMouseListener(new PaymentRowsMouseListener(student.getName(),payment));
    
    if (completed) {
      studentNameLabel.setForeground(Color.gray);
      amountTotalLabel.setForeground(Color.gray);
      amountLeftLabel.setForeground(Color.gray);
      messageLabel.setForeground(Color.gray);
      statusLabel.setForeground(Color.gray);
    }
    
    return linePanel;
  }

  private JPanel makePaymentHeaderPanel() {
    JPanel paymentHeaderPanel = new JPanel();
    paymentHeaderPanel.setLayout(new GridLayout(1,5));

    JLabel nameLabel = new JLabel("Student Name");
    nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel namePanel = makeFlowPanel(nameLabel, FlowLayout.CENTER);
    namePanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel amountTotalLabel = new JLabel("Amount Total");
    amountTotalLabel.setFont(new Font(amountTotalLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel amountTotalPanel = makeFlowPanel(amountTotalLabel, FlowLayout.CENTER);
    amountTotalPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel amountLeftLabel = new JLabel("Amount Left");
    amountLeftLabel.setFont(new Font(amountLeftLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel amountLeftPanel = makeFlowPanel(amountLeftLabel, FlowLayout.CENTER);
    amountLeftPanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel messageLabel = new JLabel("Message");
    messageLabel.setFont(new Font(messageLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel messagePanel = makeFlowPanel(messageLabel, FlowLayout.CENTER);
    messagePanel.setBorder(BorderFactory.createLineBorder(Color.black));

    JLabel statusLabel = new JLabel("Status");
    statusLabel.setFont(new Font(statusLabel.getFont().getName(), Font.PLAIN, 15));
    JPanel statusPanel = makeFlowPanel(statusLabel, FlowLayout.CENTER);
    statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
    
    paymentHeaderPanel.add(namePanel);
    paymentHeaderPanel.add(amountTotalPanel);
    paymentHeaderPanel.add(amountLeftPanel);
    paymentHeaderPanel.add(messagePanel);
    paymentHeaderPanel.add(statusPanel);

    return paymentHeaderPanel;
  }

  private JPanel makePromotionMainPanel() {
    JPanel promotionMainPanel = new JPanel();
    promotionMainPanel.setLayout(new GridLayout(0,1));

    return promotionMainPanel;
  }

  private JPanel makePromotionHeaderPanel() {
    JPanel promotionHeaderPanel = new JPanel();
    //promotionHeaderPanel.setLayout(new GridLayout(1,));

    return promotionHeaderPanel;
  }

  private void clearMainPanel() {
    mainPanel.removeAll();
    mainPanel.revalidate();
    mainPanel.repaint();
    colorInterface();
  }

  private void colorInterface() {
    headerSide1Panel.setBackground(new Color(250,250,250));
    headerSide2Panel.setBackground(new Color(250,250,250));
    headerMidPanel.setBackground(new Color(179,0,0));

    companyNameLabel.setForeground(Color.white);
    currentTabLabel.setForeground(Color.white);
    dateLabel.setForeground(Color.white);

    informationBtn.setOpaque(false);
    informationBtn.setBorderPainted(false);

    registrationBtn.setOpaque(false);
    registrationBtn.setBorderPainted(false);

    paymentBtn.setOpaque(false);
    paymentBtn.setBorderPainted(false);

    promotionBtn.setOpaque(false);
    promotionBtn.setBorderPainted(false);
  }

  private void setIcon() {
    try {
      ImageIcon icon = new ImageIcon("com/pandakings/images/AppIcon.png");
      setIconImage(icon.getImage());
    } catch (Throwable throwable) {
      setStatus("Cannot load icon");
    }
  }

  private void loadData() {
    loadContactData();
    loadPaymentData();
    loadStudentData();
    loadPromotionData();
  }

  private void loadPaymentData() {
    try {
      paymentList = new ArrayList<Payment>();
      paymentListFile = new File(PAYMENT_INFO_FILE);

      if (!paymentListFile.createNewFile()) {
        Scanner paymentFileScanner = new Scanner(paymentListFile);
        Payment payment;
        String lineInfo;
        String[] informations;
        int studentId;
        int amountTotal;
        int amountLeft;
        String message;
        PaymentStatus status;

        while (paymentFileScanner.hasNext()) {
          lineInfo = paymentFileScanner.nextLine();
          informations = lineInfo.split(",");

          studentId = Integer.parseInt(informations[0]);
          amountTotal = Integer.parseInt(informations[1]);
          amountLeft = Integer.parseInt(informations[2]);
          message = informations[3];
          status = PaymentStatus.getPaymentStatus(informations[4]);

          payment = new Payment(studentId, amountTotal, amountLeft, message, status);
          paymentList.add(payment);
        }
        paymentFileScanner.close();
      }
    } catch (NullPointerException npe) {
      setStatus("Payment file cannot be open");
    } catch (IOException ioe) {
      setStatus("Payment file cannot be created");
    } catch (SecurityException se) {
      setStatus("Payment file cannot be access due to security rights");
    } catch (NumberFormatException nfe) {
      setStatus("Error on either following: StudentID, AmountTotal, AmountLeft");
    }
  }

  private void loadPromotionData() {
    try {
      promotionList = new ArrayList<Student>();
      promotionListFile = new File(PROMOTION_INFO_FILE);

      if (!promotionListFile.createNewFile()) {
        Scanner promotionFileScanner = new Scanner(promotionListFile);
        int studentId;
        Student student;

        while (promotionFileScanner.hasNext()) {
          studentId = Integer.parseInt(promotionFileScanner.nextLine());
          student = searchStudent(studentId);

          promotionList.add(student);
        }
        promotionFileScanner.close();
      }
    } catch (NullPointerException npe) {
      setStatus("Promotion file cannot be open");
    } catch (IOException ioe) {
      setStatus("Promotion file cannot be created");
    } catch (SecurityException se) {
      setStatus("Promotion file cannot be access due to security rights");
    } catch (NumberFormatException nfe) {
      setStatus("Error on non-numeric student id");
    }
  }

  private void loadStudentData() {
    try {
      studentList = new ArrayList<Student>();
      studentIdList = new ArrayList<Integer>();
      studentListFile = new File(STUDENT_INFO_FILE);

      if (!studentListFile.createNewFile()) {
        Scanner studentFileScanner = new Scanner(studentListFile);
        Student currentStudent;
        String lineInfo;
        String[] informations;
        int id;
        String name;
        Belt currentBelt;
        List<Contact> currentStudentContactList;
        Club club;
        List<Payment> currentStudentPaymentRecord;
        ExpirationStatus expiredState;
        Date birthday;
        Date startDate;
        Date expiredDate;

        while (studentFileScanner.hasNext()) {
          lineInfo = studentFileScanner.nextLine();
          informations = lineInfo.split(",");
          id = Integer.parseInt(informations[0]);
          name = informations[1];
          currentBelt = Belt.getBelt(Integer.parseInt(informations[2]));
          club = Club.getClub(informations[3]);
          birthday = DATE_FORMAT.parse(informations[4]);
          startDate = DATE_FORMAT.parse(informations[5]);
          expiredDate = DATE_FORMAT.parse(informations[6]);

          expiredState = checkExpiredState(expiredDate);
          currentStudentContactList = getStudentContactList(id);
          currentStudentPaymentRecord = getStudentPaymentRecord(id);

          currentStudent = new Student(id, name, currentBelt, currentStudentContactList,
              club, currentStudentPaymentRecord, expiredState, 
              birthday, startDate, expiredDate);

          studentList.add(currentStudent);
          studentIdList.add(id);
        }
        studentFileScanner.close();
      }
    } catch (IOException ioe) {
      setStatus("Student list file cannot be loaded.");
    } catch (NumberFormatException nfe) {
      setStatus("File contain non-numeric payment amount");
    } catch (ParseException e) {
      setStatus("Date format incorrect");
    } catch (SecurityException se) {
      setStatus("Student-info-file cannot be access due to security rights");
    }
  }

  private void loadContactData() {
    try {
      contactList = new ArrayList<Contact>();
      contactListFile = new File(CONTACT_INFO_FILE);

      if (!contactListFile.createNewFile()) {
        Scanner fileScanner = new Scanner(contactListFile);
        Contact currentContact;
        String lineInfo;
        String[] informations;
        int studentId;
        String name;
        String phone;
        ParentRelationship relationship;
        String otherRelationship;

        while (fileScanner.hasNext()) {
          lineInfo = fileScanner.nextLine();
          informations = lineInfo.split(",");
          studentId = Integer.parseInt(informations[0]);
          name = informations[1];
          phone = informations[2];
          relationship = ParentRelationship.getParentRelationship(informations[3]);
          if (relationship == ParentRelationship.OTHER) {
            otherRelationship = informations[4];
          } else {
            otherRelationship = "";
          }

          currentContact = new Contact(studentId, name, phone, relationship, otherRelationship);
          contactList.add(currentContact);
        }

        fileScanner.close();
      }
    } catch (NumberFormatException nfe) {
      setStatus("Contact id is nun-numeric");
    } catch (IOException ioe) {
      setStatus("Cannot load the contact file");
    } catch (SecurityException se) {
      setStatus("Cannot access the contact file due to security rights");
    }
  }

  private String getStudentBeltString(Belt belt) {
    switch (belt) {
      case WHITE:
        return "WHITE";
      case YELLOW:
        return "YELLOW";
      case ORANGE:
        return "ORANGE";
      case GREEN:
        return "GREEN";
      case BLUE:
        return "BLUE";
      case PURPLE:
        return "PURPLE";
      case RED:
        return "RED";
      case BROWN:
        return "BROWN";
      case RED_BLACK:
        return "RED/BLACK";
      case SEMI_BLACK:
        return "SEMI BLACK";
      case BLACK:
        return "BLACK";
      default:
        return "UNKNOWN";
    }
  }

  private Student searchStudent(int id) {
    for (Student student: studentList) {
      if (student.getId() == id) {
        return student;
      }
    }

    return null;
  }

  private ExpirationStatus checkExpiredState(Date expiredDate) {
    Date today = CALENDAR.getTime();
    long timeDiff = expiredDate.getTime() - today.getTime();
    
    if (timeDiff <= 0) {
      return ExpirationStatus.EXPIRED;
    } else {
      long dayDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
      if (dayDiff <= 14) {
        return ExpirationStatus.SOON;
      } else {
        return ExpirationStatus.ON_GOING;
      }
    }
  }
  
  private List<Contact> getStudentContactList(int id) {
    List<Contact> studentContactList = new ArrayList<Contact>();

    for (Contact contact : contactList) {
      if (contact.getStudentId() == id) {
        studentContactList.add(contact);
      }
    }

    return studentContactList;
  }

  private List<Payment> getStudentPaymentRecord(int id) {
    List<Payment> studentPaymentRecord = new ArrayList<Payment>();

    for (Payment record : paymentList) {
      if (record.getStudentId() == id) {
        studentPaymentRecord.add(record);
      }
    }

    return studentPaymentRecord;
  }

  private void setTabTitle(String title) {
    currentTabLabel.setText(title);
  }

  private void setStatus(String message) {
    status.setText(message);
  }

  @Override
  public void windowOpened(WindowEvent e) {
  }

  @Override
  public void windowClosing(WindowEvent e) {
    closeApplication();
  }

  @Override
  public void windowClosed(WindowEvent e) {
  }

  @Override
  public void windowIconified(WindowEvent e) {
  }

  @Override
  public void windowDeiconified(WindowEvent e) {
  }

  @Override
  public void windowActivated(WindowEvent e) {
  }

  @Override
  public void windowDeactivated(WindowEvent e) {
  }

  @Override
  public void run() {
  }

  private class FileSaveListener implements ActionListener {
    public FileSaveListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      saveFile();
    }
  }

  private class FileExitListener implements ActionListener {
    public FileExitListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      closeApplication();
    }
  }

  private class PromotionListener implements ActionListener {
    public PromotionListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupPromotion();
    }
  }

  private class PaymentListener implements ActionListener {
    public PaymentListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupPayment();
    }
  }

  private class RegistrationListener implements ActionListener {
    public RegistrationListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupRegistration();
    }
  }

  private class InformationListener implements ActionListener {
    public InformationListener() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
      setupInformation();
    }
  }

  private class InfoRowsMouseListener implements MouseListener {
    private Student student;

    public InfoRowsMouseListener(Student student) {
      this.student = student;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      StudentWindow studentWindow = new StudentWindow(student);
      studentWindow.run();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) { 
    }
  }

  private class PaymentRowsMouseListener implements MouseListener {
    private String studentName;
    private Payment payment;

    public PaymentRowsMouseListener(String studentName, Payment payment) {
      this.studentName = studentName;
      this.payment = payment;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      JOptionPane.showMessageDialog(null, payment.getMessage() + "\n" + studentName);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
  }

  /**
   * The execution point of the program.
   * 
   * @param args Array of input, (not used).
   */
  public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new PandaKings();
      }
    });
  }
}
