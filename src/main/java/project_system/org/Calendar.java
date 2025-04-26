package project_system.org;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Calendar extends JFrame {

    private JTextArea noteArea;
    private JLabel selectedDateLabel;
    private JLabel monthLabel;
    private Map<String, String> notes;
    private java.util.Calendar calendar;
    private JPanel calendarPanel;
    private JPanel navigationPanel;

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/pomsdb?serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "luese_192003";

    public Calendar() {
        setTitle("Enhanced Calendar");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        notes = new HashMap<>();
        calendar = java.util.Calendar.getInstance();

        navigationPanel = new JPanel();
        JButton prevButton = createStyledButton("◀ Previous");
        prevButton.setToolTipText("Go to the previous month");
        prevButton.addActionListener(e -> {
            calendar.add(java.util.Calendar.MONTH, -1);
            updateCalendar();
        });

        JButton nextButton = createStyledButton("Next ▶");
        nextButton.setToolTipText("Go to the next month");
        nextButton.addActionListener(e -> {
            calendar.add(java.util.Calendar.MONTH, 1);
            updateCalendar();
        });

        monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        monthLabel.setForeground(Color.decode("#333333"));

        navigationPanel.setBackground(Color.decode("#F0F0F0"));
        navigationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        navigationPanel.add(prevButton);
        navigationPanel.add(monthLabel);
        navigationPanel.add(nextButton);

        calendarPanel = new JPanel(new GridLayout(0, 7));
        calendarPanel.setPreferredSize(new Dimension(1280, 800));
        calendarPanel.setBackground(Color.decode("#FFFFFF"));
        calendarPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#CCCCCC"), 1));

        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            dayLabel.setForeground(Color.decode("#555555"));
            calendarPanel.add(dayLabel);
        }

        noteArea = new JTextArea(5, 20);
        noteArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        noteArea.setBorder(BorderFactory.createLineBorder(Color.decode("#CCCCCC")));

        selectedDateLabel = new JLabel("Select a date to add notes.");
        selectedDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        selectedDateLabel.setForeground(Color.decode("#333333"));

        JButton saveButton = createStyledButton("💾 Save Note");
        saveButton.setToolTipText("Save the note for the selected date");
        saveButton.addActionListener(new SaveButtonListener());

        JButton deleteButton = createStyledButton("🗑 Delete Note");
        deleteButton.setToolTipText("Delete the note for the selected date");
        deleteButton.addActionListener(new DeleteButtonListener());

        JPanel notePanel = new JPanel();
        notePanel.setLayout(new BorderLayout());
        notePanel.setBackground(Color.decode("#FAFAFA"));
        notePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        notePanel.add(selectedDateLabel, BorderLayout.NORTH);
        notePanel.add(new JScrollPane(noteArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.decode("#FAFAFA"));
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        notePanel.add(buttonPanel, BorderLayout.SOUTH);

        add(navigationPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);
        add(notePanel, BorderLayout.EAST);

        updateCalendar();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.decode("#5783bc"));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#4A9DD1"));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.decode("#5783bc"));
            }
        });
        return button;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    private void saveNoteToDatabase(String dateKey, String note) {
        String insertQuery = "INSERT INTO notes (date_key, note) VALUES (?, ?) ON DUPLICATE KEY UPDATE note = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, dateKey);
            stmt.setString(2, note);
            stmt.setString(3, note);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteNoteFromDatabase(String dateKey) {
        String deleteQuery = "DELETE FROM notes WHERE date_key = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {
            stmt.setString(1, dateKey);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getNoteFromDatabase(String dateKey) {
        String selectQuery = "SELECT note FROM notes WHERE date_key = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setString(1, dateKey);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("note");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void updateCalendar() {
        calendarPanel.removeAll();
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};

        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            calendarPanel.add(dayLabel);
        }

        calendar.set(java.util.Calendar.DAY_OF_MONTH, 1);
        int startDay = calendar.get(java.util.Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        int month = calendar.get(java.util.Calendar.MONTH);
        int year = calendar.get(java.util.Calendar.YEAR);

        monthLabel.setText(calendar.getDisplayName(java.util.Calendar.MONTH, java.util.Calendar.LONG, Locale.getDefault()) + " " + year);

        java.util.Calendar today = java.util.Calendar.getInstance();
        int currentDay = today.get(java.util.Calendar.DAY_OF_MONTH);
        int currentMonth = today.get(java.util.Calendar.MONTH);
        int currentYear = today.get(java.util.Calendar.YEAR);

        for (int i = 0; i < startDay; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int i = 1; i <= daysInMonth; i++) {
            JButton dayButton = new JButton(String.valueOf(i));
            dayButton.addActionListener(new DayButtonListener(i, month, year));

            if (i == currentDay && month == currentMonth && year == currentYear) {
                dayButton.setBackground(Color.decode("#FFCC00")); // Highlight current date
                dayButton.setForeground(Color.BLACK);
            } else {
                dayButton.setBackground(Color.decode("#5783bc"));
                dayButton.setForeground(Color.WHITE);
            }

            String dateKey = year + "-" + (month + 1) + "-" + i;
            if (notes.containsKey(dateKey)) {
                dayButton.setToolTipText("Note: " + notes.get(dateKey)); // Show note as tooltip
            }

            calendarPanel.add(dayButton);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private class DayButtonListener implements ActionListener {
        private int day;
        private int month;
        private int year;

        public DayButtonListener(int day, int month, int year) {
            this.day = day;
            this.month = month;
            this.year = year;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            calendar.set(java.util.Calendar.DAY_OF_MONTH, day);
            calendar.set(java.util.Calendar.MONTH, month);
            calendar.set(java.util.Calendar.YEAR, year);
            String dateKey = getDateKey(calendar);
            selectedDateLabel.setText("Notes for: " + (month + 1) + "/" + day + "/" + year);
            String note = getNoteFromDatabase(dateKey);
            noteArea.setText(note);
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.Calendar selectedDate = calendar;
            String note = noteArea.getText();
            String dateKey = getDateKey(selectedDate);
            notes.put(dateKey, note);
            saveNoteToDatabase(dateKey, note);
            JOptionPane.showMessageDialog(Calendar.this, "Note saved!");
        }
    }

    private class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            java.util.Calendar selectedDate = calendar;
            String dateKey = getDateKey(selectedDate);
            notes.remove(dateKey);
            deleteNoteFromDatabase(dateKey);
            noteArea.setText("");
            JOptionPane.showMessageDialog(Calendar.this, "Note deleted!");
        }
    }

    private String getDateKey(java.util.Calendar date) {
        return date.get(java.util.Calendar.YEAR) + "-" + (date.get(java.util.Calendar.MONTH) + 1) + "-" + date.get(java.util.Calendar.DAY_OF_MONTH);
    }

}
